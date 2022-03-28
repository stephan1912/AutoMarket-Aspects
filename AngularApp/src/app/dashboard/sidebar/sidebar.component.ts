import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { Router } from '@angular/router';
import { CustomLoaderService } from 'src/app/shared/services/customLoader.service';

@Component({
    selector: 'app-sidebar',
    templateUrl: 'sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})

export class SidebarComponent implements OnInit {
    constructor(private userService: UserService, private router: Router, private customService: CustomLoaderService) { }

    ngOnInit() {
        this.isAdmin = this.userService.IsUserAdmin();
     }

    public isAdmin: boolean = false;

    SignOut(){
        this.customService.start();
        this.userService.LogoutUser().subscribe(response => {
            this.customService.success('V-ati deconectat cu succes!', 'Success');
            this.customService.stop();
            this.router.navigateByUrl("/home");
        }, e => {
            this.customService.stop();
            this.customService.success('V-ati deconectat cu succes!', 'Success');
            this.router.navigateByUrl("/home"); 
        });
      }
}