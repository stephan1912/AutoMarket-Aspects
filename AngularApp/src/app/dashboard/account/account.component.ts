import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { UserModel } from 'src/app/shared/models/userModel';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';
import { formatDate } from '@angular/common';
@Component({
    selector: 'app-account',
    templateUrl: 'account.component.html'
})

export class AccountComponent implements OnInit {
    public currentUser: UserModel = null;
    public profileForm: FormGroup;
    public newPasswordForm: FormGroup;
    public otherErrorsDiv = null;
    public otherErrorsDivPassword = null;
    public otherErrorsDivTwofa = null;
    constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router,
        private customService: CustomLoaderService) { }

    ngOnInit() {
        this.customService.start();
        this.userService.getUserProfile().subscribe(u => { this.currentUser = u; this.reinitForms(); this.customService.stop(); }, this.customService.errorFromResp);
        
    }
    get f() { return this.profileForm.controls; }
    get pf() { return this.newPasswordForm.controls; }
    checkInput(input: any) {
        return input.invalid && (input.dirty || input.touched);
    }

    reinitForms() {
        this.profileForm = this.formBuilder.group({
            userFirstName: [this.currentUser.firstName, [Validators.required]],
            userLastName: [this.currentUser.lastName, [Validators.required]],
            username: [this.currentUser.username, [Validators.required]],
            email: [this.currentUser.email, [Validators.required, Validators.email]],
            birthDate: [formatDate(this.currentUser.birthdate, 'yyyy-MM-dd', 'en'), [Validators.required]]
        });
        this.newPasswordForm = this.formBuilder.group({
            currentPassword: ['', [Validators.required]],
            newPassword: ['', [Validators.minLength(6), Validators.maxLength(12), Validators.required]],
        });
    }

    public atLeastOneEdit: boolean = false;
    onSubmit() {
        this.profileForm.markAllAsTouched();
        if (this.profileForm.invalid) {
            return;
        }
        this.customService.start();
        this.userService.updateProfile(<UserModel>{
            email: this.f.email.value,
            firstName: this.f.userFirstName.value,
            lastName: this.f.userLastName.value,
            birthdate: this.f.birthDate.value,
            username: this.f.username.value
        }).subscribe(r => {
            this.userService.getUserProfile().subscribe(user => {
                this.currentUser = user;
                this.reinitForms();
                this.customService.success('Profil actualizat cu succes!', 'Success');
                this.customService.stop();
            }, this.customService.errorFromResp);
        }, this.customService.errorFromResp);
    }

    changePassword() {
        this.newPasswordForm.markAllAsTouched();
        if (this.newPasswordForm.invalid) {
            return;
        }
        if(this.pf.currentPassword.value == this.pf.newPassword.value){
            return;
        }

        this.customService.start();
        this.userService.updateUserPassword({
            currentPassword: this.pf.currentPassword.value,
            newPassword: this.pf.newPassword.value,

        }).subscribe(r => {
            this.userService.getUserProfile().subscribe(user => {
                this.currentUser = user;
                this.reinitForms();
                this.customService.success('Parola actualizata cu succes!', 'Success');
                this.customService.stop();
            }, this.customService.errorFromResp);
        }, this.customService.errorFromResp)
    }
}