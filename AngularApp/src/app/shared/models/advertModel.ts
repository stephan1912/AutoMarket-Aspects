import { BodyStyleModel } from "./bodyStyleModel";
import { CommentModel } from "./commentModel";
import { CountryModel } from "./countryModel";
import { FeatureModel } from "./featureModel";
import { ModelObject } from "./modelObject";

export class AdvertModel{
    public advert_id: number;
    public countryDTO: CountryModel;
    public bodyStyleDTO: BodyStyleModel;
    public user_id: number;
    public model: ModelObject;
    public comments: CommentModel[];
    public features: FeatureModel[];

    public title: string;
    public description: string;
    public createdAt: Date;

    public km: number;
    public year: number;
    public registered: boolean;
    public serviceDocs: boolean;
    public vin: string;
    public horsePower: number;
    public engineCap: number;
    public price: number;

    public gearboxType: string;
    public drivetrain: string;
    public fuel: string;

    public pictures: string[];
    
    constructor(init: Partial<AdvertModel>) {
        Object.assign(this, init);
    }
}