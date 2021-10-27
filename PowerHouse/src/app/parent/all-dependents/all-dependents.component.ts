import { Component, OnInit } from '@angular/core';
import { DependentDTO } from 'src/app/dto/DependentDTO';
import { MasterDTO } from 'src/app/dto/MasterDTO';
import { DependentService } from 'src/services/dependent.service';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/dto/LoginDTO';

@Component({
  selector: 'app-all-dependents',
  templateUrl: './all-dependents.component.html',
  styleUrls: ['./all-dependents.component.css']
})
export class AllDependentsComponent implements OnInit {

  masterDTO!: MasterDTO
  dependentDTO: DependentDTO[] = []
  name: any
  loginDTO!: LoginDTO
  routeState: any;

  constructor(private service: DependentService, private router: Router) {
    this.masterDTO = new MasterDTO

    var nav = this.router.getCurrentNavigation()
    if (nav!.extras.state) {
      this.routeState = nav!.extras.state
      if (this.routeState) {
        this.loginDTO = this.routeState.loginDetails ? this.routeState.loginDetails : null;
        // alert('from allDependent : ' + this.masterDTO.id)
        this.masterDTO.id = this.loginDTO.id
        this.name = this.loginDTO.name
      }
    }

    this.getAllDependentsById()
  }

  canShowTable() {
    return this.dependentDTO.length > 0
  }

  ngOnInit(): void { }

  getAllDependentsById() {
    this.service.getAllDependentsByMasterId(this.masterDTO).subscribe((data: any) => {
      this.dependentDTO = data
    })
  }
}
