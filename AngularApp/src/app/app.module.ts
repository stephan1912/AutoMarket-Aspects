import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { SharedModule } from './shared/shared.module';
import { AuthenticationComponent } from './authentication/authentication.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AppSettings } from './app.settings';
import { UserService } from './shared/services/user.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { CommonModule } from '@angular/common';
import { JwtInterceptor } from './shared/services/jwt.interceptor';
import { JwtModule } from '@auth0/angular-jwt';
import { ToastrModule } from 'ngx-toastr';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { CustomLoaderService } from './shared/services/customLoader.service';
import { DashboardModule } from './dashboard/dashboard.module';
import { BrandService } from './shared/services/brand.service';
import { ModelService } from './shared/services/model.service';
import { BodyStyleService } from './shared/services/bodyStyle.service';
import { FeatureService } from './shared/services/feature.service';
import { CountryService } from './shared/services/country.service';
import { AdvertService } from './shared/services/advert.service';
import { AdvertComponent } from './advert/advert.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthenticationComponent,
    HomeComponent,
    AdvertComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    DashboardModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    NgxUiLoaderModule,
    MDBBootstrapModule.forRoot(),
    JwtModule.forRoot({
      config: {
        blacklistedRoutes: []
      }
    })
  ],
  providers:  [
    AppSettings,
    CustomLoaderService,
    UserService,
    BrandService,
    ModelService,
    BodyStyleService,
    FeatureService,
    CountryService,
    AdvertService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
