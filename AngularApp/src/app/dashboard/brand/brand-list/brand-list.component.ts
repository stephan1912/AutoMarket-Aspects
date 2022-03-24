
import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { BrandModel } from 'src/app/shared/models/brandModel';
import { BrandService } from 'src/app/shared/services/brand.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';

@Component({
    selector: 'app-brand-list',
    templateUrl: 'brand-list.component.html'
})

export class BrandListComponent implements OnInit {
    constructor(public brandService: BrandService, private customService: CustomLoaderService, 
        private changeDetector: ChangeDetectorRef, private router: Router) { }

    @ViewChild('brandNameFilter') brandNameFilter: HTMLInputElement;
    detectChange(){
        this.changeDetector.detectChanges();
        console.log('change_' + this.brandNameFilter.value);
    }

    ngOnInit() {
        this.customService.start();
        this.brandService.getAllBrands().subscribe(r => {
            this.customService.stop();
        }, this.customService.errorFromResp);
     }

     setSelectedBrand(brand: BrandModel){
         this.brandService.selectedBrand = brand;
         this.brandService.selectedBrandChanged.next(brand);
     }

     deleteBrand(brand: BrandModel){
        this.brandService.deleteBrand(brand).subscribe(r => {
                this.customService.success('Brandul a fost sters!', 'Success');
                  this.brandService.getAllBrands().subscribe(resp => {
                      this.customService.stop();
                  }, this.customService.errorFromResp)
        }, this.customService.errorFromResp)
     }

     seeModels(brand: BrandModel){
        this.brandService.selectedBrand = brand;
        this.router.navigate(['dashboard/model']);
     }
}