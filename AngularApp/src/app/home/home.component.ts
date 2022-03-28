import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdvertService } from '../shared/services/advert.service';
import { CustomLoaderService } from '../shared/services/customLoader.service';

@Component({
    selector: 'app-home',
    templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit{

   constructor(public advertService: AdvertService, public customService: CustomLoaderService, private router: Router){

   }
    ngOnInit(): void {
        this.customService.start();
        this.advertService.GetAllAdvertsFiltered(null).subscribe(_ => {
            this.customService.stop();
        });
    }

    goToAdvert(advert){
        this.advertService.selectedAdvert = advert;
        this.router.navigate(['advert']);
    }

    getArray(nr) : number[]{
        return Array(nr).fill(1).map((x,i)=> (i+1))
    }

    setPage(page){
        this.customService.start();
        this.advertService.GetAllAdvertsFiltered(null, page).subscribe(_ => {
            this.customService.stop();
        });
    }
}