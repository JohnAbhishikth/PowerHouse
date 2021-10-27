import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MasterDTO } from 'src/app/dto/MasterDTO';
import { MasterService } from 'src/services/master.service';
import { LoginDTO } from 'src/app/dto/LoginDTO';
import { DependentService } from 'src/services/dependent.service';
import { DependentDTO } from 'src/app/dto/DependentDTO';

@Component({
  selector: 'app-self-details',
  templateUrl: './self-details.component.html',
  styleUrls: ['./self-details.component.css']
})
export class SelfDetailsComponent implements OnInit {

  canChange: boolean = false
  routeState: any;
  // master!: MasterDTO
  // id!: string
  loginDTO!: LoginDTO


  name: any
  mobile: any
  gender: any
  emailId: any
  accountBalance!: number
  loginId: any
  password: any
  genderArray = ['Male', 'Female', 'Others']

  constructor(private router: Router, private masterService: MasterService, private dependentService: DependentService) {

    var nav = this.router.getCurrentNavigation()
    if (nav!.extras.state) {
      this.routeState = nav!.extras.state
      if (this.routeState) {
        this.loginDTO = this.routeState.loginDetails ? this.routeState.loginDetails : null;
        // console.log(this.loginDTO)
      }
    }

    this.getDetails(this.loginDTO);

  }

  ngOnInit(): void { }

  private getDetails(loginDTO: LoginDTO) {
    let id = loginDTO.id

    let loginType = loginDTO.loginType
    if (loginType == 'dependent') {
      this.dependentService.getDependentsById(id).subscribe((data: DependentDTO) => {
        this.emailId = data.mail

        this.setDetails(data)
      })
    }
    if (loginType == 'master') {
      this.masterService.getMasterDetails(id).subscribe((data: MasterDTO) => {
        this.emailId = data.email
        this.accountBalance = data.accountBalance

        this.setDetails(data)
      })
    }
  }

  private setDetails(data: any) {
    this.name = data.name
    this.mobile = data.mobile
    this.gender = data.gender
    this.loginId = data.id
  }

  saveDetails() {
    let loginType = this.loginDTO.loginType

    if (loginType == 'dependent') {
      let dependentDTO: DependentDTO = new DependentDTO
      dependentDTO.id = this.loginDTO.id
      dependentDTO.gender = this.gender
      dependentDTO.mail = this.emailId
      dependentDTO.mobile = this.mobile
      dependentDTO.name = this.name

      this.dependentService.updateDependentDetails(dependentDTO).subscribe((data: any) => {
        if (data == null) {
          alert('Details Saved Sucessfully')
          this.canChange = false
          return
        }
        else if (data.hasOwnProperty('cause')) {
          console.log(data.cause)
          return
        }
      })
    }


    if (loginType == 'master') {

      let masterDTO: MasterDTO = new MasterDTO
      masterDTO.id = this.loginDTO.id
      masterDTO.gender = this.gender
      masterDTO.email = this.emailId
      masterDTO.mobile = this.mobile
      masterDTO.name = this.name

      this.masterService.updateMasterDetails(masterDTO).subscribe((data: any) => {
        if (data == null) {
          alert('Details Saved Sucessfully')
          this.canChange = false
          return
        }
        else if (data.hasOwnProperty('cause')) {
          console.log(data.cause)
          return
        }
      })
    }
  }

  genderChangeEvent(event: any) {
    // console.log(event.target.value)
  }


  changeUpdateOption() {
    this.canChange = true
  }

}
