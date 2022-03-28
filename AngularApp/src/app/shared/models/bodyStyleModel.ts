
export class BodyStyleModel{
    public bs_id: number;
    public name: string;
    public description: string;

    constructor(init: Partial<BodyStyleModel>) {
        Object.assign(this, init);
    }
}