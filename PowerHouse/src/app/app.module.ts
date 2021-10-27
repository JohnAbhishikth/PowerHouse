import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DependentDashboardComponent } from './dependent-dashboard/dependent-dashboard.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ParentDashboardComponent } from './parent-dashboard/parent-dashboard.component';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminModule } from './admin/admin.module';
import { DependentModule } from './dependent/dependent.module';
import { ParentModule } from './parent/parent.module';
import { DependentService } from 'src/services/dependent.service';
import { LoginService } from 'src/services/login.service';
import { MerchantService } from 'src/services/merchant.service';
import { TokenInterceptorService } from 'src/services/token-interceptor.service';
import { TransactionService } from 'src/services/transaction.service';
import { SimpleNotificationsModule } from 'angular2-notifications';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomepageComponent,
    LoginComponent,
    PageNotFoundComponent,
    ParentDashboardComponent,
    DependentDashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CommonModule,
    ParentModule,
    DependentModule,
    AdminModule,
    SimpleNotificationsModule.forRoot(),
    BrowserAnimationsModule
  ],
  providers: [DependentService, LoginService, MerchantService, TransactionService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
