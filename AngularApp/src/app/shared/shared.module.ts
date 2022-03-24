import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavComponent } from './nav/nav.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { LoginComponent } from './login/login.component';
import { CreateComponent } from './create/create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CustomModalComponent } from './custom-modal/custom-modal.component';
import { ToastrModule } from 'ngx-toastr';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { AdvertFilterComponent } from './advert-filter/advert-filter.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DateTimePickerModule } from '@syncfusion/ej2-angular-calendars';
import { AppRoutingModule } from '../app-routing.module';

@NgModule({
    imports: [
        FormsModule,
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        ReactiveFormsModule,
        ToastrModule,
        NgxUiLoaderModule,
        MDBBootstrapModule.forRoot()
    ],

    declarations: [
        NavComponent,
        LoginComponent,
        CreateComponent,
        CustomModalComponent,
        AdvertFilterComponent
    ],

    exports: [
        NavComponent,
        LoginComponent,
        CreateComponent,
        CustomModalComponent,
        AdvertFilterComponent
    ]
})
export class SharedModule { }

