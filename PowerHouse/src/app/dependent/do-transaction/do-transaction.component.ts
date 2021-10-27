import { Component, OnInit } from '@angular/core';
import { MerchantDTO } from 'src/app/dto/MerchantDTO';
import { TransactionDTO } from 'src/app/dto/TransactionDTO';
import { DependentService } from 'src/services/dependent.service';
import { MerchantService } from 'src/services/merchant.service';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/dto/LoginDTO';


@Component({
  selector: 'app-do-transaction',
  templateUrl: './do-transaction.component.html',
  styleUrls: ['./do-transaction.component.css']
})
export class DoTransactionComponent implements OnInit {

  userName: any
  merchantDTO: MerchantDTO[] = []
  merchantName: any
  transactionDTO!: any
  password!: string

  loginDTO!:LoginDTO
  // dependentId!: string  // = 'jack'
  routerState: any

  constructor(private merchantService: MerchantService, private dependentService: DependentService, private router: Router) {
    this.transactionDTO = new TransactionDTO

    let navigation = this.router.getCurrentNavigation()
    if (navigation!.extras.state) {
      this.routerState = navigation!.extras.state
      if (this.routerState) {
        this.loginDTO = this.routerState.loginDetails ? this.routerState.loginDetails : null
      }
    }
    // alert('Dep Id is: ' + this.dependentId)
  }

  ngOnInit(): void {
    this.getMerchants()
  }

  getMerchants() {
    this.merchantService.getAllMerchants().subscribe((data: any) => {
      this.merchantDTO = data
    })
  }

  returnToHome() {
    this.router.navigate(['/child/home'])
  }

  doTransaction() {
    this.transactionDTO.debitAccount = this.loginDTO.id

    this.dependentService.performTransaction(this.transactionDTO).subscribe(data => {

      if (data != null) {
        if (data.hasOwnProperty('status')) {
          var status: string = data.status
          if (status == 'success') {
            alert('transaction successful')
            this.returnToHome()
            return
          } else {
            console.log('status')
            alert(status)
            this.returnToHome()
            return
          }
        } else if (data.hasOwnProperty('cause')) {
          console.log('data.cause')
          console.log(data.message)
          alert(data.message)
          this.returnToHome()
          return
        } else {
          console.log('else')
          console.log(data)
        }
      } else {
        alert("Cant Transact.Try After Some Time")
        this.returnToHome()
        return
      }
    }, (err) => {
      console.error(err)
      alert("Failed to transact");
      this.returnToHome()
      return
    })
  }
}
/**
 *
 * dependent amount --
 *
 * Transaction Table should be updated
 *
 *
 *
 *
 *
 */