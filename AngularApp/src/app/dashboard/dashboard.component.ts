import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-dashboard',
    templateUrl: 'dashboard.component.html',
    styleUrls: ['dashboard.component.scss']
})

export class DashboardComponent implements OnInit {

  
    private currentUrl = '';
  
    constructor(
      private router: Router
    ) {
  
      this.router.events.subscribe((route:any) => {
        this.currentUrl = route.url;
      });
  
    }
  
    ngOnInit(): void {
    }
  
}