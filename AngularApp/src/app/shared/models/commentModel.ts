
export class CommentModel{
    public country_id: number;
    public name: string;

    constructor(init: Partial<CommentModel>) {
        Object.assign(this, init);
    }
}