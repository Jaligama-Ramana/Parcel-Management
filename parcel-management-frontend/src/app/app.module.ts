import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { OfficerDashboardComponent } from './components/officer-dashboard/officer-dashboard.component';
import { BookingComponent } from './components/booking/booking.component';
import { PaymentComponent } from './components/payment/payment.component';
import { TrackingComponent } from './components/tracking/tracking.component';
import { PreviousBookingsComponent } from './components/previous-bookings/previous-bookings.component';
import { FeedbackComponent } from './components/feedback/feedback.component';

import { routes } from './app.routes';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
    CustomerDashboardComponent,
    OfficerDashboardComponent,
    BookingComponent,
    PaymentComponent,
    TrackingComponent,
    PreviousBookingsComponent,
    FeedbackComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }