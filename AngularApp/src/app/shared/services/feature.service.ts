import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppSettings } from 'src/app/app.settings';
import { FeatureModel } from '../models/featureModel';

@Injectable()
export class FeatureService {

    public featureList: FeatureModel[] = [];
    public selectedFeature: FeatureModel;

    public selectedFeatureChanged: Subject<FeatureModel>;

    constructor(private appSettings: AppSettings, private http: HttpClient){
        this.selectedFeatureChanged = new Subject<FeatureModel>();}
    

    public deleteFeature(feature: FeatureModel): Observable<any>{
        return this.http.delete(this.appSettings.baseApiUrl + "feature/" + feature.id);
    }

    public getAllFeatures(): Observable<FeatureModel[]>{
        return this.http.get<FeatureModel[]>(this.appSettings.baseApiUrl + "feature/all").pipe(map(list => {
            this.featureList = list;
            return list;
        }));
    }

    public updateFeature(feature: FeatureModel): Observable<FeatureModel>{
        return this.http.put<FeatureModel>(this.appSettings.baseApiUrl + "feature", feature).pipe(map(x => {
            this.selectedFeature = x;
            return x;
        }));
    }

    public createFeature(feature: FeatureModel): Observable<FeatureModel>{
        return this.http.post<FeatureModel>(this.appSettings.baseApiUrl + "feature", feature).pipe(map(x => {
            this.selectedFeature = x;
            return x;
        }));
    }
}