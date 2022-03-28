import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { ModelObject } from 'src/app/shared/models/modelObject';
import { ModelService } from 'src/app/shared/services/model.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';
import { BrandService } from 'src/app/shared/services/brand.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-model-list',
    templateUrl: 'model-list.component.html'
})

export class ModelListComponent implements OnInit {
    constructor(public modelService: ModelService, private brandService:BrandService, private customService: CustomLoaderService, 
        private changeDetector: ChangeDetectorRef, private router: Router) { }

    @ViewChild('modelNameFilter') modelNameFilter: HTMLInputElement;
    detectChange(){
        this.changeDetector.detectChanges();
        console.log('change_' + this.modelNameFilter.value);
    }

    ngOnInit() {
        if(this.brandService.selectedBrand == null) {
             //this.router.navigate(['/dashboard/brand']);
             return;
        }
        this.customService.start();
        this.modelService.getAllModels(this.brandService.selectedBrand.brand_id).subscribe(r => {
            this.customService.stop();
        }, this.customService.errorFromResp);
     }

     setSelectedModel(model: ModelObject){
         this.modelService.selectedModel = model;
         this.modelService.selectedModelChanged.next(model);
     }

     deleteModel(model: ModelObject){
        this.modelService.deleteModel(model).subscribe(r => {
                this.customService.success('Modeleul a fost sters!', 'Success');
                  this.modelService.getAllModels(this.brandService.selectedBrand.brand_id).subscribe(resp => {
                      this.customService.stop();
                  }, this.customService.errorFromResp)
        }, this.customService.errorFromResp)
     }
}