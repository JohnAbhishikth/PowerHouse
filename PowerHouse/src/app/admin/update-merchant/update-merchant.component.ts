import { Component, OnInit } from '@angular/core';
import { MerchantService } from 'src/services/merchant.service';
import { MerchantDTO } from 'src/app/dto/MerchantDTO';

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
  constructor(private merchantService: MerchantService) { }

  ngOnInit(): void { }

  editMerchant(merchantId: string, i: number) {
    this.buttons[i] = false;
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
    if (this.merchants != undefined)
      this.merchants = []
    this.merchantService.getAllMerchantsByName(this.merchantName).subscribe((data: MerchantDTO[]) => {
      if (data.length == 0) {
        this.errorMsg = 'No Merchants Found';
      } else {
        this.merchants = data;
        this.buttons = Array(data.length).fill(true);
      }
    })
  }
}
