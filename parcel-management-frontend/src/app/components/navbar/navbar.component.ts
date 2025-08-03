import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">
          <i class="fas fa-box me-2"></i>
          Parcel Management System
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto">
            <li class="nav-item" *ngIf="authService.isAuthenticated() && authService.isCustomer()">
              <a class="nav-link" routerLink="/customer-dashboard" routerLinkActive="active">
                <i class="fas fa-tachometer-alt me-1"></i>Dashboard
              </a>
            </li>
            <li class="nav-item" *ngIf="authService.isAuthenticated() && authService.isOfficer()">
              <a class="nav-link" routerLink="/officer-dashboard" routerLinkActive="active">
                <i class="fas fa-tachometer-alt me-1"></i>Dashboard
              </a>
            </li>
            <li class="nav-item" *ngIf="authService.isAuthenticated()">
              <a class="nav-link" routerLink="/booking" routerLinkActive="active">
                <i class="fas fa-plus-circle me-1"></i>Book Parcel
              </a>
            </li>
            <li class="nav-item" *ngIf="authService.isAuthenticated()">
              <a class="nav-link" routerLink="/tracking" routerLinkActive="active">
                <i class="fas fa-search me-1"></i>Track
              </a>
            </li>
            <li class="nav-item" *ngIf="authService.isAuthenticated() && authService.isCustomer()">
              <a class="nav-link" routerLink="/previous-bookings" routerLinkActive="active">
                <i class="fas fa-history me-1"></i>My Bookings
              </a>
            </li>
            <li class="nav-item" *ngIf="authService.isAuthenticated() && authService.isCustomer()">
              <a class="nav-link" routerLink="/feedback" routerLinkActive="active">
                <i class="fas fa-star me-1"></i>Feedback
              </a>
            </li>
          </ul>
          
          <ul class="navbar-nav">
            <li class="nav-item" *ngIf="!authService.isAuthenticated()">
              <a class="nav-link" routerLink="/login" routerLinkActive="active">
                <i class="fas fa-sign-in-alt me-1"></i>Login
              </a>
            </li>
            <li class="nav-item" *ngIf="!authService.isAuthenticated()">
              <a class="nav-link" routerLink="/register" routerLinkActive="active">
                <i class="fas fa-user-plus me-1"></i>Register
              </a>
            </li>
            <li class="nav-item dropdown" *ngIf="authService.isAuthenticated()">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                <i class="fas fa-user me-1"></i>{{ authService.getUserName() }}
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#" (click)="logout()">
                  <i class="fas fa-sign-out-alt me-1"></i>Logout
                </a></li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  `,
  styles: [`
    .navbar-brand {
      font-weight: bold;
    }
    .nav-link {
      font-weight: 500;
    }
    .nav-link.active {
      background-color: rgba(255, 255, 255, 0.1);
      border-radius: 5px;
    }
  `]
})
export class NavbarComponent implements OnInit {

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.authService.logout();
  }
}