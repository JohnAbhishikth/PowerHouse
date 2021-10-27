import { Component, OnInit } from '@angular/core';
import { MerchantService } from 'src/services/merchant.service';
import { MerchantDTO } from 'src/app/dto/MerchantDTO';
import { LoginService } from 'src/services/login.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-update-merchant',
  templateUrl: './update-merchant.component.html',
  styleUrls: ['./update-merchant.component.css']
})
export class UpdateMerchantComponent implements OnInit {

  merchantName!: string
  merchants: MerchantDTO[] = []
  errorMsg!: string
  buttons: any
  userName!: string

  constructor(private merchantService: MerchantService, private loginService: LoginService, private router: Router) {
    this.loginService.getLoginDetails().subscribe(data => {
      this.userName = data.id
    })
  }

  ngOnInit(): void {
    if (!this.canProceed()) {
      alert('LoginIn to proceed Further.')
      this.router.navigateByUrl('home')
    }
  }

  canProceed(): boolean {
    if (this.userName == undefined || this.userName == '')
      return false
    return true
  }

  editMerchant(merchantId: string, i: number) {
    this.buttons[i] = false;
  }

  clearMsg() {
    this.errorMsg = ''
  }

  saveMerchant(merchant: MerchantDTO, i: number) {
    this.buttons[i] = true;

    this.merchantService.updateMerchantDetails(merchant).subscribe(data => {
      if (data == null || data == undefined) {
        alert('Update Successful')
      }
      else {
        alert('Could not Update')
      }
    })
  }

  deleteMerchant(merchantId: string) {
    this.merchants = this.merchants.filter(merchant => {
      return merchant.merchantId != merchantId
    })
    alert('Merchant deleted : ' + merchantId)
  }

  getMerchant() {
    var obs: Observable<any>
    if (this.merchants != undefined)
      this.merchants = []
    if (this.merchantName == '' || this.merchantName == undefined) {
      obs = this.merchantService.getAllMerchants()
    } else {
      obs = this.merchantService.getAllMerchantsByName(this.merchantName)
    }
    obs.subscribe((data: MerchantDTO[]) => {
      if (data.length == 0) {
        this.errorMsg = 'No Merchants Found';
      } else {
        this.merchants = data;
        this.buttons = Array(data.length).fill(true);
      }
    })
  }
}
