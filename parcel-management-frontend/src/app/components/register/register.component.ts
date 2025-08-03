import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  template: `
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
          <div class="card shadow">
            <div class="card-body">
              <div class="text-center mb-4">
                <i class="fas fa-user-plus fa-3x text-primary mb-3"></i>
                <h3 class="card-title">Customer Registration</h3>
                <p class="text-muted">Create your account to start using our services</p>
              </div>

              <form [formGroup]="registerForm" (ngSubmit)="onSubmit()">
                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label for="name" class="form-label">Full Name *</label>
                    <input
                      type="text"
                      class="form-control"
                      id="name"
                      formControlName="name"
                      placeholder="Enter your full name"
                      [class.is-invalid]="registerForm.get('name')?.invalid && registerForm.get('name')?.touched">
                    <div class="invalid-feedback" *ngIf="registerForm.get('name')?.invalid && registerForm.get('name')?.touched">
                      Full name is required
                    </div>
                  </div>

                  <div class="col-md-6 mb-3">
                    <label for="email" class="form-label">Email *</label>
                    <input
                      type="email"
                      class="form-control"
                      id="email"
                      formControlName="email"
                      placeholder="Enter your email"
                      [class.is-invalid]="registerForm.get('email')?.invalid && registerForm.get('email')?.touched">
                    <div class="invalid-feedback" *ngIf="registerForm.get('email')?.invalid && registerForm.get('email')?.touched">
                      <span *ngIf="registerForm.get('email')?.errors?.['required']">Email is required</span>
                      <span *ngIf="registerForm.get('email')?.errors?.['email']">Please enter a valid email</span>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-4 mb-3">
                    <label for="countryCode" class="form-label">Country Code *</label>
                    <select class="form-control" id="countryCode" formControlName="countryCode"
                      [class.is-invalid]="registerForm.get('countryCode')?.invalid && registerForm.get('countryCode')?.touched">
                      <option value="">Select</option>
                      <option value="+91">+91 (India)</option>
                      <option value="+1">+1 (USA)</option>
                      <option value="+44">+44 (UK)</option>
                      <option value="+61">+61 (Australia)</option>
                    </select>
                    <div class="invalid-feedback" *ngIf="registerForm.get('countryCode')?.invalid && registerForm.get('countryCode')?.touched">
                      Country code is required
                    </div>
                  </div>

                  <div class="col-md-8 mb-3">
                    <label for="mobile" class="form-label">Mobile Number *</label>
                    <input
                      type="text"
                      class="form-control"
                      id="mobile"
                      formControlName="mobile"
                      placeholder="Enter 10-digit mobile number"
                      [class.is-invalid]="registerForm.get('mobile')?.invalid && registerForm.get('mobile')?.touched">
                    <div class="invalid-feedback" *ngIf="registerForm.get('mobile')?.invalid && registerForm.get('mobile')?.touched">
                      <span *ngIf="registerForm.get('mobile')?.errors?.['required']">Mobile number is required</span>
                      <span *ngIf="registerForm.get('mobile')?.errors?.['pattern']">Mobile number must be 10 digits</span>
                    </div>
                  </div>
                </div>

                <div class="mb-3">
                  <label for="address" class="form-label">Address *</label>
                  <textarea
                    class="form-control"
                    id="address"
                    rows="3"
                    formControlName="address"
                    placeholder="Enter your complete address"
                    [class.is-invalid]="registerForm.get('address')?.invalid && registerForm.get('address')?.touched"></textarea>
                  <div class="invalid-feedback" *ngIf="registerForm.get('address')?.invalid && registerForm.get('address')?.touched">
                    Address is required
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-6 mb-3">
                    <label for="password" class="form-label">Password *</label>
                    <input
                      type="password"
                      class="form-control"
                      id="password"
                      formControlName="password"
                      placeholder="Enter password"
                      [class.is-invalid]="registerForm.get('password')?.invalid && registerForm.get('password')?.touched">
                    <div class="invalid-feedback" *ngIf="registerForm.get('password')?.invalid && registerForm.get('password')?.touched">
                      <span *ngIf="registerForm.get('password')?.errors?.['required']">Password is required</span>
                      <span *ngIf="registerForm.get('password')?.errors?.['pattern']">Password must contain uppercase, lowercase, and special character</span>
                    </div>
                  </div>

                  <div class="col-md-6 mb-3">
                    <label for="confirmPassword" class="form-label">Confirm Password *</label>
                    <input
                      type="password"
                      class="form-control"
                      id="confirmPassword"
                      formControlName="confirmPassword"
                      placeholder="Confirm password"
                      [class.is-invalid]="registerForm.get('confirmPassword')?.invalid && registerForm.get('confirmPassword')?.touched">
                    <div class="invalid-feedback" *ngIf="registerForm.get('confirmPassword')?.invalid && registerForm.get('confirmPassword')?.touched">
                      <span *ngIf="registerForm.get('confirmPassword')?.errors?.['required']">Please confirm your password</span>
                      <span *ngIf="registerForm.get('confirmPassword')?.errors?.['mismatch']">Passwords do not match</span>
                    </div>
                  </div>
                </div>

                <div class="mb-3">
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="notificationPreference" formControlName="notificationPreference">
                    <label class="form-check-label" for="notificationPreference">
                      Enable notifications
                    </label>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="mailDeliveryPreference" formControlName="mailDeliveryPreference">
                    <label class="form-check-label" for="mailDeliveryPreference">
                      Enable mail delivery updates
                    </label>
                  </div>
                </div>

                <div class="alert alert-danger" *ngIf="errorMessage">
                  <i class="fas fa-exclamation-triangle me-2"></i>{{ errorMessage }}
                </div>

                <div class="alert alert-success" *ngIf="successMessage">
                  <i class="fas fa-check-circle me-2"></i>{{ successMessage }}
                  <div *ngIf="registrationData" class="mt-2">
                    <strong>Customer ID:</strong> {{ registrationData.customerId }}<br>
                    <strong>Name:</strong> {{ registrationData.name }}<br>
                    <strong>Email:</strong> {{ registrationData.email }}
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-6 mb-2">
                    <button
                      type="submit"
                      class="btn btn-primary w-100"
                      [disabled]="registerForm.invalid || loading">
                      <span *ngIf="loading" class="spinner-border spinner-border-sm me-2"></span>
                      <i class="fas fa-user-plus me-2" *ngIf="!loading"></i>
                      {{ loading ? 'Registering...' : 'Register' }}
                    </button>
                  </div>
                  <div class="col-md-6 mb-2">
                    <button type="button" class="btn btn-secondary w-100" (click)="resetForm()">
                      <i class="fas fa-undo me-2"></i>Reset
                    </button>
                  </div>
                </div>
              </form>

              <div class="text-center mt-3">
                <p class="mb-0">Already have an account?</p>
                <a routerLink="/login" class="text-decoration-none">
                  <i class="fas fa-sign-in-alt me-1"></i>Login here
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
    .fa-user-plus {
      color: #007bff;
    }
  `]
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  errorMessage = '';
  successMessage = '';
  registrationData: any = null;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      countryCode: ['', Validators.required],
      mobile: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      address: ['', Validators.required],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$/)]],
      confirmPassword: ['', Validators.required],
      notificationPreference: [true],
      mailDeliveryPreference: [true]
    }, { validators: this.passwordMatchValidator });
  }

  ngOnInit(): void {
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ mismatch: true });
    } else {
      if (confirmPassword?.errors) {
        delete confirmPassword.errors['mismatch'];
        if (Object.keys(confirmPassword.errors).length === 0) {
          confirmPassword.setErrors(null);
        }
      }
    }
    return null;
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.registrationData = null;

    const userData = this.registerForm.value;

    this.authService.register(userData).subscribe({
      next: (response) => {
        this.loading = false;
        if (response.success) {
          this.successMessage = response.message;
          this.registrationData = response.data;
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 3000);
        } else {
          this.errorMessage = response.message || 'Registration failed';
        }
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = error.error?.message || 'Registration failed. Please try again.';
      }
    });
  }

  resetForm(): void {
    this.registerForm.reset({
      notificationPreference: true,
      mailDeliveryPreference: true
    });
    this.errorMessage = '';
    this.successMessage = '';
    this.registrationData = null;
  }
}