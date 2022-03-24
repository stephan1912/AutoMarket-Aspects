import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FeatureModel } from 'src/app/shared/models/featureModel';
import { FeatureService } from 'src/app/shared/services/feature.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';

@Component({
    selector: 'app-feature-create',
    templateUrl: 'feature-create.component.html'
})

export class FeatureCreateComponent implements OnInit {
    public featureForm: FormGroup;
    public otherErrorsDiv = null;
    public otherErrorsDivPassword = null;
    public otherErrorsDivTwofa = null;
    constructor(public featureService: FeatureService, private formBuilder: FormBuilder, private router: Router,
        private customService: CustomLoaderService) {
            this.featureService.selectedFeatureChanged.subscribe(_ => {
                this.reinitForms();
            });
         }

    ngOnInit() {
        this.reinitForms();
    }
    get f() { return this.featureForm.controls; }
    checkInput(input: any) {
        return input.invalid && (input.dirty || input.touched);
    }

    reinitForms(clear: boolean = false) {
        if(clear) this.featureService.selectedFeature = null;
        this.featureForm = this.formBuilder.group({
            featureName: [this.featureService.selectedFeature?.name, [Validators.required]],
        });
    }

    public atLeastOneEdit: boolean = false;
    onSubmit() {
        this.atLeastOneEdit = false;
        this.featureForm.markAllAsTouched();
        if (this.featureForm.invalid && this.featureService.selectedFeature == null) {
            return;
        }
        if (this.featureService.selectedFeature != null) {
            this.customService.start();
            this.featureService.updateFeature(<FeatureModel>{
                id: this.featureService.selectedFeature.id,
                name: this.f.featureName.value,
            }).subscribe(r => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Optiunea a fost actualizata!', 'Success');
                this.featureService.getAllFeatures().subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp)
            }, this.customService.errorFromResp);
        }
        else {
            this.customService.start();
            this.featureService.createFeature(<FeatureModel>{
                name: this.f.featureName.value,
            }).subscribe(response => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Optiunea a fost creata!', 'Success');
                this.featureService.getAllFeatures().subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp);
            }, this.customService.errorFromResp);
        }
    }
    
}