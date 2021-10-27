import { Component, OnInit } from '@angular/core';
import { DependentDTO } from 'src/app/dto/DependentDTO';
import { MasterDTO } from 'src/app/dto/MasterDTO';
import { TransactionDTO } from 'src/app/dto/TransactionDTO';
import { DependentService } from 'src/services/dependent.service';
import { TransactionService } from 'src/services/transaction.service';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/dto/LoginDTO';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {
  dto!: TransactionDTO
  dtoList: TransactionDTO[] = []
  master: MasterDTO
  dependentDTO!: DependentDTO[]
  dependentId: any
  error: boolean = false
  errorMsg: string = ''

  loginDTO!: LoginDTO

  routeState: any

  constructor(private transactionService: TransactionService, private dependentService: DependentService, private router: Router) {
    this.dto = new TransactionDTO
    this.master = new MasterDTO

    let navigation = this.router.getCurrentNavigation()
    if (navigation!.extras.state) {
      this.routeState = navigation!.extras.state
      if (this.routeState) {
        this.loginDTO = this.routeState.loginDetails ? this.routeState.loginDetails : null;
        // alert('from transaction : ' + this.loginDTO.loginType)
      }
    }
  }

  ngOnInit(): void {
    if (this.loginDTO.loginType == 'master') {
      this.getAllDependentsByMasterId()
    } else {
      // console.log(this.dto)
      // console.log("middle")
      // console.log(this.loginDTO.id)
      this.dto.dependentId = this.loginDTO.id
      this.getTransaction(this.dto)
    }
  }

  isParent(): boolean {
    return this.loginDTO.loginType == 'master' ? true : false
  }

  getTransaction(dto: TransactionDTO) {
    if (this.dto.dependentId == undefined) {
      this.error = true
      this.errorMsg = 'Id cant be null'
      return
    }

    this.transactionService.getAllTransactionsByDepId(dto).subscribe((data: any) => {
      this.dtoList = data
      if (this.dtoList.length > 0) {
        // console.log(this.dtoList)
      } else {
        this.error = true
        this.errorMsg = 'No Transactions found for id: "' + this.dto.dependentId + '"'
      }
    }, (err) => {
      this.error = true
    })
  }

  private getAllDependentsByMasterId() {
    this.master.id = this.loginDTO.id
    this.dependentService.getAllDependentsByMasterId(this.master).subscribe((data: any) => {
      // if (data == null) {
      //   this.dependentDTO.push(new DependentDTO('No Dependents'))
      // }
      this.dependentDTO = data
    })
  }

  clearErrorMsg() {
    this.errorMsg = ''
  }

  isDependentPresent() {
    if (this.dependentDTO != undefined) {
      return this.dependentDTO.length > 0
    }
    return false
  }
}
