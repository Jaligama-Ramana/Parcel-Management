import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Payment, PaymentRequest, ApiResponse } from '../models/payment.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiUrl = 'http://localhost:8080/api/payments';

  constructor(private http: HttpClient) { }

  processPayment(paymentData: PaymentRequest): Observable<ApiResponse<any>> {
    return this.http.post<ApiResponse<any>>(`${this.apiUrl}/process`, paymentData);
  }

  processOfficeCounterPayment(bookingId: string): Observable<ApiResponse<any>> {
    return this.http.post<ApiResponse<any>>(`${this.apiUrl}/office-counter/${bookingId}`, {});
  }

  getPaymentByBookingId(bookingId: string): Observable<ApiResponse<Payment>> {
    return this.http.get<ApiResponse<Payment>>(`${this.apiUrl}/booking/${bookingId}`);
  }

  getPaymentByPaymentId(paymentId: string): Observable<ApiResponse<Payment>> {
    return this.http.get<ApiResponse<Payment>>(`${this.apiUrl}/${paymentId}`);
  }
}