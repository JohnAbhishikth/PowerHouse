import { Component, OnInit } from '@angular/core';
import { MasterDTO } from 'src/app/dto/MasterDTO';
import { MasterService } from 'src/services/master.service';
import { LoginService } from 'src/services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-parent',
  templateUrl: './create-parent.component.html',
  styleUrls: ['./create-parent.component.css']
})
export class CreateParentComponent implements OnInit {

  master!: MasterDTO
  userName!: string
  errorMsg!: string

  constructor(private masterService: MasterService, private loginService: LoginService, private router: Router) {
    this.master = new MasterDTO

    this.loginService.getLoginDetails().subscribe(data => {
      this.userName = data.id
    })
  }

  ngOnInit(): void { }

  canProceed(): boolean {
    if (this.userName == undefined || this.userName == '')
      return false
    return true
  }

  saveDetails() {
    if (this.canProceed()) {
      this.masterService.createMasterDetails(this.master).subscribe((data: any) => {
        if (data !== null) {
          this.errorMsg = data.message
        } else {
          alert('Parent Crated')
          this.router.navigate(['/admin/home'])
        }
      })
    } else {
      alert('Login to Create Parent Account')
      this.router.navigateByUrl('home')
    }
  }
}
