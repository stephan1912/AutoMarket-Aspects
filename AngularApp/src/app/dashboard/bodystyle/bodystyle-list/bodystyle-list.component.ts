
import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { BodyStyleModel } from 'src/app/shared/models/bodyStyleModel';
import { BodyStyleService } from 'src/app/shared/services/bodyStyle.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';

@Component({
    selector: 'app-bodyStyle-list',
    templateUrl: 'bodyStyle-list.component.html'
})

export class BodyStyleListComponent implements OnInit {
    constructor(public bodyStyleService: BodyStyleService, private customService: CustomLoaderService, 
        private changeDetector: ChangeDetectorRef, private router: Router) { }

    @ViewChild('bodyStyleNameFilter') bodyStyleNameFilter: HTMLInputElement;
    detectChange(){
        this.changeDetector.detectChanges();
        console.log('change_' + this.bodyStyleNameFilter.value);
    }

    ngOnInit() {
        this.customService.start();
        this.bodyStyleService.getAllBodyStyles().subscribe(r => {
            this.customService.stop();
        }, this.customService.errorFromResp);
     }

     setSelectedBodyStyle(bodyStyle: BodyStyleModel){
         this.bodyStyleService.selectedBodyStyle = bodyStyle;
         this.bodyStyleService.selectedBsChanged.next(bodyStyle);
     }

     deleteBodyStyle(bodyStyle: BodyStyleModel){
        this.bodyStyleService.deleteBodyStyle(bodyStyle).subscribe(r => {
                this.customService.success('Caroseria a fost stearsa!', 'Success');
                  this.bodyStyleService.getAllBodyStyles().subscribe(resp => {
                      this.customService.stop();
                  }, this.customService.errorFromResp)
        }, this.customService.errorFromResp)
     }

     seeModels(bodyStyle: BodyStyleModel){
        this.bodyStyleService.selectedBodyStyle = bodyStyle;
        this.router.navigate(['dashboard/model']);
     }
}