import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { OfficerDashboardComponent } from './components/officer-dashboard/officer-dashboard.component';
import { BookingComponent } from './components/booking/booking.component';
import { PaymentComponent } from './components/payment/payment.component';
import { TrackingComponent } from './components/tracking/tracking.component';
import { PreviousBookingsComponent } from './components/previous-bookings/previous-bookings.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { 
    path: 'customer-dashboard', 
    component: CustomerDashboardComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'officer-dashboard', 
    component: OfficerDashboardComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'booking', 
    component: BookingComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'payment/:bookingId', 
    component: PaymentComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'tracking', 
    component: TrackingComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'previous-bookings', 
    component: PreviousBookingsComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'feedback', 
    component: FeedbackComponent, 
    canActivate: [AuthGuard] 
  },
  { path: '**', redirectTo: '/login' }
];