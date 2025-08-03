import { Component } from '@angular/core';

@Component({
  selector: 'app-payment',
  template: `
    <div class="container mt-4">
      <div class="card">
        <div class="card-body text-center">
          <i class="fas fa-credit-card fa-3x text-success mb-3"></i>
          <h3>Payment Processing</h3>
          <p class="text-muted">Payment functionality will be implemented here</p>
          <div class="alert alert-info">
            <strong>Coming Soon!</strong> This feature is under development.
          </div>
        </div>
      </div>
    </div>
  `
})
export class PaymentComponent {}