
import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { FeatureModel } from 'src/app/shared/models/featureModel';
import { FeatureService } from 'src/app/shared/services/feature.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';
import { features } from 'process';

@Component({
    selector: 'app-feature-list',
    templateUrl: 'feature-list.component.html'
})

export class FeatureListComponent implements OnInit {
    constructor(public featureService: FeatureService, private customService: CustomLoaderService, 
        private changeDetector: ChangeDetectorRef, private router: Router) { }

    @ViewChild('featureNameFilter') featureNameFilter: HTMLInputElement;
    detectChange(){
        this.changeDetector.detectChanges();
    }

    ngOnInit() {
        this.customService.start();
        this.featureService.getAllFeatures().subscribe(r => {
            this.customService.stop();
        }, this.customService.errorFromResp);
     }

     setSelectedFeature(feature: FeatureModel){
         this.featureService.selectedFeature = feature;
         this.featureService.selectedFeatureChanged.next(feature);
     }

     deleteFeature(feature: FeatureModel){
        this.featureService.deleteFeature(feature).subscribe(r => {
                this.customService.success('Optiunea a fost stearsa!', 'Success');
                  this.featureService.getAllFeatures().subscribe(resp => {
                      this.customService.stop();
                  }, this.customService.errorFromResp)
        }, this.customService.errorFromResp)
     }

     seeModels(feature: FeatureModel){
        this.featureService.selectedFeature = feature;
        this.router.navigate(['dashboard/model']);
     }
}