import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  template: `
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
          <div class="card shadow">
            <div class="card-body">
              <div class="text-center mb-4">
                <i class="fas fa-box fa-3x text-primary mb-3"></i>
                <h3 class="card-title">Login</h3>
                <p class="text-muted">Enter your credentials to continue</p>
              </div>

              <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
                <div class="mb-3">
                  <label for="customerId" class="form-label">Customer ID</label>
                  <input
                    type="text"
                    class="form-control"
                    id="customerId"
                    formControlName="customerId"
                    placeholder="Enter your Customer ID"
                    [class.is-invalid]="loginForm.get('customerId')?.invalid && loginForm.get('customerId')?.touched">
                  <div class="invalid-feedback" *ngIf="loginForm.get('customerId')?.invalid && loginForm.get('customerId')?.touched">
                    Customer ID is required
                  </div>
                </div>

                <div class="mb-3">
                  <label for="password" class="form-label">Password</label>
                  <input
                    type="password"
                    class="form-control"
                    id="password"
                    formControlName="password"
                    placeholder="Enter your password"
                    [class.is-invalid]="loginForm.get('password')?.invalid && loginForm.get('password')?.touched">
                  <div class="invalid-feedback" *ngIf="loginForm.get('password')?.invalid && loginForm.get('password')?.touched">
                    Password is required
                  </div>
                </div>

                <div class="alert alert-danger" *ngIf="errorMessage">
                  <i class="fas fa-exclamation-triangle me-2"></i>{{ errorMessage }}
                </div>

                <div class="alert alert-success" *ngIf="successMessage">
                  <i class="fas fa-check-circle me-2"></i>{{ successMessage }}
                </div>

                <div class="d-grid gap-2 mb-3">
                  <button
                    type="submit"
                    class="btn btn-primary"
                    [disabled]="loginForm.invalid || loading">
                    <span *ngIf="loading" class="spinner-border spinner-border-sm me-2"></span>
                    <i class="fas fa-sign-in-alt me-2" *ngIf="!loading"></i>
                    {{ loading ? 'Signing In...' : 'Sign In' }}
                  </button>
                </div>
              </form>

              <div class="text-center">
                <p class="mb-0">Don't have an account?</p>
                <a routerLink="/register" class="text-decoration-none">
                  <i class="fas fa-user-plus me-1"></i>Register here
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .card {
      border: none;
      border-radius: 10px;
    }
    .fa-box {
      color: #007bff;
    }
  `]
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      customerId: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Redirect if already authenticated
    if (this.authService.isAuthenticated()) {
      this.redirectToDashboard();
    }
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const credentials = this.loginForm.value;

    this.authService.login(credentials).subscribe({
      next: (response) => {
        this.loading = false;
        if (response.success) {
          this.successMessage = 'Login successful! Redirecting...';
          setTimeout(() => {
            this.redirectToDashboard();
          }, 1000);
        } else {
          this.errorMessage = response.message || 'Login failed';
        }
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = error.error?.message || 'Login failed. Please try again.';
      }
    });
  }

  private redirectToDashboard(): void {
    if (this.authService.isCustomer()) {
      this.router.navigate(['/customer-dashboard']);
    } else if (this.authService.isOfficer()) {
      this.router.navigate(['/officer-dashboard']);
    } else {
      this.router.navigate(['/customer-dashboard']);
    }
  }
}