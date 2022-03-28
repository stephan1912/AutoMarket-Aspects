import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppSettings } from 'src/app/app.settings';
import { AdvertModel } from '../models/advertModel';

@Injectable()
export class AdvertService {

    public SavedAdvert: AdvertModel

    public advertList: AdvertModel[] = [];
    public totalCount: number = 0;

    public pageSize: number = 1;
    public currentPage: number = 1;
    public totalPages: number = 1;
    public currentFilter: any = null;

    public selectedAdvert: AdvertModel;

    constructor(private appSettings: AppSettings, private http: HttpClient){
    }

    public GetAdminAdverts(page: number = 1, sort: string = 'asc'): Observable<any>{
        return this.http.get<any>(this.appSettings.baseApiUrl + 'advert/admin/all?page=' + page + '&sort=' + sort);
    }

    public GetUserAdverts(): Observable<AdvertModel[]>{
        return this.http.get<AdvertModel[]>(this.appSettings.baseApiUrl + 'advert/all/me');
    }

    public DeleteAdvert(advert_id: number): Observable<any>{
        return this.http.delete<any>(this.appSettings.baseApiUrl + 'advert/' + advert_id);
    }

    public GetAllAdvertsFiltered(filter: any, page: number = 1): Observable<AdvertModel[]>{
        if(filter == null){
            const filterBtoa = this.currentFilter != null ? btoa(JSON.stringify(this.currentFilter)) : ''
            return this.http.get<any>(this.appSettings.baseApiUrl + 'advert/all?filter=' + filterBtoa + '&page=' + page).pipe(map(ads => {
                this.advertList = ads.adverts;
                this.totalCount = ads.totalCount;
                this.totalPages = this.totalCount / this.pageSize;// + this.totalCount % this.pageSize > 0 ? 1 : 0;
                this.currentPage = page;
                return ads;
            }));
        }
        this.currentFilter = filter;
        return this.http.get<any>(this.appSettings.baseApiUrl + 'advert/all?filter=' + btoa(JSON.stringify(filter)) + '&page=' + page).pipe(map(ads => {
            this.advertList = ads.adverts;
            this.totalCount = ads.totalCount;
            this.totalPages = this.totalCount / this.pageSize;// + this.totalCount % this.pageSize > 0 ? 1 : 0;
            //this.totalPages = this.totalCount / this.pageSize;
            this.currentPage = page;
            return ads;
        }));
    }
    
    public UpdateAdvert(advertModel: any): Observable<AdvertModel>{
        return this.http.put<AdvertModel>(this.appSettings.baseApiUrl + 'advert', advertModel);
    }

    public CreateAdvert(advertModel: any, pictures: File[]): Observable<AdvertModel>{
        let form = new FormData();
        pictures.forEach(p => form.append('files', p));
        form.append("JsonObject", JSON.stringify(advertModel));
        return this.http.post<AdvertModel>(this.appSettings.baseApiUrl + 'advert', form);
    }
}