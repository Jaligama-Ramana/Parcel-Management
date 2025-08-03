export interface User {
  id?: number;
  customerId: string;
  name: string;
  email: string;
  countryCode: string;
  mobile: string;
  address: string;
  role: 'CUSTOMER' | 'OFFICER';
  notificationPreference?: boolean;
  mailDeliveryPreference?: boolean;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface UserRegistration {
  name: string;
  email: string;
  countryCode: string;
  mobile: string;
  address: string;
  password: string;
  confirmPassword: string;
  notificationPreference: boolean;
  mailDeliveryPreference: boolean;
}

export interface LoginRequest {
  customerId: string;
  password: string;
}

export interface LoginResponse {
  success: boolean;
  message: string;
  data?: {
    token: string;
    customerId: string;
    name: string;
    email: string;
    role: string;
    address: string;
    mobile: string;
  };
}