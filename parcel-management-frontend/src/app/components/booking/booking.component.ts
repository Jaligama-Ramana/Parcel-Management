import { Component } from '@angular/core';

@Component({
  selector: 'app-booking',
  template: `
    <div class="container mt-4">
      <div class="card">
        <div class="card-body text-center">
          <i class="fas fa-plus-circle fa-3x text-primary mb-3"></i>
          <h3>Book a Parcel</h3>
          <p class="text-muted">Booking functionality will be implemented here</p>
          <div class="alert alert-info">
            <strong>Coming Soon!</strong> This feature is under development.
          </div>
        </div>
      </div>
    </div>
  `
})
export class BookingComponent {}