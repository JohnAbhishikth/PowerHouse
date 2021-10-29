import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Subscription, interval } from 'rxjs';
import { TransactionService } from 'src/services/transaction.service';
import { PendingStatusDTO } from '../dto/PendingStatusDTO';
import { NotificationsService } from 'angular2-notifications';
import { LoginService } from 'src/services/login.service';
import { TransactionDTO } from '../dto/TransactionDTO';
import { LoginDTO } from '../dto/LoginDTO';


@Component({
  selector: 'app-parent-dashboard',
  templateUrl: './parent-dashboard.component.html',
  styleUrls: ['./parent-dashboard.component.css']
})
export class ParentDashboardComponent implements OnInit {

  pendingStatusDTO: PendingStatusDTO
  pendingStatusList: PendingStatusDTO[] = []

  transactionDto: TransactionDTO

  loginDTO!: LoginDTO
  subscription!: Subscription
  userName: any
  componentName!: string

  transList: number[] = []

  alertValue: number = 0

  constructor(private notificationService: NotificationsService, private transactionService: TransactionService, private loginService: LoginService, private router: Router, private route: ActivatedRoute) {
    this.pendingStatusDTO = new PendingStatusDTO
    this.transactionDto = new TransactionDTO
  }

  ngOnInit(): void {
    this.loginService.getLoginDetails().subscribe((data: LoginDTO) => {
      this.loginDTO = data
      this.loginDTO.loginType = 'master'
      this.pendingStatusDTO.masterId = data.id
      this.userName = data.name
    })

    const source = interval(5000);
    this.subscription = source.subscribe(val => this.getPedingTransactions());

  }

  private btn(debitId: string, creditId: string, amount: number, transId: number) {
    const toast = this.notificationService.info('Transaction', 'By: ' + debitId + '\n to ' + creditId + 'Amount: Rs.' + amount + '/-\nClick to accept or reject', {
      position: ["bottom", "left"],
      animate: "scale",
      timeOut: 3000,
      showProgressBar: true
    })

    toast.click!.subscribe((event) => {
      var res = confirm("Accept Transaction?")
      if (res) {
        this.transactionDto.transactionId = transId
        this.transactionDto.status = 'SUCCESS'
        this.transactionService.updateTransactionStatus(this.transactionDto).subscribe((data: any) => {
          console.log(data)
        })
        alert('Transaction with id :' + transId + ' is Successful')
      } else {
        this.transactionDto.transactionId = transId
        this.transactionDto.status = 'REJECTED'
        this.transactionService.updateTransactionStatus(this.transactionDto).subscribe((data: any) => {
          console.log(data)
        })
        alert('Transaction with id :' + transId + ' is Rejected')
      }
    })
  }

  playSound() {
    let audio = new Audio
    audio.src = "../../../assets/notification.mp3"
    audio.load()
    audio.play()
  }

  getTransactionAlert() {
    var transDtoList: TransactionDTO[] = this.pendingStatusDTO.transactionDTO
    // if (transDtoList != undefined && transDtoList.length > 0) {
    if (this.transList.length > 0) {

      transDtoList.forEach((value) => {
        this.btn(value.debitAccount, value.creditAccount, value.transactionAmount, value.transactionId)
      })

    } else {
      this.notificationService.success('No Alerts', 'No Pending Notifications', {
        position: ["bottom", "left"],
        timeOut: 2500,
        animate: "scale",
        showProgressBar: true
      })
    }
  }

  private getPedingTransactions() {
    if (this.pendingStatusDTO.masterId != undefined || this.pendingStatusDTO.masterId != null) {
      this.transactionService.pendingTransactions(this.pendingStatusDTO).subscribe((data: any) => {
        if (data != null) {
          this.pendingStatusDTO = data
          if (this.pendingStatusDTO.transactionDTO.length > 0) {
            this.alertValue = this.pendingStatusDTO.transactionDTO.length

            this.pendingStatusDTO.transactionDTO.forEach(value => {
              if (!this.transList.includes(value.transactionId)) {
                this.playSound()
                this.transList.push(value.transactionId)
              }
            })

          } else {
            this.alertValue = 0
            this.transList = []
          }

          /**
           * 0 alerts  
           * if alert=>play sound()
           */
        }
      })
    }
  }

  createDep() {
    this.componentName = 'Create Dependent'
    this.router.navigate(['createDependent'], {
      relativeTo: this.route,
      state: {
        // frontEnd: JSON.stringify({ framwork: 'Angular', version: '9' }),
        loginDetails: this.loginDTO
      }
    })
  }

  home() {
    this.componentName = ''
    this.router.navigate(['home'], {
      relativeTo: this.route,
      state: { loginDetails: this.loginDTO }
    })
  }

  transaction() {
    this.componentName = 'Transaction Details'
    // alert('from parent : ' + this.masterID)
    this.router.navigate(['transaction'], {
      relativeTo: this.route,
      state: { loginDetails: this.loginDTO }
    })
  }

  allDependents() {
    this.componentName = 'Dependents Details'
    this.router.navigate(['allDependents'], {
      relativeTo: this.route,
      state: { loginDetails: this.loginDTO }
    })
  }

  selfDetails() {
    this.componentName = 'Self Details'
    this.router.navigate(['self'], {
      relativeTo: this.route,
      state: { loginDetails: this.loginDTO }
    })
  }

  // logout() {
  //   sessionStorage.clear()
  //   this.router.navigateByUrl("/").then(() => {
  //     window.location.reload();
  //   })
  // }
}
