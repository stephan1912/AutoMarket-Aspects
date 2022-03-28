
import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AdvertModel } from 'src/app/shared/models/advertModel';
import { AdvertService } from 'src/app/shared/services/advert.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
    selector: 'app-advert-list',
    templateUrl: 'advert-list.component.html'
})

export class AdvertListComponent implements OnInit {
    userAdverts: AdvertModel[] = [];
    currentPage: number = 1;
    totalCount: number = 1;
    totalPages: number = 1;
    pageSize: number = 1;

    constructor(private advertService: AdvertService, private router: Router, public userService: UserService, private customService: CustomLoaderService) 
    {
    }

    goToAdvert(advert: AdvertModel){
      this.customService.start();
        this.advertService.SavedAdvert = advert;
        this.router.navigateByUrl('/dashboard/advert');
    }
    ngOnInit() {
      this.customService.start();
      if(this.userService.IsUserAdmin()){
        this.advertService.GetAdminAdverts().subscribe(resp => {
          this.totalCount = (resp as any).totalCount;
          this.totalPages = this.totalCount / this.pageSize + this.totalCount % this.pageSize > 0 ? 1 : 0;
          this.userAdverts = resp.adverts;
          this.advertService.SavedAdvert = null;
          this.customService.stop();
        }, this.customService.errorFromResp);
      }
      else{
        this.advertService.GetUserAdverts().subscribe(resp => {
          this.userAdverts = resp;
          this.advertService.SavedAdvert = null;
          this.customService.stop();
        }, this.customService.errorFromResp);
      }
    }

    getArray(nr) : number[]{
        return Array(nr).fill(1).map((x,i)=> (i+1))
    }

    setPage(page){
        this.customService.start();
        this.advertService.GetAdminAdverts(page, (document.getElementById("sortSelect") as any).value).subscribe(resp => {
          this.totalCount = (resp as any).totalCount;
          this.totalPages = this.totalCount / this.pageSize;
          this.userAdverts = resp.adverts;
          this.currentPage = page;
          this.advertService.SavedAdvert = null;
          this.customService.stop();
        }, this.customService.errorFromResp);
    }

    sortTypeChange(event){
      this.customService.start();
      this.advertService.GetAdminAdverts(1, event.target.value).subscribe(resp => {
        this.totalCount = (resp as any).totalCount;
        this.totalPages = this.totalCount / this.pageSize;
        this.userAdverts = resp.adverts;
        this.currentPage = 1;
        this.advertService.SavedAdvert = null;
        this.customService.stop();
      }, this.customService.errorFromResp);
    }
}