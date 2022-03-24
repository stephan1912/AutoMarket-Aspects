import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CountryModel } from 'src/app/shared/models/countryModel';
import { CountryService } from 'src/app/shared/services/country.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';

@Component({
    selector: 'app-country-create',
    templateUrl: 'country-create.component.html'
})

export class CountryCreateComponent implements OnInit {
    public countryForm: FormGroup;
    public otherErrorsDiv = null;
    public otherErrorsDivPassword = null;
    public otherErrorsDivTwofa = null;
    constructor(public countryService: CountryService, private formBuilder: FormBuilder, private router: Router,
        private customService: CustomLoaderService) {
            this.countryService.selectedCountryChanged.subscribe(_ => {
                this.reinitForms();
            });
         }

    ngOnInit() {
        this.reinitForms();
    }
    get f() { return this.countryForm.controls; }
    checkInput(input: any) {
        return input.invalid && (input.dirty || input.touched);
    }

    reinitForms(clear: boolean = false) {
        if(clear) this.countryService.selectedCountry = null;
        this.countryForm = this.formBuilder.group({
            countryName: [this.countryService.selectedCountry?.name, [Validators.required]],
        });
    }

    
    public atLeastOneEdit: boolean = false;
    onSubmit() {
        
        this.countryForm.markAllAsTouched();
        if (this.countryForm.invalid && this.countryService.selectedCountry == null) {
            return;
        }
        if (this.countryService.selectedCountry != null) {
            this.customService.start();
            this.countryService.updateCountry(<CountryModel>{
                country_id: this.countryService.selectedCountry.country_id,
                name: this.f.countryName.value,
            }).subscribe(r => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Tara a fost actualizata!', 'Success');
                this.countryService.getAllCountrys().subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp)
            }, this.customService.errorFromResp);
        }
        else {
            this.customService.start();
            this.countryService.createCountry(<CountryModel>{
                name: this.f.countryName.value,
            }).subscribe(response => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Tara a fost creata!', 'Success');
                this.countryService.getAllCountrys().subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp);
            }, this.customService.errorFromResp);
        }
    }
    
}