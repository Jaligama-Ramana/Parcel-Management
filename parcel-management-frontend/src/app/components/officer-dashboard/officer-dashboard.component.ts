import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-officer-dashboard',
  template: `
    <div class="container mt-4">
      <div class="row">
        <div class="col-12">
          <div class="card bg-success text-white mb-4">
            <div class="card-body">
              <h2 class="card-title">
                <i class="fas fa-user-tie me-2"></i>
                Welcome Officer, {{ authService.getUserName() }}!
              </h2>
              <p class="card-text">Officer Dashboard - Manage parcel operations</p>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-plus-circle fa-3x text-primary mb-3"></i>
              <h5 class="card-title">Book for Customer</h5>
              <p class="card-text">Create bookings on behalf of customers</p>
              <a routerLink="/booking" class="btn btn-primary">Book Now</a>
            </div>
          </div>
        </div>

        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-search fa-3x text-success mb-3"></i>
              <h5 class="card-title">Track All Parcels</h5>
              <p class="card-text">Track any parcel in the system</p>
              <a routerLink="/tracking" class="btn btn-success">Track</a>
            </div>
          </div>
        </div>

        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-list fa-3x text-info mb-3"></i>
              <h5 class="card-title">View All Bookings</h5>
              <p class="card-text">Manage all customer bookings</p>
              <button class="btn btn-info" onclick="alert('Feature coming soon!')">View All</button>
            </div>
          </div>
        </div>

        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-truck fa-3x text-warning mb-3"></i>
              <h5 class="card-title">Delivery Status</h5>
              <p class="card-text">Update parcel delivery status</p>
              <button class="btn btn-warning" onclick="alert('Feature coming soon!')">Update Status</button>
            </div>
          </div>
        </div>

        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card dashboard-card h-100">
            <div class="card-body text-center">
              <i class="fas fa-star fa-3x text-secondary mb-3"></i>
              <h5 class="card-title">View Feedback</h5>
              <p class="card-text">Monitor customer feedback</p>
              <a routerLink="/feedback" class="btn btn-secondary">View Feedback</a>
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
export class OfficerDashboardComponent implements OnInit {

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  }
}