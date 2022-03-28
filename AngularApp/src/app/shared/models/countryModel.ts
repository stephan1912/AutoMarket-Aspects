
export class CountryModel{
    public country_id: number;
    public name: string;

    constructor(init: Partial<CountryModel>) {
        Object.assign(this, init);
    }
}