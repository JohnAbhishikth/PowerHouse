import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ParentDashboardHomeComponent } from './parent-dashboard-home/parent-dashboard-home.component';
import { CreateDependentComponent } from './create-dependent/create-dependent.component';
import { AllDependentsComponent } from './all-dependents/all-dependents.component';
import { TransactionComponent } from './transaction/transaction.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SelfDetailsComponent } from './self-details/self-details.component';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  declarations: [
    ParentDashboardHomeComponent,
    CreateDependentComponent,
    AllDependentsComponent,
    TransactionComponent,
    SelfDetailsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule
  ],
  exports: [
    ParentDashboardHomeComponent,
    CreateDependentComponent,
    AllDependentsComponent,
    TransactionComponent,
    SelfDetailsComponent
  ]
})
export class ParentModule { }
