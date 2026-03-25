# Salon Backend API - Spring Boot Documentation

## Project Overview

This is a Java Spring Boot backend API for the Salon Management System. It provides REST APIs for:
- Owner Authentication (Login/Signup)
- Client Authentication (Login/Signup)
- User Profile Management
- JWT Token-based Security

## Project Structure

```
salon-backend/
├── pom.xml                                 # Maven dependencies
├── src/
│   ├── main/
│   │   ├── java/com/salon/
│   │   │   ├── SalonBackendApplication.java       # Main Spring Boot class
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java            # Spring Security configuration
│   │   │   │   └── JwtTokenProvider.java          # JWT token generation/validation
│   │   │   ├── controller/
│   │   │   │   ├── OwnerAuthController.java       # Owner API endpoints
│   │   │   │   └── ClientAuthController.java      # Client API endpoints
│   │   │   ├── service/
│   │   │   │   ├── OwnerService.java              # Owner business logic
│   │   │   │   └── ClientService.java             # Client business logic
│   │   │   ├── model/
│   │   │   │   ├── Owner.java                     # Owner entity
│   │   │   │   └── Client.java                    # Client entity
│   │   │   ├── repository/
│   │   │   │   ├── OwnerRepository.java           # Owner data access
│   │   │   │   └── ClientRepository.java          # Client data access
│   │   │   └── dto/
│   │   │       ├── LoginRequest.java
│   │   │       ├── LoginResponse.java
│   │   │       ├── OwnerSignupRequest.java
│   │   │       ├── ClientSignupRequest.java
│   │   │       ├── SignupResponse.java
│   │   │       ├── UserDto.java
│   │   │       └── ApiResponse.java
│   │   └── resources/
│   │       └── application.properties             # Application configuration
│   └── test/                                       # Unit tests
```

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+
- Git

## Installation & Setup

### 1. Clone/Download Project
```bash
cd c:\Users\kanna\Desktop\NEW\salon-backend
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Database Setup
```sql
CREATE DATABASE salon_db;
USE salon_db;
```

### 4. Configure Database Connection
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/salon_db
spring.datasource.username=your_mysql_user
spring.datasource.password=your_mysql_password
```

### 5. Run Application
```bash
mvn spring-boot:run
```

Or use IDE's run button. Application will start on `http://localhost:8080`

## API Endpoints

### Base URL
```
http://localhost:8080/api
```

### OWNER AUTHENTICATION

#### 1. Owner Signup
```
POST /auth/owner/signup
Content-Type: application/json

{
  "name": "John Salon",
  "email": "owner@salon.com",
  "password": "SecurePass123",
  "phone": "9876543210",
  "salonName": "Luxe Hair Salon",
  "address": "123 Main Street, City, Country"
}

Response (201 Created):
{
  "success": true,
  "message": "Owner registered successfully",
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": 1,
    "name": "John Salon",
    "email": "owner@salon.com",
    "phone": "9876543210",
    "role": "owner",
    "salonName": "Luxe Hair Salon",
    "address": "123 Main Street, City, Country"
  }
}
```

#### 2. Owner Login
```
POST /auth/owner/login
Content-Type: application/json

{
  "email": "owner@salon.com",
  "password": "SecurePass123",
  "role": "owner"
}

Response (200 OK):
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": 1,
    "name": "John Salon",
    "email": "owner@salon.com",
    "phone": "9876543210",
    "role": "owner",
    "salonName": "Luxe Hair Salon",
    "address": "123 Main Street, City, Country"
  }
}
```

#### 3. Verify Owner Token
```
POST /auth/owner/verify
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...

Response (200 OK):
{
  "success": true,
  "message": "Token is valid"
}
```

### CLIENT AUTHENTICATION

#### 1. Client Signup
```
POST /auth/client/signup
Content-Type: application/json

{
  "name": "Maria Johnson",
  "email": "maria@email.com",
  "password": "ClientPass123",
  "phone": "9876543210"
}

Response (201 Created):
{
  "success": true,
  "message": "Client registered successfully",
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": 1,
    "name": "Maria Johnson",
    "email": "maria@email.com",
    "phone": "9876543210",
    "role": "client",
    "salonName": null,
    "address": null
  }
}
```

#### 2. Client Login
```
POST /auth/client/login
Content-Type: application/json

{
  "email": "maria@email.com",
  "password": "ClientPass123",
  "role": "client"
}

Response (200 OK):
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": 1,
    "name": "Maria Johnson",
    "email": "maria@email.com",
    "phone": "9876543210",
    "role": "client",
    "salonName": null,
    "address": null
  }
}
```

#### 3. Verify Client Token
```
POST /auth/client/verify
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...

Response (200 OK):
{
  "success": true,
  "message": "Token is valid"
}
```

## Error Responses

### 400 Bad Request
```json
{
  "success": false,
  "message": "Email already registered"
}
```

### 401 Unauthorized
```json
{
  "success": false,
  "message": "Invalid password"
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "Owner not found with email: test@email.com"
}
```

## Data Models

### Owner Entity
```java
{
  id: Long,
  name: String,
  email: String (unique),
  password: String (encrypted),
  phone: String,
  salonName: String,
  address: String,
  active: Boolean,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime
}
```

### Client Entity
```java
{
  id: Long,
  name: String,
  email: String (unique),
  password: String (encrypted),
  phone: String,
  active: Boolean,
  createdAt: LocalDateTime,
  updatedAt: LocalDateTime
}
```

## Security Features

### JWT Authentication
- Token-based authentication using JWT
- Token expiration: 24 hours
- Secret key: Configurable in `application.properties`

### Password Encryption
- Uses BCrypt encryption
- Password hashed before storage in database

### CORS Configuration
- Allows requests from `http://localhost:3000` (React frontend)
- Supports cross-origin requests with proper headers

### Input Validation
- Email format validation
- Phone number validation (10 digits)
- Required field validation

## Testing APIs

### Using cURL
```bash
# Owner Signup
curl -X POST http://localhost:8080/api/auth/owner/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Salon",
    "email": "owner@salon.com",
    "password": "SecurePass123",
    "phone": "9876543210",
    "salonName": "Luxe Hair Salon",
    "address": "123 Main Street, City, Country"
  }'

# Owner Login
curl -X POST http://localhost:8080/api/auth/owner/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "owner@salon.com",
    "password": "SecurePass123",
    "role": "owner"
  }'
```

### Using Postman
1. Create a new collection "Salon API"
2. Add requests for each endpoint
3. Set headers: `Content-Type: application/json`
4. For token verification, add: `Authorization: Bearer <token>`

## Database Schema

### owners table
```sql
CREATE TABLE owners (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  salon_name VARCHAR(255) NOT NULL,
  address TEXT NOT NULL,
  active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### clients table
```sql
CREATE TABLE clients (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Dependencies

- **Spring Boot 3.1.5**: Web framework
- **Spring Data JPA**: ORM and database access
- **Spring Security**: Authentication and authorization
- **JWT (JJWT 0.12.3)**: Token management
- **MySQL Connector**: Database driver
- **Lombok**: Code generation
- **Validation**: Form validation

## Configuration

### JWT Secret Key
Generate a secure key and update in `application.properties`:
```properties
jwt.secret=YourSuperSecureSecretKeyHere!
jwt.expiration=86400000
```

### Database Credentials
Update credentials for your environment:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/salon_db
spring.datasource.username=root
spring.datasource.password=password
```

## Common Issues & Solutions

### Issue: Can't connect to database
**Solution**: 
- Verify MySQL is running
- Check credentials in `application.properties`
- Ensure `salon_db` database exists

### Issue: Port 8080 already in use
**Solution**: Change port in `application.properties`
```properties
server.port=8081
```

### Issue: CORS errors from frontend
**Solution**: 
- Check `SecurityConfig.java` corsConfigurationSource()
- Ensure frontend URL is in allowed origins
- Add origin if needed

## Frontend Integration

### Update React Frontend

In your React app, update API calls:

```javascript
const loginOwner = async (email, password) => {
  const response = await fetch('http://localhost:8080/api/auth/owner/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password, role: 'owner' })
  });
  return response.json();
};
```

Store JWT token in localStorage:
```javascript
localStorage.setItem('token', response.token);
localStorage.setItem('user', JSON.stringify(response.user));
```

## Next Steps

1. **Add profile endpoints**
   - GET /api/owner/{id}
   - PUT /api/owner/{id}

2. **Add service management**
   - POST /api/owner/services
   - GET /api/owner/services

3. **Add appointment management**
   - POST /api/client/appointments
   - GET /api/appointments

4. **Add review/rating system**
   - POST /api/client/reviews

5. **Implement email notifications**
   - Confirm registration
   - Reset password
   - Appointment reminders

## Troubleshooting

For detailed logs, update `application.properties`:
```properties
logging.level.com.salon=DEBUG
logging.level.org.hibernate.SQL=DEBUG
spring.jpa.show-sql=true
```

## Support & Documentation

- Spring Boot Docs: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- JWT: https://jwt.io
- MySQL: https://www.mysql.com

---

**Status**: ✅ Ready for Frontend Integration
**Version**: 1.0.0
**Last Updated**: 2026-03-24
