import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppSettings } from '../app.settings';
import { AdvertService } from '../shared/services/advert.service';
import { CustomLoaderService } from '../shared/services/customLoader.service';
import { FeatureService } from '../shared/services/feature.service';

@Component({
    selector: 'app-advert',
    templateUrl: 'advert.component.html',
    styleUrls: ['./advert.component.scss']
})

export class AdvertComponent implements OnInit {
    
    constructor(public appSettings: AppSettings, public advertService: AdvertService, public router: Router, private customService: CustomLoaderService) { }

    ngOnInit() { 
        
        if(this.advertService.selectedAdvert == null){
            this.router.navigate(['home']);
            return;
        }

    }
}