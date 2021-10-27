import { Component, OnInit, Host } from '@angular/core';
import { DependentDTO } from 'src/app/dto/DependentDTO';
import { MerchantDTO } from 'src/app/dto/MerchantDTO';
import { MerchantService } from 'src/services/merchant.service';
import { HttpClient } from '@angular/common/http';
import { HotListDTO } from 'src/app/dto/hotListDTO';
import { MerchantHotlistDTO } from 'src/app/dto/merchantHotlistDTO';
import { DependentSpendLimitDTO } from 'src/app/dto/DependentSpendLimitDTO';
import { DependentService } from 'src/services/dependent.service';
import { Router } from '@angular/router';
import { MasterDependentDTO } from 'src/app/dto/MasterDependentDTO';
import { LoginDTO } from 'src/app/dto/LoginDTO';


@Component({
  selector: 'app-create-dependent',
  templateUrl: './create-dependent.component.html',
  styleUrls: ['./create-dependent.component.css']
})
export class CreateDependentComponent implements OnInit {

  loginDTO!: LoginDTO
  dependent!: DependentDTO
  merchantDTO!: MerchantDTO[]
  step: any = 1;
  tempStep: number = 0
  merchantDetails!: MerchantDTO[]
  tempMerchant!: string[]
  type!: string
  hotlistMerchant: string[] = [] //'amaz', 'flip', 'snap', 'hotels', 'e-commerce'
  lastStep: boolean = false
  HighlightRow!: Number
  gender = ['Male', 'Female', 'Others']
  errorMsg: string = ''
  alert: boolean = false

  tempMerchantHotlistDTO!: MerchantHotlistDTO[]
  hotlist!: HotListDTO
  merchantHotlistDTO: MerchantHotlistDTO[] = []
  dependentSpendLimit!: DependentSpendLimitDTO
  masterDependentDTO!: MasterDependentDTO

  routeState: any;
  // masterId!: string

  constructor(private dependentService: DependentService, private merchantService: MerchantService, private router: Router, private http: HttpClient) {
    this.dependent = new DependentDTO
    this.dependentSpendLimit = new DependentSpendLimitDTO
    this.masterDependentDTO = new MasterDependentDTO
    this.hotlist = new HotListDTO

    var nav = this.router.getCurrentNavigation()
    if (nav!.extras.state) {
      this.routeState = nav!.extras.state
      if (this.routeState) {
        this.loginDTO = this.routeState.loginDetails ? this.routeState.loginDetails : null;
        // alert('from createDependent : ' + this.loginDTO.id)
      }
    }
  }

  ngOnInit(): void {
    this.merchantService.getAllMerchants().subscribe((data: MerchantDTO[]) => {
      this.merchantDetails = data
    })
  }

  periodicFlag(event: any) {
    let value = event.target.value
    this.dependentSpendLimit.spendFlag = value
  }

  setDiabledStatus(dto: MerchantHotlistDTO, index: number) {
    dto.alertFlag = this.merchantHotlistDTO[index].alertFlag
  }

  createDependent() {
    this.masterDependentDTO.dependentId = this.dependent.id
    this.masterDependentDTO.masterId = this.loginDTO.id

    this.dependentSpendLimit.dependentId = this.dependent.id
    this.dependentSpendLimit.accountBalance = 1000

    this.hotlist.dependentId = this.dependent.id
    this.hotlist.hotlist = this.merchantHotlistDTO

    this.registerDependent(this.dependent)

    // console.log(this.masterDependentDTO)
    try {
      setTimeout(() => {
        this.tagMasterAndDependent(this.masterDependentDTO)
      }, 1000);

      // console.log(this.dependentSpendLimit)
      setTimeout(() => {
        this.setSpendLimit(this.dependentSpendLimit)
      }, 150);


      // console.log(this.hotlist)
      setTimeout(() => {
        this.setHotlist(this.hotlist)
      }, 150);
    } catch (error) {
      alert('Cant create Dependent')
      alert(error)
    }

    alert('Dependent Created')
    this.router.navigate(['/parent/home'])

  }

  private registerDependent(dependent: DependentDTO) {
    this.dependentService.registerDependent(dependent).subscribe((data: any) => {
      if (data != null) {
        alert('Cant create Dependent')
        alert(data.message)
        this.error(data)
      }
    })
  }

  private tagMasterAndDependent(masterDependentDTO: MasterDependentDTO) {
    this.dependentService.tagMasterAndDependent(masterDependentDTO).subscribe((data: any) => {
      if (data != null) {
        alert('Cant join with master')
        alert(data.message)
        this.error(data)
        return
      }
    }, (err) => {
      alert(err)
      throw err
    })
  }

  private setSpendLimit(dependentSpendLimit: DependentSpendLimitDTO) {
    this.dependentService.setSpendLimit(dependentSpendLimit).subscribe((data: any) => {
      if (data != null) {
        alert('Cant setSpendLimit')
        this.error(data)
        return
      }
    })
  }

  private setHotlist(hotlist: HotListDTO) {
    this.dependentService.setHotList(hotlist).subscribe((data: any) => {  //Arrow Function
      if (data != null) {
        alert('Cant setHotList')
        alert(data.message)
        this.error(data)
        return
      }
    })
  }

  private error(data: any) {
    if (data.hasOwnProperty('cause')) {
      alert(data.cause.message)
    }
  }

  ClickedRow(i: number) {
    this.HighlightRow = i;
  }

  getMerchants() {
    this.merchantService.getAllMerchants().subscribe((data: any) => {
      this.merchantDTO = data
    })
  }

  merchantName() {
    this.type = 'Merchant'

    this.tempMerchantHotlistDTO = []
    for (let index = 0; index < this.merchantDetails.length; index++) {
      let merchantHotlist = new MerchantHotlistDTO
      merchantHotlist.merchant = this.merchantDetails[index].merchantName
      merchantHotlist.merchantFlag = this.type
      this.tempMerchantHotlistDTO.push(merchantHotlist)
    }
  }

  merchantType() {
    this.type = 'Type'

    this.tempMerchantHotlistDTO = []
    for (let index = 0; index < this.merchantDetails.length; index++) {
      let merchantHotlist = new MerchantHotlistDTO
      merchantHotlist.merchant = this.merchantDetails[index].merchantType
      merchantHotlist.merchantFlag = this.type
      this.tempMerchantHotlistDTO.push(merchantHotlist)
    }
  }

  selectHotlist(merchant: MerchantHotlistDTO) {
    if (this.merchantHotlistDTO.includes(merchant)) {
      let response = confirm('Do you want to remove the hotlist flag for ' + merchant.merchant + ' ?')
      if (response == true) {
        this.removeHotlist(merchant)
      }
    }
    if (!this.merchantHotlistDTO.includes(merchant)) {
      merchant.alertFlag = true
      merchant.spendLimit = 0
      this.merchantHotlistDTO.push(merchant)
    }
  }

  spendLimitChange(event: any, index: number) {
    var amount = event.target.value
    this.merchantHotlistDTO[index].spendLimit = amount
  }

  check(value: MerchantHotlistDTO): boolean {
    if (this.merchantHotlistDTO !== undefined) {
      for (let index = 0; index < this.merchantHotlistDTO.length; index++) {
        if (JSON.stringify(this.merchantHotlistDTO[index]) === JSON.stringify(value)) {
          return true
        }
      }
    }
    return false
  }

  removeHotlist(merchant: MerchantHotlistDTO) {
    // let index: number = this.hotlistMerchant.indexOf(merchant)
    // this.hotlistMerchant.splice(index, 1)

    // this.merchantHotlistDTO.forEach((value, index) => {
    //   if (JSON.stringify(value) == JSON.stringify(merchant)) {
    //     console.log(merchant)
    //     this.merchantHotlistDTO.splice(index, 1)
    //   }
    // })

    this.merchantHotlistDTO = this.merchantHotlistDTO.filter(item => JSON.stringify(item) !== JSON.stringify(merchant));
    console.log(this.merchantHotlistDTO)

    // let index = this.merchantHotlistDTO.indexOf(merchant)
    // console.log(index)
    // this.merchantHotlistDTO.splice(index, 1)

  }

  clearErrorMsg() {
    this.errorMsg = ''
  }

  showErrMsg() {
    this.errorMsg = '* Fields cannot be empty';
  }

  onNext() {
    // alert('from createDependent : ' + this.loginDTO)
    console.log(this.loginDTO)
    if (this.step == 1) {
      if (this.dependent.id == null || this.dependent.mail == null) {
        this.showErrMsg()
        return
      }
    }

    if (this.step == 2) {
      if (this.dependent.age <= 0 || this.dependent.age == undefined) {
        this.errorMsg = 'Age not allowed'
        return
      }
      if (this.dependent.mobile === undefined || this.dependent.mobile.length <= 9 || this.dependent.name === undefined || this.dependent.gender == null) {
        this.showErrMsg()
        return
      }
    }
    if (this.step == -1) {
      this.step = this.tempStep
      this.step += 1
      return
    }
    if (this.merchantHotlistDTO.length > 0) {
      this.tempStep = this.step
      this.step = -1
      return
    }
    if (this.tempStep != 0) {
      this.step = this.tempStep
    }
    this.errorMsg = ''
    this.step += 1
  }

  onPrevious() {
    if (this.step == -1) {
      this.step = 3
      return
    }
    this.step -= 1
  }

}

