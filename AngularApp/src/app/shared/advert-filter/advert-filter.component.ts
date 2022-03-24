import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { BodyStyleModel } from '../models/bodyStyleModel';
import { BrandModel } from '../models/brandModel';
import { ModelObject } from '../models/modelObject';
import { AdvertService } from '../services/advert.service';
import { BodyStyleService } from '../services/bodyStyle.service';
import { BrandService } from '../services/brand.service';
import { CustomLoaderService } from '../services/customLoader.service';

@Component({
    selector: 'app-advert-filter',
    templateUrl: 'advert-filter.component.html'
})

export class AdvertFilterComponent implements OnInit {
    public brandsList: BrandModel[];
    public modelListToShow: ModelObject[];
    public bsList: BodyStyleModel[];
    public filterForm: FormGroup;
   
    constructor(public advertService: AdvertService, private formBuilder: FormBuilder, private router: Router,
        private customService: CustomLoaderService, public brandService: BrandService, public bsService: BodyStyleService,
        public changeDetector: ChangeDetectorRef) { }


    public initDone = false;
    ngOnInit() {
        this.customService.start();
        this.initDone = false;
            this.brandService.getAllBrands().subscribe(brands => {
                this.brandsList = brands;
                this.bsService.getAllBodyStyles().subscribe(bss => {
                    this.bsList = bss;
                    this.reinitForms();
                }, this.customService.errorFromResp);
            }, this.customService.errorFromResp);
        };

    reinitForms() {
        this.selectedBrand = -1;
        this.selectedBs = -1;
        this.selectedModel = -1;
        this.selectedGearbox = 'EMPTY';
        this.filterForm = this.formBuilder.group({
            brandSelect: ['', []],
            modelSelect: ['', []],
            bsSelect: ['', []],
            gearboxType: ['', []],
            horsePowerMin: ['', []],
            horsePowerMax: ['', []],
            yearMin: ['', []],
            yearMax: ['', []],
            kmMin: ['', []],
            kmMax: ['', []],
            priceMin: ['', []],
            priceMax: ['', []]

        });
        this.initDone = true;
        this.customService.stop();
    }

    onSubmit(){
        this.customService.start();
        this.advertService.GetAllAdvertsFiltered(<any>{
            brand: this.selectedBrand,
            model: this.selectedModel,
            bs: this.selectedBs,
            gearbox: this.selectedGearbox,
            kmMin: this.filterForm.controls.kmMin.value == '' ? -1 : this.filterForm.controls.kmMin.value,
            kmMax: this.filterForm.controls.kmMax.value == '' ? -1 : this.filterForm.controls.kmMax.value,
            yearMin: this.filterForm.controls.yearMin.value == '' ? -1 : this.filterForm.controls.yearMin.value,
            yearMax: this.filterForm.controls.yearMax.value == '' ? -1 : this.filterForm.controls.yearMax.value,
            horsePowerMin: this.filterForm.controls.horsePowerMin.value == '' ? -1 : this.filterForm.controls.horsePowerMin.value,
            horsePowerMax: this.filterForm.controls.horsePowerMax.value == '' ? -1 : this.filterForm.controls.horsePowerMax.value,
            priceMin: this.filterForm.controls.priceMin.value == '' ? -1 : this.filterForm.controls.priceMin.value,
            priceMax: this.filterForm.controls.priceMax.value == '' ? -1 : this.filterForm.controls.priceMax.value,
        }).subscribe(_ => {
            this.customService.stop();
        }, this.customService.errorFromResp); 
    }

    selectedBrand: number = -1;
    brandChanged(event) {
        this.selectedBrand = event;
        this.modelListToShow = this.brandsList.find(b => b.brand_id == event).models;
        (document.getElementById('modelSelect') as any).value = -1;
    }

    selectedModel: number = -1;
    modelChanged(event) {
        this.selectedModel = event
    }

    selectedBs: number = -1;
    bsChanged(event) {
        this.selectedBs = event;
    }

    selectedGearbox: string = 'EMPTY';
    gearboxTypeChanged(event) {
        this.selectedGearbox = event;
    }
}