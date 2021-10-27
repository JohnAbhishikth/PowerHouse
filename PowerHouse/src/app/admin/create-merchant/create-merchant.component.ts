import { Component, OnInit } from '@angular/core';
import { MerchantDTO } from 'src/app/dto/MerchantDTO';
import { MerchantService } from 'src/services/merchant.service';
import { LoginService } from 'src/services/login.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { NotificationsService } from 'angular2-notifications';

@Component({
  selector: 'app-create-merchant',
  templateUrl: './create-merchant.component.html',
  styleUrls: ['./create-merchant.component.css']
})
export class CreateMerchantComponent implements OnInit {
  merchant!: MerchantDTO
  userName!: string;
  errorMsg: any
  merchantTypes: any

  constructor(private notificationService: NotificationsService, private httpClient: HttpClient, private merchantService: MerchantService, private loginService: LoginService, private router: Router) {
    this.merchant = new MerchantDTO

    this.loginService.getLoginDetails().subscribe(data => {
      this.userName = data.id
    })
  }

  ngOnInit(): void {
    this.httpClient.get('assets/merchantTypes.json').subscribe(data => {
      this.merchantTypes = data
    })
  }

  canProceed(): boolean {
    if (this.userName == undefined || this.userName == '')
      return false
    return true
  }

  saveDetails() {
    if (this.canProceed()) {
      this.merchantService.createMerchant(this.merchant).subscribe((data: any) => {
        if (data !== null) {
          this.errorMsg = data.message
          console.log(data)
        } else {
          this.notificationService.success('Success', 'Parent Created Successfully', {
            position: ["bottom", "right"],
            timeOut: 1400,
            animate: "scale",
            showProgressBar: true,
            clickToClose: false
          })
          setTimeout(() => {
            this.router.navigate(['/admin/home'])
          }, 1400);
          this.router.navigate(['/admin/home'])
        }
      })
    } else {
      alert('Login to Create new Merchant')
      this.router.navigateByUrl('home')
    }
  }
}
