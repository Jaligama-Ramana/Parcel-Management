# Parcel Management System

A comprehensive parcel management system built with **Angular** frontend and **Spring Boot** backend, featuring customer and officer portals with complete booking, payment, tracking, and feedback functionality.

## 🚀 Features

### Customer Features
- **User Registration & Login** - Secure authentication with BCrypt password encryption
- **Dashboard** - Welcome screen with navigation menu
- **Parcel Booking** - Complete booking form with cost calculation
- **Payment Processing** - Credit/Debit card payment with validation
- **Parcel Tracking** - Track bookings by Booking ID
- **Previous Bookings** - View booking history with pagination and filters
- **Booking Cancellation** - Cancel bookings with status validation
- **Feedback System** - Rate and review delivered parcels

### Officer Features
- **Officer Login** - Same authentication system with officer role
- **Officer Dashboard** - Administrative interface
- **Book on Behalf** - Create bookings for customers with admin fee
- **Parcel Tracking** - Track all bookings across the system
- **Status Management** - Update delivery status and pickup/drop times
- **View All Bookings** - Manage all customer bookings
- **Booking Cancellation** - Cancel bookings with refund processing
- **Feedback Management** - View all customer feedback

### System Features
- **Cost Calculator** - Dynamic pricing based on weight, delivery type, and packing
- **Invoice Generation** - Generate and download invoices by Booking ID
- **Responsive Design** - Bootstrap-based professional UI
- **Data Export** - Export booking data to Excel/PDF (pagination >10 records)
- **Search & Filter** - Advanced search across all modules

## 🏗️ Architecture

### Backend (Spring Boot)
- **Entities**: User, Booking, Payment, Feedback
- **Repositories**: JPA repositories with custom queries
- **Services**: Business logic layer
- **Controllers**: REST API endpoints
- **Security**: JWT authentication, BCrypt password encryption
- **Database**: Derby (embedded) for development

### Frontend (Angular)
- **Components**: Modular component architecture
- **Services**: HTTP client services for API communication
- **Guards**: Route protection with authentication
- **Models**: TypeScript interfaces for type safety
- **Routing**: Angular Router with lazy loading

## 📊 Pricing Formula

```
Service Cost = (BaseRate + WeightCharge + DeliveryCharge + PackingCharge + AdminFee) × (1 + Tax)

Where:
- BaseRate = ₹50
- WeightCharge = 0.02 × weight
- DeliveryCharge = ₹30 (Standard) | ₹80 (Express) | ₹150 (Same-day)
- PackingCharge = ₹10 (Basic) | ₹30 (Premium)
- AdminFee = ₹50 (Officer bookings only)
- Tax = 5%
```

## 🛠️ Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (Database Operations)
- **Derby Database** (Embedded)
- **Maven** (Dependency Management)

### Frontend
- **Angular 17**
- **TypeScript**
- **Bootstrap 5** (UI Framework)
- **RxJS** (Reactive Programming)
- **Angular Router** (Navigation)

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- npm or yarn
- Maven 3.6+

### Backend Setup

1. **Navigate to backend directory**:
   ```bash
   cd parcel-management-backend
   ```

2. **Install dependencies and run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Backend will start on**: `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**:
   ```bash
   cd parcel-management-frontend
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Start development server**:
   ```bash
   npm start
   ```

4. **Frontend will start on**: `http://localhost:4200`

## 📱 Usage

### Customer Workflow
1. **Register** → Create account with email, mobile, address
2. **Login** → Use generated Customer ID and password
3. **Book Parcel** → Fill booking form, see cost calculation
4. **Pay** → Process payment with card details
5. **Track** → Monitor parcel status with Booking ID
6. **Feedback** → Rate delivered parcels

### Officer Workflow
1. **Login** → Use Officer credentials
2. **Book for Customer** → Create bookings with admin fee
3. **Manage Status** → Update delivery status and times
4. **View All Bookings** → Monitor system-wide bookings
5. **Process Payments** → Handle office counter payments
6. **Review Feedback** → Monitor customer satisfaction

## 🔐 Security Features

- **JWT Authentication** - Secure token-based authentication
- **Password Encryption** - BCrypt hashing
- **Role-based Access** - Customer/Officer role separation
- **Route Guards** - Protected routes with authentication
- **CORS Configuration** - Cross-origin request handling
- **Input Validation** - Frontend and backend validation

## 📊 Database Schema

### Users Table
- Customer ID (Auto-generated)
- Personal Information
- Authentication Details
- Preferences

### Bookings Table
- Booking ID (Auto-generated)
- Customer & Officer References
- Receiver Details
- Parcel Information
- Cost Breakdown
- Status Tracking

### Payments Table
- Payment ID & Transaction ID
- Booking Reference
- Payment Method & Card Details
- Status & Timestamps

### Feedback Table
- Booking & Customer References
- Rating (1-5 stars)
- Description & Timestamp

## 🎨 UI/UX Features

- **Professional Design** - Bootstrap-based modern interface
- **Responsive Layout** - Mobile-friendly design
- **Interactive Forms** - Real-time validation and feedback
- **Status Indicators** - Color-coded status badges
- **Loading States** - User-friendly loading indicators
- **Error Handling** - Comprehensive error messages
- **Success Notifications** - Clear success confirmations

## 🔄 Status Flow

```
NEW → SCHEDULED → PICKED_UP → ASSIGNED → BOOKED → IN_TRANSIT → DELIVERED
                                                                    ↓
                                                              CANCELLED
```

## 📈 Future Enhancements

- **Email Notifications** - Automated status updates
- **SMS Integration** - Mobile notifications
- **Real-time Tracking** - GPS-based tracking
- **Mobile App** - Native mobile applications
- **Payment Gateway** - Integration with payment providers
- **Reporting Dashboard** - Analytics and insights
- **Multi-language Support** - Internationalization

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and queries, please contact the development team or create an issue in the repository.

---

**Built with ❤️ using Angular and Spring Boot**
