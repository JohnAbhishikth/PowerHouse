import { Component, OnInit } from '@angular/core';
import { DependentService } from 'src/services/dependent.service';
import { DependentAccountDTO } from 'src/app/dto/dependentAccountDTO';
import { MerchantHotlistDTO } from 'src/app/dto/merchantHotlistDTO';
import { DependentSpendLimitDTO } from 'src/app/dto/DependentSpendLimitDTO';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/dto/LoginDTO';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {

  dependentId!: string
  dependentSpendLimitDTO!: DependentSpendLimitDTO
  merchantHotlistDTO: MerchantHotlistDTO[] = []
  routerState: any
  loginDTO!: LoginDTO

  constructor(private dependentService: DependentService, private router: Router) {
    this.dependentSpendLimitDTO = new DependentSpendLimitDTO

    let nav = this.router.getCurrentNavigation()
    if (nav!.extras.state) {
      this.routerState = nav!.extras.state
      if (this.routerState) {
        this.loginDTO = this.routerState.loginDetails ? this.routerState.loginDetails : null
      }
    }
    this.dependentId = this.loginDTO.id
    // console.log(this.loginDTO)

  }

  ngOnInit(): void {
    this.getAccountDetails(this.dependentId)
  }

  getAccountDetails(dependentId: string) {
    this.dependentService.getAccountDetails(dependentId).subscribe((data: DependentAccountDTO) => {
      let dependentAccountDTO: DependentAccountDTO
      dependentAccountDTO = data

      this.dependentSpendLimitDTO = dependentAccountDTO.dependentSpendLimitDTO
      this.merchantHotlistDTO = dependentAccountDTO.merchantHotlistDTO
    })
  }

}
