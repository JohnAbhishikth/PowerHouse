import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { interval } from 'rxjs';
import { PendingStatusDTO } from 'src/app/dto/PendingStatusDTO';
import { TransactionDTO } from 'src/app/dto/TransactionDTO';


@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient) { }

  url = "http://localhost:8888/transaction/"

  getAllTransactionsByDepId(transactionDTO: TransactionDTO) {
    return this.http.post(this.url + "getByDepId", transactionDTO)
  }

  pendingTransactions(pendingStatusDTO: PendingStatusDTO) {
    return this.http.post(this.url + "pending", pendingStatusDTO)
  }

  //  getPendingTransaction(pendingStatusDTO: PendingStatusDTO) {
  //   const source = interval(2000)
  //   const subscribe = source.subscribe(()=>{
  //     this.pendingTransactions(pendingStatusDTO)
  //   })
  // }

  updateTransactionStatus(transactionDTO: TransactionDTO) {
    return this.http.post(this.url + "updateStatus", transactionDTO)
  }
}
