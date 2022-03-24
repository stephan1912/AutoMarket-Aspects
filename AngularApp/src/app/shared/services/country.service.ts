import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppSettings } from 'src/app/app.settings';
import { CountryModel } from '../models/countryModel';

@Injectable()
export class CountryService {

    public countryList: CountryModel[] = [];
    public selectedCountry: CountryModel;

    public selectedCountryChanged: Subject<CountryModel>;
    constructor(private appSettings: AppSettings, private http: HttpClient){
        this.selectedCountryChanged = new Subject<CountryModel>();
    }
    

    public deleteCountry(country: CountryModel): Observable<any>{
        return this.http.delete(this.appSettings.baseApiUrl + "country/" + country.country_id);
    }

    public getAllCountrys(): Observable<CountryModel[]>{
        return this.http.get<CountryModel[]>(this.appSettings.baseApiUrl + "country/all").pipe(map(list => {
            this.countryList = list;
            return list;
        }));
    }

    public updateCountry(country: CountryModel): Observable<CountryModel>{
        return this.http.put<CountryModel>(this.appSettings.baseApiUrl + "country", country).pipe(map(newOne => {
            this.selectedCountry = newOne;
            return newOne;
        }));
    }

    public createCountry(country: CountryModel): Observable<CountryModel>{
        return this.http.post<CountryModel>(this.appSettings.baseApiUrl + "country", country).pipe(map(newOne => {
            this.selectedCountry = newOne;
            return newOne;
        }));
    }

}