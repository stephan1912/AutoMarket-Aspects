import { Component, OnInit } from '@angular/core';
import { BodyStyleService } from 'src/app/shared/services/bodyStyle.service';

@Component({
    selector: 'app-bodyStyle',
    templateUrl: 'bodyStyle.component.html'
})

export class BodyStyleComponent implements OnInit {
    constructor(public bodyStyleService: BodyStyleService) { }

    ngOnInit() { }
}