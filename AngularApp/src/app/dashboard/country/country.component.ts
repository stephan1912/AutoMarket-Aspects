import { Component, OnInit } from '@angular/core';
import { CountryService } from 'src/app/shared/services/country.service';

@Component({
    selector: 'app-country',
    templateUrl: 'country.component.html'
})

export class CountryComponent implements OnInit {
    constructor(public countryService: CountryService) { }

    ngOnInit() { }
}