import { Component, OnInit } from '@angular/core';
import { FeatureService } from 'src/app/shared/services/feature.service';

@Component({
    selector: 'app-feature',
    templateUrl: 'feature.component.html'
})

export class FeatureComponent implements OnInit {
    constructor(public featureService: FeatureService) { }

    ngOnInit() { }
}