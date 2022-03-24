import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { MainBoardComponent } from './mainboard/mainboard.component';
import { NgModule } from '@angular/core';
import { AuthGuard } from '../shared/services/auth.guard';
import { AccountComponent } from './account/account.component';
import { Roles } from '../shared/models/roles';
import { BrandComponent } from './brand/brand.component';
import { ModelComponent } from './model/model.component';
import { BodyStyleComponent } from './bodystyle/bodystyle.component';
import { FeatureComponent } from './feature/feature.component';
import { CountryService } from '../shared/services/country.service';
import { CountryComponent } from './country/country.component';
import { AdvertCreateComponent } from './advert/advert-create/advert-create.component';

const routes: Routes = [
    {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard],
        data: { role: [Roles.User, Roles.Admin], requireLogin: true },
        children:[
            { 
                path: '', 
                pathMatch: 'full' ,
                redirectTo: 'main',
                canActivate: [AuthGuard],
                data: { role: [Roles.User, Roles.Admin], requireLogin: true },
            },
            {
                path: 'main',
                component: MainBoardComponent,
                canActivate: [AuthGuard],
                data: { role: [Roles.User, Roles.Admin], requireLogin: true },
            },
            {
                path: 'brand',
                component: BrandComponent,
                canActivate: [AuthGuard],
                data: { role: [Roles.Admin], requireLogin: true },
            },
            {
                path: 'model',
                component: ModelComponent,
                canActivate: [AuthGuard],
                data: { role: [Roles.Admin], requireLogin: true },
            },
            {
                path: 'bodystyle',
                component: BodyStyleComponent,
                canActivate: [AuthGuard],
                data: { role: [Roles.Admin], requireLogin: true },
            },
            {
                path: 'feature',
                component: FeatureComponent,
                canActivate: [AuthGuard],
                data: { role: [Roles.Admin], requireLogin: true },
            },
            {
                path: 'country',
                component: CountryComponent,
                canActivate: [AuthGuard],
                data: { role: [Roles.Admin], requireLogin: true },
            },
            // {
            //     path: 'create-advert',
            //     component: CreateElectionComponent,
            //     canActivate: [AuthGuard],
            //     data: { role: Roles.Creator }
            // },
            {
                path: 'advert',
                component: AdvertCreateComponent,
                canActivate: [AuthGuard],
                data: { role: [Roles.Admin, Roles.User], requireLogin: true }
            },
            {
                path: 'account',
                component: AccountComponent,
                canActivate: [AuthGuard],
                data: { role: [Roles.User, Roles.Admin], requireLogin: true },
            }
        ]
    }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class DashboardModuleRoutes { }
