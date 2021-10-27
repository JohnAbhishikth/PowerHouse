import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { LoginDTO } from 'src/app/dto/LoginDTO';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private login = new BehaviorSubject<LoginDTO>(new LoginDTO)
  public isUserLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  // loginId = this.login.asObservable()

  private tokenValue = new BehaviorSubject<string>('defaultToken')
  token = this.tokenValue.asObservable()

  constructor(private http: HttpClient) { }

  url = "http://localhost:8888/auth/"

  setLoginDetails(loginDTO: LoginDTO) {
    this.login.next(loginDTO)
  }

  getLoginDetails() {
    return this.login.asObservable()
  }

  setToken(token: string) {
    // alert('token from loginservice : '+token)
    this.tokenValue.next(token)
  }

  loginUser(login: LoginDTO) {
    return this.http.post<LoginDTO>(this.url + "login", login, { headers: { 'NA': '' } });
  }

  adminLogin(login: LoginDTO) {
    return this.http.post(this.url + "admin", login, { headers: { 'NA': '' } });
  }

}
