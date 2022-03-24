
export class FeatureModel{
    public id: number;
    public name: string;

    constructor(init: Partial<FeatureModel>) {
        Object.assign(this, init);
    }
}