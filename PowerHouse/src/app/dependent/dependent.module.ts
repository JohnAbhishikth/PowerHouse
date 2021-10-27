import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DependentDashboardHomeComponent } from './dependent-dashboard-home/dependent-dashboard-home.component';
import { FormsModule } from '@angular/forms';
import { DoTransactionComponent } from './do-transaction/do-transaction.component';
import { AccountDetailsComponent } from './account-details/account-details.component';


@NgModule({
  declarations: [
    DependentDashboardHomeComponent,
    DoTransactionComponent,
    AccountDetailsComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    DependentDashboardHomeComponent, 
    DoTransactionComponent,
    AccountDetailsComponent
  ]
})
export class DependentModule { }
