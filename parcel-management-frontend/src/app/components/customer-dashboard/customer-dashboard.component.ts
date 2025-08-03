import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-customer-dashboard',
  template: `
    <div class="container mt-4">
      <div class="row">
        <div class="col-12">
          <div class="card bg-primary text-white mb-4">
            <div class="card-body">
              <h2 class="card-title">
                <i class="fas fa-tachometer-alt me-2"></i>
                Welcome, {{ authService.getUserName() }}!
              </h2>
              <p class="card-text">Customer Dashboard - Manage your parcel deliveries</p>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-plus-circle fa-3x text-primary mb-3"></i>
              <h5 class="card-title">Book a Parcel</h5>
              <p class="card-text">Create a new parcel booking with cost calculation</p>
              <a routerLink="/booking" class="btn btn-primary">Book Now</a>
            </div>
          </div>
        </div>

        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-search fa-3x text-success mb-3"></i>
              <h5 class="card-title">Track Parcel</h5>
              <p class="card-text">Track your parcel status using Booking ID</p>
              <a routerLink="/tracking" class="btn btn-success">Track Now</a>
            </div>
          </div>
        </div>

        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-history fa-3x text-info mb-3"></i>
              <h5 class="card-title">Previous Bookings</h5>
              <p class="card-text">View your booking history and manage orders</p>
              <a routerLink="/previous-bookings" class="btn btn-info">View History</a>
            </div>
          </div>
        </div>

        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-star fa-3x text-warning mb-3"></i>
              <h5 class="card-title">Feedback</h5>
              <p class="card-text">Rate and review your delivered parcels</p>
              <a routerLink="/feedback" class="btn btn-warning">Give Feedback</a>
            </div>
          </div>
        </div>

        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-headset fa-3x text-secondary mb-3"></i>
              <h5 class="card-title">Contact Support</h5>
              <p class="card-text">Get help with your parcel delivery issues</p>
              <button class="btn btn-secondary" onclick="alert('Contact Support: 1800-123-4567')">Contact Us</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dashboard-card {
      transition: transform 0.2s, box-shadow 0.2s;
      cursor: pointer;
    }
    .dashboard-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 16px rgba(0,0,0,0.1);
    }
  `]
})
export class CustomerDashboardComponent implements OnInit {

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  }
}