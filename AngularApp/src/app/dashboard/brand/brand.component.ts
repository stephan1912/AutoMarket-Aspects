import { Component, OnInit } from '@angular/core';
import { BrandService } from 'src/app/shared/services/brand.service';

@Component({
    selector: 'app-brand',
    templateUrl: 'brand.component.html'
})

export class BrandComponent implements OnInit {
    constructor(public brandService: BrandService) { }

    ngOnInit() { }
}