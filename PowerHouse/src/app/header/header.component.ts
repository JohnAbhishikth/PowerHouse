import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/services/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userName: any
  loggedIn: boolean = false

  routeState: any

  constructor(private router: Router, private loginService: LoginService) {

    this.loginService.isUserLoggedIn.subscribe((data) => {
      this.loggedIn = data
    })
  }

  ngOnInit(): void {
    // alert('header')
    // this.isLogged()
  }


  isLogged() {
    try {
      // let token = sessionStorage.getItem('token')
      let isLoggedIn = sessionStorage.getItem('??')
      sessionStorage.removeItem('??')
      var len = new String(isLoggedIn).length
      if (len == 0 || isLoggedIn == undefined) {
        this.loggedIn = false
        return
      }
      if (len > 0) {
        this.loggedIn = true
        this.userName = sessionStorage.getItem("user")
      }
    } catch {
      this.loggedIn = false
    }
  }

  loginPage($event: MouseEvent) {
    if ($event.altKey == true && $event.ctrlKey == true)
      window.location.href = "/adminLogin"
    else {
      window.location.href = "/login"
    }
  }

  logOut() {
    sessionStorage.clear()
    this.router.navigateByUrl("/home")
    this.loggedIn = false
    setTimeout(() => {
      window.location.reload();
    }, 1000);

  }
}
