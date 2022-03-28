
import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CountryModel } from 'src/app/shared/models/countryModel';
import { CountryService } from 'src/app/shared/services/country.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';

@Component({
    selector: 'app-country-list',
    templateUrl: 'country-list.component.html'
})

export class CountryListComponent implements OnInit {
    constructor(public countryService: CountryService, private customService: CustomLoaderService, 
        private changeDetector: ChangeDetectorRef, private router: Router) { }

    @ViewChild('countryNameFilter') countryNameFilter: HTMLInputElement;
    detectChange(){
        this.changeDetector.detectChanges();
        console.log('change_' + this.countryNameFilter.value);
    }

    ngOnInit() {
        this.customService.start();
        this.countryService.getAllCountrys().subscribe(r => {
            this.customService.stop();
        }, this.customService.errorFromResp);
     }

     setSelectedCountry(country: CountryModel){
         this.countryService.selectedCountry = country;
         this.countryService.selectedCountryChanged.next(country);

     }

     deleteCountry(country: CountryModel){
        this.countryService.deleteCountry(country).subscribe(r => {
                this.customService.success('Tara a fost stearsa!', 'Success');
                  this.countryService.getAllCountrys().subscribe(resp => {
                      this.customService.stop();
                  }, this.customService.errorFromResp)
        }, this.customService.errorFromResp)
     }

     seeModels(country: CountryModel){
        this.countryService.selectedCountry = country;
        this.router.navigate(['dashboard/model']);
     }
}