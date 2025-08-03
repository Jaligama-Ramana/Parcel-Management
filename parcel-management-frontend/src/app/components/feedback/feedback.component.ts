import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-feedback',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-4">
      <div class="card">
        <div class="card-body text-center">
          <i class="fas fa-star fa-3x text-secondary mb-3"></i>
          <h3>Feedback & Reviews</h3>
          <p class="text-muted">Feedback functionality will be implemented here</p>
          <div class="alert alert-info">
            <strong>Coming Soon!</strong> This feature is under development.
          </div>
        </div>
      </div>
    </div>
  `
})
export class FeedbackComponent {}