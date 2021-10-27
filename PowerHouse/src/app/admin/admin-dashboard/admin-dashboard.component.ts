import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/services/login.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  userName!: string

  constructor(private loginService: LoginService, private route: ActivatedRoute, private router: Router) {

    this.loginService.getLoginDetails().subscribe(data => {
      this.userName = data.id
    })
  }

  ngOnInit(): void { }

  home() {
    this.router.navigate(['home'], { relativeTo: this.route })
  }

  createParent() {
    this.router.navigate(['parent'], { relativeTo: this.route })
  }

  createMerchant() {
    this.router.navigate(['merchant'], { relativeTo: this.route })
  }

  updateMerchant() {
    this.router.navigate(['updateMerchant'], { relativeTo: this.route })
  }

}
