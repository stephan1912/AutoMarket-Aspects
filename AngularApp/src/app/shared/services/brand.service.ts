import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppSettings } from 'src/app/app.settings';
import { BrandModel } from '../models/brandModel';

@Injectable()
export class BrandService {

    public brandList: BrandModel[] = [];
    public selectedBrand: BrandModel;

    public selectedBrandChanged: Subject<BrandModel>;
    constructor(private appSettings: AppSettings, private http: HttpClient){
        this.selectedBrandChanged = new Subject<BrandModel>();
    }
    

    public deleteBrand(brand: BrandModel): Observable<any>{
        return this.http.delete(this.appSettings.baseApiUrl + "brand/" + brand.brand_id);
    }

    public getAllBrands(): Observable<BrandModel[]>{
        return this.http.get<BrandModel[]>(this.appSettings.baseApiUrl + "brand/all").pipe(map(list => {
            this.brandList = list;
            return list;
        }));
    }

    public updateBrand(brand: BrandModel): Observable<BrandModel>{
        return this.http.put<BrandModel>(this.appSettings.baseApiUrl + "brand", brand).pipe(map(x => {
            this.selectedBrand = x;
            return x;
        }));
    }

    public createBrand(brand: BrandModel): Observable<BrandModel>{
        return this.http.post<BrandModel>(this.appSettings.baseApiUrl + "brand", brand).pipe(map(x => {
            this.selectedBrand = x;
            return x;
        }));
    }
}