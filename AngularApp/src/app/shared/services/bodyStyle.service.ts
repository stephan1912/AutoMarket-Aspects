import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppSettings } from 'src/app/app.settings';
import { BodyStyleModel } from '../models/bodyStyleModel';

@Injectable()
export class BodyStyleService {

    public bodyStyleList: BodyStyleModel[] = [];
    public selectedBodyStyle: BodyStyleModel;

    public selectedBsChanged: Subject<BodyStyleModel>;
    constructor(private appSettings: AppSettings, private http: HttpClient){
        this.selectedBsChanged = new Subject<BodyStyleModel>();
    }
    

    public deleteBodyStyle(bodyStyle: BodyStyleModel): Observable<any>{
        return this.http.delete(this.appSettings.baseApiUrl + "bodyStyle/" + bodyStyle.bs_id);
    }

    public getAllBodyStyles(): Observable<BodyStyleModel[]>{
        return this.http.get<BodyStyleModel[]>(this.appSettings.baseApiUrl + "bodyStyle/all").pipe(map(list => {
            this.bodyStyleList = list;
            return list;
        }));
    }

    public updateBodyStyle(bodyStyle: BodyStyleModel): Observable<BodyStyleModel>{
        return this.http.put<BodyStyleModel>(this.appSettings.baseApiUrl + "bodyStyle", bodyStyle).pipe(map(x => {
            this.selectedBodyStyle = x;
            return x;
        }));
    }

    public createBodyStyle(bodyStyle: BodyStyleModel): Observable<BodyStyleModel>{
        return this.http.post<BodyStyleModel>(this.appSettings.baseApiUrl + "bodyStyle", bodyStyle).pipe(map(x => {
            this.selectedBodyStyle = x;
            return x;
        }));
    }
}