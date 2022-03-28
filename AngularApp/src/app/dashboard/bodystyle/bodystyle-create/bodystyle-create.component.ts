import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BodyStyleModel } from 'src/app/shared/models/bodyStyleModel';
import { BodyStyleService } from 'src/app/shared/services/bodyStyle.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
    selector: 'app-bodyStyle-create',
    templateUrl: 'bodyStyle-create.component.html'
})

export class BodyStyleCreateComponent implements OnInit {
    public bodyStyleForm: FormGroup;
    public otherErrorsDiv = null;
    public otherErrorsDivPassword = null;
    public otherErrorsDivTwofa = null;
    constructor(public bodyStyleService: BodyStyleService, private formBuilder: FormBuilder, private router: Router,
        private customService: CustomLoaderService, private userService: UserService) { 
            this.bodyStyleService.selectedBsChanged.subscribe(_ => {
                this.reinitForms();
            })
        }

    ngOnInit() {
        this.reinitForms();
    }
    get f() { return this.bodyStyleForm.controls; }
    checkInput(input: any) {
        return input.invalid && (input.dirty || input.touched);
    }

    reinitForms(clear: boolean = false) {
        if(clear) this.bodyStyleService.selectedBodyStyle = null;
        this.bodyStyleForm = this.formBuilder.group({
            bodyStyleName: [this.bodyStyleService.selectedBodyStyle?.name, [Validators.required]],
            bodyStyleDescription: [this.bodyStyleService.selectedBodyStyle?.description, [Validators.required]],
        });
    }

    public atLeastOneEdit: boolean = false;
    onSubmit() {
        this.atLeastOneEdit = false;
        this.bodyStyleForm.markAllAsTouched();
        if (this.bodyStyleForm.invalid && this.bodyStyleService.selectedBodyStyle == null) {
            return;
        }
        if (this.bodyStyleService.selectedBodyStyle != null) {
            this.customService.start();
            this.bodyStyleService.updateBodyStyle(<BodyStyleModel>{
                bs_id: this.bodyStyleService.selectedBodyStyle.bs_id,
                name: this.f.bodyStyleName.value,
                description: this.f.bodyStyleDescription.value,
            }).subscribe(r => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Caroseria a fost actualizata!', 'Succes');
                this.bodyStyleService.getAllBodyStyles().subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp)
            }, this.customService.errorFromResp);
        }
        else {
            this.customService.start();
            this.bodyStyleService.createBodyStyle(<BodyStyleModel>{
                name: this.f.bodyStyleName.value,
                description: this.f.bodyStyleDescription.value,
            }).subscribe(response => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Caroseria a fost creata!', 'Succes');
                this.bodyStyleService.getAllBodyStyles().subscribe(resp => {
                    this.customService.stop();
                }, this.customService.errorFromResp);
            }, this.customService.errorFromResp);
        }
    }
    
}