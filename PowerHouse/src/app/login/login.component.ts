import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from 'src/services/login.service';
import { LoginDTO } from '../dto/LoginDTO';
import { DependentService } from 'src/services/dependent.service';
import { MasterService } from 'src/services/master.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  logindto: LoginDTO
  name!: string

  constructor(private loginService: LoginService, private router: Router, private route: ActivatedRoute) {
    this.logindto = new LoginDTO
  }

  ngOnInit(): void { }

  errorMsg!: string
  token!: string
  isError: boolean = false

  login() {
    this.loginService.loginUser(this.logindto).subscribe((data: any) => {
      if (data != null) {
        if (data.hasOwnProperty('cause')) {
          console.log(data.cause.message)
          this.isError = true
          this.errorMsg = data.cause.message
          return
        }
        else {
          this.loginUser(data, this.logindto)
        }
      } else {
        alert("Error cant Login.Try After Some Time")
      }
    }, (err) => {
      alert("Failed to Login");
    })
  }

  loginUser(data: any, dto: LoginDTO) {

    this.token = data.token
    this.name = data.name

    sessionStorage.setItem("token", this.token)
    // sessionStorage.setItem("userName", this.name)
    // sessionStorage.setItem("masterId", dto.id)
    // sessionStorage.setItem("type", dto.user)


    var loginDTO: LoginDTO = data
    loginDTO.id = dto.id

    this.loginService.setLoginDetails(loginDTO)

    this.loginService.setToken('token.token.token')

    this.loginService.isUserLoggedIn.next(true)

    if (dto.user == "parent") {
      this.router.navigateByUrl('/parent/home')
    } else {
      this.router.navigateByUrl('/child/home')
    }
  }
}
