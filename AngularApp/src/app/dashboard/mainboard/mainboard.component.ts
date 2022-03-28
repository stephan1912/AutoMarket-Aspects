import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/shared/services/user.service';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';

@Component({
    selector: 'app-mainboard',
    templateUrl: 'mainboard.component.html',
    styleUrls: ['./mainboard.component.scss']
})

export class MainBoardComponent implements OnInit {
    constructor() 
    {
    }

    ngOnInit() {
    }
}