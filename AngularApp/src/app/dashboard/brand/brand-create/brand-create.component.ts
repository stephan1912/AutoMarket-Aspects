import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BrandModel } from 'src/app/shared/models/brandModel';
import { BrandService } from 'src/app/shared/services/brand.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
    selector: 'app-brand-create',
    templateUrl: 'brand-create.component.html'
})

export class BrandCreateComponent implements OnInit {
    public brandForm: FormGroup;
    public otherErrorsDiv = null;
    public otherErrorsDivPassword = null;
    public otherErrorsDivTwofa = null;
    constructor(public brandService: BrandService, private formBuilder: FormBuilder, private router: Router,
        private customService: CustomLoaderService, private userService: UserService) { 
            this.brandService.selectedBrandChanged.subscribe(_ => {
                this.reinitForms();
            });
        }

    ngOnInit() {
        this.reinitForms();
    }
    get f() { return this.brandForm.controls; }
    checkInput(input: any) {
        return input.invalid && (input.dirty || input.touched);
    }

    reinitForms(clear: boolean = false) {
        if(clear) this.brandService.selectedBrand = null;
        this.brandForm = this.formBuilder.group({
            brandName: [this.brandService.selectedBrand?.name, [Validators.required]],
            brandCode: [this.brandService.selectedBrand?.code, [Validators.required]],
        });
    }

    public atLeastOneEdit: boolean = false;
    onSubmit() {
        this.brandForm.markAllAsTouched();
        if (this.brandForm.invalid && this.brandService.selectedBrand == null) {
            return;
        }
        if (this.brandService.selectedBrand != null) {
            this.customService.start();
            this.brandService.updateBrand(<BrandModel>{
                brand_id: this.brandService.selectedBrand.brand_id,
                name: this.f.brandName.value,
                code: this.f.brandCode.value,
            }).subscribe(r => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Brandul a fost actualizat!', 'Success');
                this.brandService.getAllBrands().subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp)
            }, this.customService.errorFromResp);
        }
        else {
            this.customService.start();
            this.brandService.createBrand(<BrandModel>{
                name: this.f.brandName.value,
                code: this.f.brandCode.value,
            }).subscribe(response => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Brandul a fost creat!', 'Success');
                this.brandService.getAllBrands().subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp);
            }, this.customService.errorFromResp);
        }
    }
    
}