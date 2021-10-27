import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from 'src/services/login.service';
import { LoginDTO } from '../dto/LoginDTO';

@Component({
  selector: 'app-dependent-dashboard',
  templateUrl: './dependent-dashboard.component.html',
  styleUrls: ['./dependent-dashboard.component.css']
})
export class DependentDashboardComponent implements OnInit {
  userName: any
  dependentId!: string
  loginDTO!: LoginDTO


  constructor(private router: Router, private route: ActivatedRoute, private loginService: LoginService) { }

  ngOnInit(): void {
    this.loginService.getLoginDetails().subscribe((data: LoginDTO) => {
      // this.dependentId = data
      this.loginDTO = data
      this.loginDTO.loginType = 'dependent'
    })

    this.userName = this.loginDTO.name
    // alert('dependent Id: ' + this.dependentId)

  }

  // logout() {
  //   sessionStorage.clear()
  //   this.router.navigateByUrl("/").then(() => {
  //     window.location.reload();
  //   })
  // }

  selfDetails() {
    this.loginDTO.loginType = 'dependent'
    this.router.navigate(['self'], {
      relativeTo: this.route,
      state: { loginDetails: this.loginDTO }
    })
  }

  transaction() {
    this.router.navigate(['transact'], {
      relativeTo: this.route,
      state: {
        loginDetails: this.loginDTO
      }
    })
  }

  getTransactionDetails() {
    this.router.navigate(['transaction/details'], {
      relativeTo: this.route,
      state: {
        loginDetails: this.loginDTO
      }
    })
  }

  accountDetails() {
    this.router.navigate(['account'], {
      relativeTo: this.route,
      state: {
        loginDetails: this.loginDTO
      }
    })
  }

  home() {
    this.router.navigate(['home'], { relativeTo: this.route })
  }
}
