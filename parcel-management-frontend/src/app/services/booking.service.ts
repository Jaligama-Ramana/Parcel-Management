import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Booking, BookingRequest, BookingStatus, ApiResponse } from '../models/booking.model';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private apiUrl = 'http://localhost:8080/api/bookings';

  constructor(private http: HttpClient) { }

  createBooking(bookingData: BookingRequest, customerId: string, officerId?: string): Observable<ApiResponse<any>> {
    let params = new HttpParams().set('customerId', customerId);
    if (officerId) {
      params = params.set('officerId', officerId);
    }
    return this.http.post<ApiResponse<any>>(`${this.apiUrl}/create`, bookingData, { params });
  }

  calculateCost(bookingData: BookingRequest): Observable<ApiResponse<number>> {
    return this.http.post<ApiResponse<number>>(`${this.apiUrl}/calculate-cost`, bookingData);
  }

  getBookingById(bookingId: string): Observable<ApiResponse<Booking>> {
    return this.http.get<ApiResponse<Booking>>(`${this.apiUrl}/${bookingId}`);
  }

  getBookingsByCustomer(customerId: string): Observable<ApiResponse<Booking[]>> {
    return this.http.get<ApiResponse<Booking[]>>(`${this.apiUrl}/customer/${customerId}`);
  }

  getBookingsByCustomerPaginated(
    customerId: string, 
    page: number = 0, 
    size: number = 10, 
    sortBy: string = 'bookingDate', 
    sortDir: string = 'desc'
  ): Observable<ApiResponse<any>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);
    
    return this.http.get<ApiResponse<any>>(`${this.apiUrl}/customer/${customerId}/paginated`, { params });
  }

  getAllBookings(
    page: number = 0, 
    size: number = 10, 
    sortBy: string = 'bookingDate', 
    sortDir: string = 'desc'
  ): Observable<ApiResponse<any>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);
    
    return this.http.get<ApiResponse<any>>(`${this.apiUrl}/all`, { params });
  }

  searchBookings(
    searchTerm: string,
    page: number = 0, 
    size: number = 10, 
    sortBy: string = 'bookingDate', 
    sortDir: string = 'desc'
  ): Observable<ApiResponse<any>> {
    const params = new HttpParams()
      .set('searchTerm', searchTerm)
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);
    
    return this.http.get<ApiResponse<any>>(`${this.apiUrl}/search`, { params });
  }

  updateBookingStatus(bookingId: string, status: BookingStatus): Observable<ApiResponse<string>> {
    const params = new HttpParams().set('status', status);
    return this.http.put<ApiResponse<string>>(`${this.apiUrl}/${bookingId}/status`, null, { params });
  }

  updatePickupDropoffTime(
    bookingId: string, 
    pickupTime?: Date, 
    dropoffTime?: Date
  ): Observable<ApiResponse<string>> {
    let params = new HttpParams();
    if (pickupTime) {
      params = params.set('pickupTime', pickupTime.toISOString());
    }
    if (dropoffTime) {
      params = params.set('dropoffTime', dropoffTime.toISOString());
    }
    
    return this.http.put<ApiResponse<string>>(`${this.apiUrl}/${bookingId}/times`, null, { params });
  }

  cancelBooking(bookingId: string, userRole: string): Observable<ApiResponse<string>> {
    const params = new HttpParams().set('userRole', userRole);
    return this.http.put<ApiResponse<string>>(`${this.apiUrl}/${bookingId}/cancel`, null, { params });
  }

  // Utility methods
  getDeliveryTypeDisplay(type: string): string {
    switch (type) {
      case 'STANDARD': return 'Standard (₹30)';
      case 'EXPRESS': return 'Express (₹80)';
      case 'SAME_DAY': return 'Same Day (₹150)';
      default: return type;
    }
  }

  getPackingPreferenceDisplay(preference: string): string {
    switch (preference) {
      case 'BASIC': return 'Basic (₹10)';
      case 'PREMIUM': return 'Premium (₹30)';
      default: return preference;
    }
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'NEW': return 'badge bg-secondary';
      case 'SCHEDULED': return 'badge bg-info';
      case 'PICKED_UP': return 'badge bg-primary';
      case 'ASSIGNED': return 'badge bg-warning';
      case 'BOOKED': return 'badge bg-success';
      case 'IN_TRANSIT': return 'badge bg-primary';
      case 'DELIVERED': return 'badge bg-success';
      case 'CANCELLED': return 'badge bg-danger';
      default: return 'badge bg-secondary';
    }
  }
}