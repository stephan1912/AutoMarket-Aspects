import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppSettings } from 'src/app/app.settings';
import { ModelObject } from '../models/modelObject';

@Injectable()
export class ModelService {
    public modelList: ModelObject[] = [];
    public selectedModel: ModelObject;
    public selectedModelChanged: Subject<ModelObject>;
    constructor(private appSettings: AppSettings, private http: HttpClient){
        this.selectedModelChanged = new Subject<ModelObject>();
    }
    

    public deleteModel(model: ModelObject): Observable<any>{
        return this.http.delete(this.appSettings.baseApiUrl + "model/" + model.model_id);
    }

    public getAllModels(brand_id: number): Observable<ModelObject[]>{
        return this.http.get<ModelObject[]>(this.appSettings.baseApiUrl + "brand/" +  brand_id + "/models").pipe(map(list => {
            this.modelList = list;
            return list;
        }));
    }

    public updateModel(model: any): Observable<ModelObject>{
        return this.http.put<ModelObject>(this.appSettings.baseApiUrl + "model", model).pipe(map(x => {
            this.selectedModel = x;
            return x;
        }));
    }

    public createModel(model: any): Observable<ModelObject>{
        return this.http.post<ModelObject>(this.appSettings.baseApiUrl + "model", model).pipe(map(x => {
            this.selectedModel = null;
            return x;
        }));
    }
    
}