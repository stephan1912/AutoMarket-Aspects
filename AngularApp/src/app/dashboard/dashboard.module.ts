import { NgModule } from '@angular/core';
import { DashboardComponent } from './dashboard.component';
import { MainBoardComponent } from './mainboard/mainboard.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from '../app-routing.module';
import { SharedModule } from '../shared/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { DashboardModuleRoutes } from './dashboard.routes';
import { StatCardComponent } from './components/stat-card/stat-card.component';
import { DateTimePickerModule } from '@syncfusion/ej2-angular-calendars';
import { CommonModule } from '@angular/common';
import { NavBarComponent } from './components/navbar/navbar.component';
import { AccountComponent } from './account/account.component';
import { ToastrModule } from 'ngx-toastr';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { BrandListComponent } from './brand/brand-list/brand-list.component';
import { BrandComponent } from './brand/brand.component';
import { BrandCreateComponent } from './brand/brand-create/brand-create.component';
import { ModelListComponent } from './model/model-list/model-list.component';
import { ModelComponent } from './model/model.component';
import { ModelCreateComponent } from './model/model-create/model-create.component';
import { BodyStyleComponent } from './bodystyle/bodystyle.component';
import { BodyStyleListComponent } from './bodystyle/bodystyle-list/bodystyle-list.component';
import { BodyStyleCreateComponent } from './bodystyle/bodystyle-create/bodystyle-create.component';
import { FeatureComponent } from './feature/feature.component';
import { FeatureListComponent } from './feature/feature-list/feature-list.component';
import { FeatureCreateComponent } from './feature/feature-create/feature-create.component';
import { CountryComponent } from './country/country.component';
import { CountryListComponent } from './country/country-list/country-list.component';
import { CountryCreateComponent } from './country/country-create/country-create.component';
import { AdvertListComponent } from './advert/advert-list/advert-list.component';
import { AdvertCreateComponent } from './advert/advert-create/advert-create.component';
 

@NgModule({
    imports: [
        FormsModule,
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        SharedModule,
        ReactiveFormsModule,
        HttpClientModule,
        DashboardModuleRoutes,
        DateTimePickerModule,
        ToastrModule,
        NgxUiLoaderModule,
        MDBBootstrapModule.forRoot()
    ],

    exports: [
        StatCardComponent,
        AccountComponent
    ],

    declarations: [
        DashboardComponent,
        MainBoardComponent,
        SidebarComponent,
        StatCardComponent,
        NavBarComponent,
        AccountComponent,
        BrandComponent,
        BrandListComponent,
        BrandCreateComponent,
        ModelComponent,
        ModelListComponent,
        ModelCreateComponent,
        BodyStyleComponent,
        BodyStyleListComponent,
        BodyStyleCreateComponent,
        FeatureComponent,
        FeatureListComponent,
        FeatureCreateComponent,
        CountryComponent,
        CountryListComponent,
        CountryCreateComponent,
        AdvertListComponent,
        AdvertCreateComponent
    ],
    providers: []
})

export class DashboardModule { }