import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminDashboardComponent } from './admin/admin-dashboard/admin-dashboard.component';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { AdminLoginComponent } from './admin/admin-login/admin-login.component';
import { CreateMerchantComponent } from './admin/create-merchant/create-merchant.component';
import { CreateParentComponent } from './admin/create-parent/create-parent.component';
import { UpdateMerchantComponent } from './admin/update-merchant/update-merchant.component';
import { DependentDashboardComponent } from './dependent-dashboard/dependent-dashboard.component';
import { AccountDetailsComponent } from './dependent/account-details/account-details.component';
import { DependentDashboardHomeComponent } from './dependent/dependent-dashboard-home/dependent-dashboard-home.component';
import { DoTransactionComponent } from './dependent/do-transaction/do-transaction.component';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ParentDashboardComponent } from './parent-dashboard/parent-dashboard.component';
import { AllDependentsComponent } from './parent/all-dependents/all-dependents.component';
import { CreateDependentComponent } from './parent/create-dependent/create-dependent.component';
import { ParentDashboardHomeComponent } from './parent/parent-dashboard-home/parent-dashboard-home.component';
import { SelfDetailsComponent } from './parent/self-details/self-details.component';
import { TransactionComponent } from './parent/transaction/transaction.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomepageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'adminLogin', component: AdminLoginComponent },
  {
    path: 'admin', component: AdminDashboardComponent,
    children: [
      { path: 'home', component: AdminHomeComponent },
      { path: 'parent', component: CreateParentComponent },
      { path: 'merchant', component: CreateMerchantComponent },
      { path: 'updateMerchant', component: UpdateMerchantComponent }
    ]
  },
  { path: 'header', component: HeaderComponent },
  {
    path: 'parent', component: ParentDashboardComponent,
    children: [
      { path: 'home', component: ParentDashboardHomeComponent },
      { path: 'createDependent', component: CreateDependentComponent },
      { path: 'allDependents', component: AllDependentsComponent },
      { path: 'transaction', component: TransactionComponent },
      { path: 'self', component: SelfDetailsComponent }
    ]
  },
  {
    path: 'child', component: DependentDashboardComponent,
    children: [
      { path: 'home', component: DependentDashboardHomeComponent },
      { path: 'transact', component: DoTransactionComponent },
      { path: 'self', component: SelfDetailsComponent },
      { path: 'account', component: AccountDetailsComponent },
      { path: 'transaction/details', component: TransactionComponent }
    ]
  },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
