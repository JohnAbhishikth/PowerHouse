import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DependentDTO } from 'src/app/dto/DependentDTO';
import { MasterDependentDTO } from 'src/app/dto/MasterDependentDTO';
import { TransactionDTO } from 'src/app/dto/TransactionDTO';
import { DependentSpendLimitDTO } from 'src/app/dto/DependentSpendLimitDTO';
import { HotListDTO } from 'src/app/dto/hotListDTO';
import { MasterDTO } from 'src/app/dto/MasterDTO';
import { Observable } from 'rxjs';
import { DependentAccountDTO } from 'src/app/dto/dependentAccountDTO';

@Injectable({
  providedIn: 'root'
})
export class DependentService {

  constructor(private http: HttpClient) { }

  url = "http://localhost:8888/dependent/"

  registerDependent(dependentDTO: DependentDTO) {
    return this.http.post(this.url + "register", dependentDTO)
  }

  updateDependentDetails(dependentDTO: DependentDTO) {
    return this.http.post(this.url + 'update', dependentDTO)
  }

  getDependentsById(dependentId: string) {
    return this.http.get<DependentDTO>(this.url + dependentId)
  }

  tagMasterAndDependent(masterDependentDTO: MasterDependentDTO) {
    return this.http.post(this.url + "joinMaster", masterDependentDTO)
  }

  setSpendLimit(dependentSpendLimitDTO: DependentSpendLimitDTO) {
    return this.http.post(this.url + "setSpendLimit", dependentSpendLimitDTO)
  }

  setHotList(hotListDTO: HotListDTO) {
    return this.http.post(this.url + "setHotlist", hotListDTO)
  }

  performTransaction(transactionDTO: TransactionDTO): Observable<any> {
    return this.http.post<any>(this.url + "transaction", transactionDTO)
  }

  getAllDependentsByMasterId(masterDTO: MasterDTO) {
    return this.http.post(this.url + "getAllDependents", masterDTO)
  }

  getAccountDetails(dependentId: string): Observable<DependentAccountDTO> {
    return this.http.get<DependentAccountDTO>(this.url + 'account/' + dependentId)
  }


}