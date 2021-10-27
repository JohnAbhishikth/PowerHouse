import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/services/login.service';
import { LoginDTO } from 'src/app/dto/LoginDTO';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {

  login: LoginDTO
  errorMsg!: string
  constructor(private loginService: LoginService, private router: Router) {
    this.login = new LoginDTO
  }

  ngOnInit(): void { }

  adminLogin() {
    this.login.loginType = 'admin'
    this.loginService.adminLogin(this.login).subscribe((data: any) => {
      if (data != null) {
        this.errorMsg = data.message
        return
      } else {
        this.loginService.setLoginDetails(this.login)
        this.loginService.setToken('token.token.token')
        this.loginService.isUserLoggedIn.next(true)

        this.router.navigateByUrl('admin/home')
      }
    })
  }

  clearMsg() {
    this.errorMsg = ''
  }

}
