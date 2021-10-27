import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MerchantDTO } from 'src/app/dto/MerchantDTO';
import { DependentMerchantDTO } from 'src/app/dto/DependentMerchantDTO';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class MerchantService {

  constructor(private http: HttpClient) { }

  url = "http://localhost:8888/merchant/"

  createMerchant(merchant: MerchantDTO) {
    return this.http.post(this.url + "create", merchant)
  }

  getAllMerchants(): Observable<MerchantDTO[]> {
    return this.http.get<MerchantDTO[]>(this.url + "getAll")
  }

  getAllMerchantType() {
    return this.http.get(this.url + "getAllMerchantType")
  }

  getAllMerchantsOfType(dependentMerchantDTO: DependentMerchantDTO) {
    return this.http.post(this.url + "getAllMerchantsofType", dependentMerchantDTO)
  }

  getAllMerchantsByName(merchantName: string) {
    return this.http.get<MerchantDTO[]>(this.url + merchantName)
  }
  updateMerchantDetails(merchant: MerchantDTO) {
    return this.http.post(this.url + "update", merchant)
  }

  deleteMerchant(merchantId:string){
      
  }
}