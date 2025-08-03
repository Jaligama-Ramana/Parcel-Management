export interface Payment {
  id?: number;
  paymentId: string;
  transactionId: string;
  booking?: any;
  amount: number;
  paymentMethod: PaymentMethod;
  status: PaymentStatus;
  cardType: CardType;
  cardNumberMasked?: string;
  cardholderName: string;
  paymentDate: Date;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface PaymentRequest {
  bookingId: string;
  amount: number;
  paymentMethod: PaymentMethod;
  cardType: CardType;
  cardNumber: string;
  expiryMonth: string;
  expiryYear: string;
  cvv: string;
  cardholderName: string;
}

export enum PaymentMethod {
  CREDIT_CARD = 'CREDIT_CARD',
  DEBIT_CARD = 'DEBIT_CARD',
  OFFICE_COUNTER = 'OFFICE_COUNTER'
}

export enum PaymentStatus {
  PENDING = 'PENDING',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED',
  REFUNDED = 'REFUNDED'
}

export enum CardType {
  CREDIT = 'CREDIT',
  DEBIT = 'DEBIT'
}