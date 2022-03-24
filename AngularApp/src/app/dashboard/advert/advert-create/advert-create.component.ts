import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdvertModel } from 'src/app/shared/models/advertModel';
import { BodyStyleModel } from 'src/app/shared/models/bodyStyleModel';
import { BrandModel } from 'src/app/shared/models/brandModel';
import { CountryModel } from 'src/app/shared/models/countryModel';
import { FeatureModel } from 'src/app/shared/models/featureModel';
import { ModelObject } from 'src/app/shared/models/modelObject';
import { AdvertService } from 'src/app/shared/services/advert.service';
import { BodyStyleService } from 'src/app/shared/services/bodyStyle.service';
import { BrandService } from 'src/app/shared/services/brand.service';
import { CountryService } from 'src/app/shared/services/country.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';
import { FeatureService } from 'src/app/shared/services/feature.service';

@Component({
    selector: 'app-advert-create',
    templateUrl: 'advert-create.component.html'
})

export class AdvertCreateComponent implements OnInit {
    public advertForm: FormGroup;
    public otherErrorsDiv = null;
    public otherErrorsDivPassword = null;
    public otherErrorsDivTwofa = null;


    public brandsList: BrandModel[];
    public modelListToShow: ModelObject[];
    public bsList: BodyStyleModel[];
    public countryList: CountryModel[];
    public featureList: FeatureModel[];

    constructor(public advertService: AdvertService, private formBuilder: FormBuilder, private router: Router,
        private customService: CustomLoaderService, public brandService: BrandService, public bsService: BodyStyleService,
        public changeDetector: ChangeDetectorRef, public countryService: CountryService, public featureService: FeatureService) { }


    public initDone = false;
    ngOnInit() {
    this.initDone = false;
        this.brandService.getAllBrands().subscribe(brands => {
            this.brandsList = brands;
            this.bsService.getAllBodyStyles().subscribe(bss => {
                this.bsList = bss;
                this.countryService.getAllCountrys().subscribe(countries => {
                    this.countryList = countries;
                    this.featureService.getAllFeatures().subscribe(features => {
                        this.featureList = features;
                        this.reinitForms();

                        if (this.advertService.SavedAdvert != null) {
                            let cBrand = brands.find(b => b.models.find(m => m.model_id == this.advertService.SavedAdvert.model.model_id) != undefined);
                            this.modelListToShow = cBrand.models;
                            this.changeDetector.detectChanges();
                            (document.getElementById('brandSelect') as any).value = this.selectedBrand =  cBrand.brand_id; 
                            (document.getElementById('modelSelect') as any).value = this.selectedModel = this.advertService.SavedAdvert.model.model_id;
                            (document.getElementById('bsSelect') as any).value = this.selectedBs = this.advertService.SavedAdvert.bodyStyleDTO.bs_id;

                            (document.getElementById('gearboxType') as any).value = this.selectedGearbox = this.advertService.SavedAdvert.gearboxType;
                            (document.getElementById('drivetrain') as any).value = this.selectedDrivetrain = this.advertService.SavedAdvert.drivetrain;
                            (document.getElementById('fuel') as any).value = this.selectedFuel = this.advertService.SavedAdvert.fuel;
                            (document.getElementById('countrySelect') as any).value = this.selectedCountry = this.advertService.SavedAdvert.countryDTO.country_id;
                            (document.getElementById('registered') as any).checked = this.advertService.SavedAdvert.registered;
                            (document.getElementById('serviceDocs') as any).checked = this.advertService.SavedAdvert.serviceDocs;

                            this.addedFeatures = this.advertService.SavedAdvert.features;
                            this.addedFeatures.forEach(f => {
                                this.featureList.splice(this.featureList.findIndex(x => x.id == f.id), 1);
                            });
                            this.changeDetector.detectChanges();
                            
                        }
                        this.customService.stop();
                    }, this.customService.errorFromResp);

                }, this.customService.errorFromResp);

            }, this.customService.errorFromResp);

        }, this.customService.errorFromResp);
    }
    get f() { return this.advertForm.controls; }
    checkInput(input: any) {
        return input.invalid && (input.dirty || input.touched);
    }

    reinitForms() {
        this.advertForm = this.formBuilder.group({
            title: [this.advertService.SavedAdvert?.title, [Validators.required]],
            vin: [this.advertService.SavedAdvert?.vin, [Validators.required]],
            description: [this.advertService.SavedAdvert?.description, [Validators.required, Validators.minLength(100), Validators.maxLength(1000)]],
            kilometers: [this.advertService.SavedAdvert?.km, [Validators.required, Validators.min(0)]],
            year: [this.advertService.SavedAdvert?.year, [Validators.required, Validators.min(1900), Validators.max(2021)]],
            registered: [this.advertService.SavedAdvert?.registered, [Validators.required]],
            serviceDocs: [this.advertService.SavedAdvert?.serviceDocs, [Validators.required]],
            brandSelect: ['', [Validators.required]],
            modelSelect: ['', [Validators.required]],
            feature: ['', [Validators.required]],
            bsSelect: ['', [Validators.required]],
            gearboxType: ['', [Validators.required]],
            drivetrain: ['', [Validators.required]],
            fuel: ['', [Validators.required]],
            horsePower: [this.advertService.SavedAdvert?.horsePower, [Validators.required, Validators.min(0)]],
            engineCap: [this.advertService.SavedAdvert?.engineCap, [Validators.required, Validators.min(0)]],
            price: [this.advertService.SavedAdvert?.price, [Validators.required, Validators.min(0)]],
            countrySelect: ['', [Validators.required]]
        });
        this.initDone = true;
        // if(this.advertService.SavedAdvert )
        // this.advertService.SavedAdvert = null;
    }

    public atLeastOneEdit: boolean = false;
    onSubmit() {
        this.nopicError = false;
        this.atLeastOneEdit = false;
        this.advertForm.markAllAsTouched();
        let shouldStop = false;
        if (this.advertService.SavedAdvert == null) {
            if (this.advertForm.invalid) {
                shouldStop = true;
            }

            if (this.pictureFiles == null || this.pictureFiles.length == 0) {
                this.nopicError = true;
                shouldStop = true;
            }

            if (this.selectedModel == -1 || this.selectedBrand == -1 || this.selectedBs == -1 || this.selectedCountry == -1 || this.selectedDrivetrain == 'EMPTY' ||
                this.selectedFuel == 'EMPTY' || this.selectedGearbox == 'EMPTY') {
                shouldStop = true;
            }

            if (this.addedFeatures == null || this.addedFeatures.length == 0) {
                shouldStop = true;
            }
            if(shouldStop) return;
        }

        if (this.advertService.SavedAdvert != null) {
            this.customService.start();
            this.advertService.UpdateAdvert(<any>{
                advert_id: this.advertService.SavedAdvert.advert_id,
                country_id: this.selectedCountry,
                model_id: this.selectedModel,
                bodyStyle_id: this.selectedBs,
                gearboxType: this.selectedGearbox,
                drivetrain: this.selectedDrivetrain,
                fuel: this.selectedFuel,
                features: this.addedFeatures.map(f => f.id),
                title: this.f.title.value,
                vin: this.f.vin.value,
                description: this.f.description.value,
                km: this.f.kilometers.value == '' ? -1 : this.f.kilometers.value,
                year: this.f.year.value == '' ? -1 : this.f.year.value,
                registered: (document.getElementById('registered') as any).checked,
                serviceDocs: (document.getElementById('serviceDocs') as any).checked,
                horsePower: this.f.horsePower.value == '' ? -1 : this.f.horsePower.value,
                engineCap: this.f.engineCap.value == '' ? -1 : this.f.engineCap.value,
                price: this.f.price.value == '' ? -1 : this.f.price.value
            }).subscribe(r => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Anuntul a fost actualizat!', 'Succes');
                this.router.navigate(['dashboard/main']);
            }, this.customService.errorFromResp);
        }
        else {
            this.customService.start();
            this.advertService.CreateAdvert(<any>{
                country_id: this.selectedCountry,
                model_id: this.selectedModel,
                bodyStyle_id: this.selectedBs,
                gearboxType: this.selectedGearbox,
                drivetrain: this.selectedDrivetrain,
                fuel: this.selectedFuel,
                features: this.addedFeatures.map(f => f.id),
                title: this.f.title.value,
                vin: this.f.vin.value,
                description: this.f.description.value,
                km: this.f.kilometers.value,
                year: this.f.year.value,
                registered: (document.getElementById('registered') as any).checked,
                serviceDocs: (document.getElementById('serviceDocs') as any).checked,
                horsePower: this.f.horsePower.value,
                engineCap: this.f.engineCap.value,
                price: this.f.price.value
            }, this.pictureFiles.map(pf => pf.file)).subscribe(response => {
                this.otherErrorsDiv = "";
                this.reinitForms();
                this.customService.success('Anuntul a fost creat!', 'Succes');
                this.router.navigate(['dashboard/main']);
            }, this.customService.errorFromResp);
        }
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

    selectedDrivetrain: string = 'EMPTY';
    drivetrainChanged(event) {
        this.selectedDrivetrain = event;
    }

    selectedFuel: string = 'EMPTY';
    fuelChanged(event) {
        this.selectedFuel = event;
    }

    selectedCountry: number = -1;
    countryChanged(event) {
        this.selectedCountry = event;
    }

    public nopicError = false;
    public pictureFiles: any[] = null;
    public addedImages: boolean = false;
    processFile(image: any) {
        this.pictureFiles = [];
        for (let i = 0; i < image.files.length; i++) {
            this.pictureFiles.push(<any>{
                id: i,
                file: image.files[i]
            });
        }
        this.addedImages = true;
    }

    deleteFile(index) {
        console.log(this.pictureFiles[index]);
        this.pictureFiles.splice(index, 1);
        if (this.pictureFiles.length == 0) this.addedImages = false;
    }
    showFileBox() {
        document.getElementById('carPicInput').click();
    }


    addedFeatures: FeatureModel[] = [];
    featureChanged(event) {
        let selected = this.featureList.find(f => f.id == event);
        this.addedFeatures.push(selected);
        this.featureList.splice(this.featureList.findIndex(f => f.id == event), 1);
        (document.getElementById('feature') as any).value = -1;
    }
    deleteFeature(id) {
        let selected = this.addedFeatures.find(f => f.id == id);
        this.featureList.push(selected);
        this.addedFeatures.splice(this.addedFeatures.findIndex(f => f.id == id), 1);
    }
}