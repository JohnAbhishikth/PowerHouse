import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { FormsModule } from '@angular/forms';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { RouterModule } from '@angular/router';
import { CreateMerchantComponent } from './create-merchant/create-merchant.component';
import { CreateParentComponent } from './create-parent/create-parent.component';
import { UpdateMerchantComponent } from './update-merchant/update-merchant.component';
import { SimpleNotificationsModule } from 'angular2-notifications';


@NgModule({
  declarations: [
    AdminLoginComponent,
    AdminDashboardComponent,
    AdminHomeComponent,
    CreateMerchantComponent,
    CreateParentComponent,
    UpdateMerchantComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    SimpleNotificationsModule.forRoot()
  ],
  exports: [
    AdminLoginComponent,
    AdminDashboardComponent,
    AdminHomeComponent
  ]
})
export class AdminModule { }
