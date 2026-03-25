# Salon Backend API Reference

## Base URL
```
http://localhost:8080/api
```

## API Endpoints Summary

| Endpoint | Method | Auth | Purpose |
|----------|--------|------|---------|
| `/auth/owner/signup` | POST | No | Register new owner |
| `/auth/owner/login` | POST | No | Owner login |
| `/auth/owner/verify` | POST | No | Verify owner token |
| `/auth/client/signup` | POST | No | Register new client |
| `/auth/client/login` | POST | No | Client login |
| `/auth/client/verify` | POST | No | Verify client token |

---

## DETAILED API DOCUMENTATION

### 1. OWNER SIGNUP

**Endpoint:** `POST /auth/owner/signup`

**Description:** Register a new salon owner account

**Request Body:**
```json
{
  "name": "John Smith",
  "email": "john@salon.com",
  "password": "SecurePassword123",
  "phone": "9876543210",
  "salonName": "Luxe Hair & Salon",
  "address": "123 Main Street, New York, NY 10001"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Owner registered successfully",
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "name": "John Smith",
    "email": "john@salon.com",
    "phone": "9876543210",
    "role": "owner",
    "salonName": "Luxe Hair & Salon",
    "address": "123 Main Street, New York, NY 10001"
  }
}
```

**Error Response (400 if email exists):**
```json
{
  "success": false,
  "message": "Email already registered",
  "token": null,
  "user": null
}
```

---

### 2. OWNER LOGIN

**Endpoint:** `POST /auth/owner/login`

**Description:** Login with owner credentials

**Request Body:**
```json
{
  "email": "john@salon.com",
  "password": "SecurePassword123",
  "role": "owner"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "name": "John Smith",
    "email": "john@salon.com",
    "phone": "9876543210",
    "role": "owner",
    "salonName": "Luxe Hair & Salon",
    "address": "123 Main Street, New York, NY 10001"
  }
}
```

**Error Response (401 if invalid credentials):**
```json
{
  "success": false,
  "message": "Invalid password",
  "token": null,
  "user": null
}
```

---

### 3. OWNER TOKEN VERIFICATION

**Endpoint:** `POST /auth/owner/verify`

**Description:** Verify if JWT token is valid

**Request Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Token is valid"
}
```

**Error Response (400 if invalid token):**
```json
{
  "success": false,
  "message": "Invalid token format"
}
```

---

### 4. CLIENT SIGNUP

**Endpoint:** `POST /auth/client/signup`

**Description:** Register a new client account

**Request Body:**
```json
{
  "name": "Maria Garcia",
  "email": "maria@email.com",
  "password": "ClientPassword123",
  "phone": "9876543210"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Client registered successfully",
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "name": "Maria Garcia",
    "email": "maria@email.com",
    "phone": "9876543210",
    "role": "client",
    "salonName": null,
    "address": null
  }
}
```

**Error Response (400 if email exists):**
```json
{
  "success": false,
  "message": "Email already registered",
  "token": null,
  "user": null
}
```

---

### 5. CLIENT LOGIN

**Endpoint:** `POST /auth/client/login`

**Description:** Login with client credentials

**Request Body:**
```json
{
  "email": "maria@email.com",
  "password": "ClientPassword123",
  "role": "client"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "name": "Maria Garcia",
    "email": "maria@email.com",
    "phone": "9876543210",
    "role": "client",
    "salonName": null,
    "address": null
  }
}
```

**Error Response (401 if invalid credentials):**
```json
{
  "success": false,
  "message": "Invalid password",
  "token": null,
  "user": null
}
```

---

### 6. CLIENT TOKEN VERIFICATION

**Endpoint:** `POST /auth/client/verify`

**Description:** Verify if client JWT token is valid

**Request Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Token is valid"
}
```

---

## HTTP Status Codes

| Code | Meaning | Usage |
|------|---------|-------|
| 200 | OK | Successful GET/POST request |
| 201 | Created | Successful resource creation |
| 400 | Bad Request | Invalid input or duplicate email |
| 401 | Unauthorized | Invalid credentials |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Backend error |

---

## Input Validation Rules

### Owner Signup
- **Name**: Required, string
- **Email**: Required, valid email format, unique
- **Password**: Required, minimum 6 characters
- **Phone**: Required, exactly 10 digits
- **Salon Name**: Required, string
- **Address**: Required, string (can be multi-line)

### Client Signup
- **Name**: Required, string
- **Email**: Required, valid email format, unique
- **Password**: Required, minimum 6 characters
- **Phone**: Required, exactly 10 digits

### Login (Both)
- **Email**: Required, valid email format
- **Password**: Required
- **Role**: Required ("owner" or "client")

---

## Request Headers

All requests should include:
```
Content-Type: application/json
```

For authenticated endpoints, include:
```
Authorization: Bearer <jwt_token>
```

---

## JWT Token Structure

The token contains:
- **Subject (sub)**: User email
- **UserId (userId)**: User ID
- **Role (role)**: "owner" or "client"
- **Issued At (iat)**: Token creation timestamp
- **Expiration (exp)**: Token expiration timestamp (24 hours)

---

## Error Codes & Messages

| Error | Status | Meaning |
|-------|--------|---------|
| Email already registered | 400 | User with this email exists |
| Owner not found | 401 | Email doesn't exist for owner |
| Client not found | 401 | Email doesn't exist for client |
| Invalid password | 401 | Wrong password provided |
| Invalid token format | 400 | Token missing or malformed |
| Account inactive | 401 | Owner/Client account disabled |

---

## Response Format

### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "token": "jwt_token_here",
  "user": {
    "id": 1,
    "name": "User Name",
    "email": "user@email.com",
    "phone": "9876543210",
    "role": "owner/client"
  }
}
```

### Error Response
```json
{
  "success": false,
  "message": "Error description",
  "token": null,
  "user": null
}
```

---

## Testing with cURL

### Owner Signup
```bash
curl -X POST http://localhost:8080/api/auth/owner/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john@salon.com",
    "password": "SecurePassword123",
    "phone": "9876543210",
    "salonName": "Luxe Hair & Salon",
    "address": "123 Main Street, New York, NY 10001"
  }'
```

### Owner Login
```bash
curl -X POST http://localhost:8080/api/auth/owner/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@salon.com",
    "password": "SecurePassword123",
    "role": "owner"
  }'
```

### Client Signup
```bash
curl -X POST http://localhost:8080/api/auth/client/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Garcia",
    "email": "maria@email.com",
    "password": "ClientPassword123",
    "phone": "9876543210"
  }'
```

### Client Login
```bash
curl -X POST http://localhost:8080/api/auth/client/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "maria@email.com",
    "password": "ClientPassword123",
    "role": "client"
  }'
```

---

## Future Endpoints (To Be Implemented)

### Owner Management
- GET `/api/owner/{id}` - Get owner profile
- PUT `/api/owner/{id}` - Update owner profile
- DELETE `/api/owner/{id}` - Deactivate owner account

### Services Management
- POST `/api/owner/services` - Create service
- GET `/api/owner/services` - List services
- PUT `/api/owner/services/{id}` - Update service
- DELETE `/api/owner/services/{id}` - Delete service

### Appointments
- POST `/api/client/appointments` - Book appointment
- GET `/api/appointments` - Get appointments
- PUT `/api/appointments/{id}` - Update appointment
- DELETE `/api/appointments/{id}` - Cancel appointment

### Reviews & Ratings
- POST `/api/client/reviews` - Submit review
- GET `/api/salon/{id}/reviews` - Get salon reviews

---

**API Version**: 1.0.0
**Last Updated**: 2026-03-24
**Maintained By**: Salon Backend Team
