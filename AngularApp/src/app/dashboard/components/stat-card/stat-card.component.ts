import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { AppSettings } from 'src/app/app.settings';
import { AdvertModel } from 'src/app/shared/models/advertModel';
import { AdvertService } from 'src/app/shared/services/advert.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';

@Component({
    selector: 'app-stat-card',
    templateUrl: 'stat-card.component.html',
    styleUrls: ['./stat-card.component.scss']
})

export class StatCardComponent implements OnInit {
    @Input()
    public Title: string = 'title';
    @Input()
    public Picture: string = 'picture';
    @Input()
    public Price: number = 20000;
    @Input()
    public Year: number = 2021;
    @Input()
    public Km: number = 1000;
    @Input()
    public Fuel: string = 'fuel';
    @Input()
    public EngineCap: number = 2000;
    @Input()
    public ShowDelete: boolean = false;
    @Input()
    public Advert: AdvertModel = null;
    @Input()
    public AdvertList: AdvertModel[] = null;
    colorClass: string = 'light-blue lighten-1'

    constructor(public appSettings: AppSettings, public advertService: AdvertService, public changeDetector: ChangeDetectorRef, public customService: CustomLoaderService) { }

    deleteAdvert(event){
        this.customService.start();
        this.advertService.DeleteAdvert(this.Advert.advert_id).subscribe(_ => {
            this.AdvertList.splice(this.AdvertList.findIndex(a => a.advert_id == this.Advert.advert_id), 1);
            this.changeDetector.detectChanges();
            this.customService.success("Anunt sters!", "Success");
            this.customService.stop();
        }, this.customService.errorFromResp);
        event.preventDefault();
        event.stopPropagation();
    }

    ngOnInit() {
     }
}