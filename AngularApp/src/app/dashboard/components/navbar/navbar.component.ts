import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
    selector: 'app-navbar',
    templateUrl: 'navbar.component.html'
})

export class NavBarComponent implements OnInit {
    
    @Input()
    public Route: string = '';
    @Input()
    public BackButtonValue: string = '';
    @Input()
    public ShowNewAdvert: boolean = false;
    
    constructor(public userService: UserService) { }

    ngOnInit() { }
}