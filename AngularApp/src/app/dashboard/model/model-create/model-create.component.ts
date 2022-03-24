import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ModelService } from 'src/app/shared/services/model.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';
import { BrandService } from 'src/app/shared/services/brand.service';

@Component({
    selector: 'app-model-create',
    templateUrl: 'model-create.component.html'
})

export class ModelCreateComponent implements OnInit {
    public modelForm: FormGroup;
    public otherErrorsDiv = null;
    public otherErrorsDivPassword = null;
    public otherErrorsDivTwofa = null;
    constructor(public modelService: ModelService, private formBuilder: FormBuilder, private router: Router,
        private customService: CustomLoaderService, private brandService: BrandService) {
            this.modelService.selectedModelChanged.subscribe(_ => {
                this.reinitForms();
            });
         }

    ngOnInit() {
        this.reinitForms();
    }
    get f() { return this.modelForm.controls; }
    checkInput(input: any) {
        return input.invalid && (input.dirty || input.touched);
    }

    reinitForms(clear: boolean = false) {
        if(clear) this.modelService.selectedModel = null;
        this.modelForm = this.formBuilder.group({
            name: [this.modelService.selectedModel?.name, [Validators.required]],
            generation: [this.modelService.selectedModel?.generation, [Validators.required]],
            launchYear: [this.modelService.selectedModel?.launchYear, [Validators.min(1955), Validators.max(2021)]],
            finalYear: [this.modelService.selectedModel?.finalYear, [Validators.min(1955), Validators.max(2021)]],
        });
    }

    public atLeastOneEdit: boolean = false;
    onSubmit() {
        this.atLeastOneEdit = false;
        this.modelForm.markAllAsTouched();
        if (this.modelForm.invalid && this.modelService.selectedModel == null) {
            return;
        }
        if (this.modelService.selectedModel != null) {
            this.customService.start();
            this.modelService.updateModel(<any>{
                brand_id: this.brandService.selectedBrand.brand_id,
                model_id: this.modelService.selectedModel.model_id,
                name: this.f.name.value,
                generation: this.f.generation.value,
                launchYear: this.f.launchYear.value,
                finalYear: this.f.finalYear.value
            }).subscribe(r => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Modelul a fost actualizat!', 'Succes');
                this.modelService.getAllModels(this.brandService.selectedBrand.brand_id).subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp)
            }, this.customService.errorFromResp);
        }
        else {
            this.customService.start();
            this.modelService.createModel(<any>{
                brand_id: this.brandService.selectedBrand.brand_id,
                name: this.f.name.value,
                generation: this.f.generation.value,
                launchYear: this.f.launchYear.value,
                finalYear: this.f.finalYear.value
            }).subscribe(response => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Modelul a fost creat!', 'Succes');
                this.modelService.getAllModels(this.brandService.selectedBrand.brand_id).subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp);
            }, this.customService.errorFromResp);
        }
    }
    
}