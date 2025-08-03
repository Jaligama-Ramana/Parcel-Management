export interface Booking {
  id?: number;
  bookingId: string;
  customer?: any;
  officer?: any;
  receiverName: string;
  receiverAddress: string;
  receiverPin: string;
  receiverMobile: string;
  parcelWeight: number;
  contentsDescription: string;
  deliveryType: DeliveryType;
  packingPreference: PackingPreference;
  pickupTime?: Date;
  dropoffTime?: Date;
  baseRate: number;
  weightCharge: number;
  deliveryCharge: number;
  packingCharge: number;
  adminFee?: number;
  taxRate: number;
  totalCost: number;
  status: BookingStatus;
  bookingDate: Date;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface BookingRequest {
  receiverName: string;
  receiverAddress: string;
  receiverPin: string;
  receiverMobile: string;
  parcelWeight: number;
  contentsDescription: string;
  deliveryType: DeliveryType;
  packingPreference: PackingPreference;
  pickupTime?: Date;
  dropoffTime?: Date;
  customerId?: string; // For officer bookings
}

export enum DeliveryType {
  STANDARD = 'STANDARD',
  EXPRESS = 'EXPRESS',
  SAME_DAY = 'SAME_DAY'
}

export enum PackingPreference {
  BASIC = 'BASIC',
  PREMIUM = 'PREMIUM'
}

export enum BookingStatus {
  NEW = 'NEW',
  SCHEDULED = 'SCHEDULED',
  PICKED_UP = 'PICKED_UP',
  ASSIGNED = 'ASSIGNED',
  BOOKED = 'BOOKED',
  IN_TRANSIT = 'IN_TRANSIT',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED'
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data?: T;
  timestamp: Date;
}