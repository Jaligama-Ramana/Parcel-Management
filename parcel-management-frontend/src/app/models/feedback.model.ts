export interface Feedback {
  id?: number;
  booking?: any;
  customer?: any;
  description: string;
  rating: number;
  feedbackDate: Date;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface FeedbackRequest {
  bookingId: string;
  customerId: string;
  description: string;
  rating: number;
}