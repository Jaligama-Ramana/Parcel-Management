# 🚀 Parcel Management System - Setup & Testing Guide

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17 or higher** - [Download Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven 3.6+** - [Download Maven](https://maven.apache.org/download.cgi)
- **Node.js 18+** - [Download Node.js](https://nodejs.org/)
- **npm** (comes with Node.js)

## 🔧 Quick Setup

### 1. Backend Setup (Spring Boot)

```bash
# Navigate to backend directory
cd parcel-management-backend

# Install dependencies and run
mvn clean install
mvn spring-boot:run
```

**Backend will start on:** `http://localhost:8080`

### 2. Frontend Setup (Angular)

```bash
# Navigate to frontend directory
cd parcel-management-frontend

# Install dependencies
npm install

# Start development server
npm start
```

**Frontend will start on:** `http://localhost:4200`

## 🧪 Testing the Application

### Step 1: Access the Application
1. Open your browser and go to `http://localhost:4200`
2. You should see the Parcel Management System login page

### Step 2: Register a New Customer
1. Click "Register here" link
2. Fill out the registration form:
   - **Name**: John Doe
   - **Email**: john@example.com
   - **Country Code**: +91
   - **Mobile**: 9876543210
   - **Address**: 123 Main Street, City
   - **Password**: Test@123 (must have uppercase, lowercase, special char)
   - **Confirm Password**: Test@123
3. Click "Register"
4. Note the auto-generated **Customer ID** (e.g., CUST1234567890)

### Step 3: Login
1. Use the Customer ID and password to login
2. You'll be redirected to the Customer Dashboard

### Step 4: Test Navigation
- Click on different menu items in the navbar
- Navigate through the dashboard cards
- Test the logout functionality

## 🗂️ Project Structure

### Backend (Spring Boot)
```
parcel-management-backend/
├── src/main/java/com/parcelmanagement/
│   ├── entity/          # JPA Entities (User, Booking, Payment, Feedback)
│   ├── repository/      # JPA Repositories
│   ├── service/         # Business Logic Services
│   ├── controller/      # REST API Controllers
│   ├── dto/            # Data Transfer Objects
│   ├── config/         # Configuration Classes
│   └── security/       # Security & JWT Configuration
├── src/main/resources/
│   └── application.properties  # Database & App Configuration
└── pom.xml             # Maven Dependencies
```

### Frontend (Angular)
```
parcel-management-frontend/
├── src/app/
│   ├── components/     # Angular Components
│   ├── services/       # HTTP Services
│   ├── models/         # TypeScript Interfaces
│   ├── guards/         # Route Guards
│   ├── app.component.ts    # Main App Component
│   ├── app.routes.ts       # Routing Configuration
│   └── app.config.ts       # App Configuration
├── src/index.html      # Main HTML File
├── src/styles.css      # Global Styles
├── angular.json        # Angular Configuration
├── package.json        # Dependencies
└── tsconfig.json       # TypeScript Configuration
```

## 🔍 API Endpoints (Backend)

### Authentication
- `POST /api/auth/register` - Register new customer
- `POST /api/auth/login` - User login
- `POST /api/auth/create-officer` - Create officer account

### Bookings
- `POST /api/bookings/create` - Create new booking
- `GET /api/bookings/{bookingId}` - Get booking by ID
- `GET /api/bookings/customer/{customerId}` - Get customer bookings
- `PUT /api/bookings/{bookingId}/status` - Update booking status
- `PUT /api/bookings/{bookingId}/cancel` - Cancel booking

### Payments
- `POST /api/payments/process` - Process payment
- `GET /api/payments/booking/{bookingId}` - Get payment by booking ID

### Feedback
- `POST /api/feedback/add` - Add feedback
- `GET /api/feedback/all` - Get all feedback

## 🛠️ Development Commands

### Backend Commands
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Create JAR file
mvn clean package

# Run specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Frontend Commands
```bash
# Install dependencies
npm install

# Start development server
npm start

# Build for production
npm run build

# Run tests
npm test

# Check for linting issues
ng lint
```

## 🔧 Configuration

### Backend Configuration (application.properties)
```properties
# Database (Derby - embedded)
spring.datasource.url=jdbc:derby:parceldb;create=true
spring.datasource.driver-class-name=org.apache.derby.jdbc.EmbeddedDriver

# Server
server.port=8080
server.servlet.context-path=/api

# JWT
jwt.secret=mySecretKey123456789012345678901234567890
jwt.expiration=86400000
```

### Frontend Configuration
- **API Base URL**: `http://localhost:8080/api`
- **Default Port**: 4200
- **Bootstrap**: Included via CDN
- **FontAwesome**: Included via CDN

## 🐛 Troubleshooting

### Common Issues

1. **Backend won't start**
   - Check if Java 17+ is installed: `java -version`
   - Ensure port 8080 is not in use: `netstat -an | grep 8080`
   - Check Maven installation: `mvn -version`

2. **Frontend won't start**
   - Check Node.js version: `node --version` (should be 18+)
   - Clear npm cache: `npm cache clean --force`
   - Delete node_modules and reinstall: `rm -rf node_modules && npm install`

3. **CORS Issues**
   - Backend is configured to allow `http://localhost:4200`
   - If using different port, update CORS configuration in `SecurityConfig.java`

4. **Database Issues**
   - Derby database files are created automatically
   - To reset database, delete `parceldb` folder in backend directory

### Port Conflicts
If default ports are occupied:

**Backend (8080):**
```bash
mvn spring-boot:run -Dserver.port=8081
```

**Frontend (4200):**
```bash
ng serve --port 4201
```

## 📱 Features Currently Working

✅ **User Registration** - Complete with validation
✅ **User Login** - JWT authentication
✅ **Customer Dashboard** - Navigation and UI
✅ **Officer Dashboard** - Role-based access
✅ **Responsive Design** - Bootstrap styling
✅ **Route Protection** - Auth guards
✅ **Navigation** - Navbar with role-based menus

## 🚧 Features Under Development

🔄 **Booking System** - Form and cost calculation
🔄 **Payment Processing** - Card validation and processing
🔄 **Parcel Tracking** - Status tracking system
🔄 **Previous Bookings** - History with pagination
🔄 **Feedback System** - Rating and reviews

## 📞 Support

If you encounter any issues:

1. **Check the console** for error messages (F12 in browser)
2. **Verify all services are running** (backend on 8080, frontend on 4200)
3. **Check network requests** in browser dev tools
4. **Ensure proper CORS configuration** between frontend and backend

## 🎯 Testing Credentials

For testing purposes, you can create:

**Customer Account:**
- Register through the UI
- Use generated Customer ID for login

**Officer Account:**
- Use the `/api/auth/create-officer` endpoint
- Or modify a customer's role in the database

---

**🎉 Happy Testing!** The basic authentication and navigation system is fully functional. Additional features will be added incrementally.