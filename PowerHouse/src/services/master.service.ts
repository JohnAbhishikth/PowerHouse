import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MasterDTO } from 'src/app/dto/MasterDTO';

@Injectable({
  providedIn: 'root'
})
export class MasterService {

  constructor(private http: HttpClient) { }

  url = "http://localhost:8888/master/"

  getMasterDetails(id: string): Observable<MasterDTO> {
    return this.http.get<MasterDTO>(this.url + 'id/' + id)
  }

  updateMasterDetails(masterDTO: MasterDTO) {
    return this.http.post(this.url + 'update', masterDTO)
  }

  createMasterDetails(master: MasterDTO) {
    return this.http.post(this.url + 'create', master)
  }

}
