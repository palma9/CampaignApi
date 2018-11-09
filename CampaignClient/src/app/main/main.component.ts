import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../_services/auth.service';
import { CampaignService } from '../_services/campaign.service';
import { CampaignResponse } from '../_interfaces/campaign.interface';


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  campaigns: CampaignResponse[];
  nombre = localStorage.getItem('username');


  constructor(private authService: AuthService, private campaignService: CampaignService, private router: Router) {}

  ngOnInit() {
    if (this.authService.getToken() == null) {
      this.router.navigate(['/']);
    } else {
      this.showData();
    }
  }

  doLogout() {
    this.authService.removeLoginData();
    this.router.navigate(['/']);
  }

  showData() {
    this.campaignService.getCampaigns().subscribe(campaignList => {
      this.campaigns = campaignList;
    }, error => {
      console.log('Error. No recibe datos.');
    });
  }

  isAdmin() {
    if (localStorage.getItem('admin') === 'true') {
      return true;
    }
  }

}

