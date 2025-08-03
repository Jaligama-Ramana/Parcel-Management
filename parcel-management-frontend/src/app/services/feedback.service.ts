import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Feedback, FeedbackRequest } from '../models/feedback.model';
import { ApiResponse } from '../models/booking.model';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  private apiUrl = 'http://localhost:8080/api/feedback';

  constructor(private http: HttpClient) { }

  addFeedback(feedbackData: FeedbackRequest): Observable<ApiResponse<string>> {
    const params = new HttpParams()
      .set('bookingId', feedbackData.bookingId)
      .set('customerId', feedbackData.customerId)
      .set('description', feedbackData.description)
      .set('rating', feedbackData.rating.toString());
    
    return this.http.post<ApiResponse<string>>(`${this.apiUrl}/add`, null, { params });
  }

  getDeliveredBookingsForFeedback(customerId: string): Observable<ApiResponse<any[]>> {
    return this.http.get<ApiResponse<any[]>>(`${this.apiUrl}/delivered-bookings/${customerId}`);
  }

  getAllFeedback(page: number = 0, size: number = 10): Observable<ApiResponse<any>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    
    return this.http.get<ApiResponse<any>>(`${this.apiUrl}/all`, { params });
  }
}