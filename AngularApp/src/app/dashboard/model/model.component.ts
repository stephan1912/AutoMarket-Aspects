import { Component, OnInit } from '@angular/core';
import { BrandService } from 'src/app/shared/services/brand.service';

@Component({
    selector: 'app-model',
    templateUrl: 'model.component.html'
})

export class ModelComponent implements OnInit {

    constructor(public brandService: BrandService) { }

    ngOnInit() { }
}