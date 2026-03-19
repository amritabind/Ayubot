# AyuBot Authentication System - Setup Guide

## Overview
The authentication system has been upgraded to a fully functional, production-ready implementation with:
- ✅ BCrypt password hashing
- ✅ JWT token-based authentication
- ✅ Secure registration and login endpoints
- ✅ Client-side and server-side validation
- ✅ Error handling and user feedback

## Backend Changes

### 1. Dependencies Added (pom.xml)
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` (v0.11.5) - For JWT token generation

### 2. New/Updated Files

#### `JwtUtil.java` (NEW)
- Location: `src/main/java/com/ayubot/util/JwtUtil.java`
- Handles JWT token generation and validation
- Tokens expire after 24 hours
- Contains user email, role, and userId in the token payload

#### `User.java` (UPDATED)
- Changed ID type from `int` to `Long`
- Added `@GeneratedValue(strategy = GenerationType.IDENTITY)` for auto-increment

#### `UserRepository.java` (UPDATED)
- Added `findByEmail()` method for authentication
- Changed generic type from `Integer` to `Long`

#### `UserService.java` (UPDATED)
- Added `registerUser()` - Registers new users with encrypted passwords
- Added `authenticateUser()` - Validates user credentials
- Uses BCrypt for password hashing and verification

#### `AuthController.java` (UPDATED)
- `/api/register` - User registration endpoint
- `/api/login` - User login endpoint
- `/api/verify-token` - Token validation endpoint (optional)
- Proper error handling and validation
- Returns JWT token on successful authentication

## Frontend Changes

### 1. `login.html` (UPDATED)
- Connects to `/api/login` endpoint
- Validates credentials with backend
- Stores JWT token and user info in localStorage
- Shows loading states and error messages
- Auto-redirects to appropriate dashboard based on role
- Checks if user is already logged in on page load

### 2. `register.html` (UPDATED)
- Connects to `/api/register` endpoint
- Client-side and server-side validation
- Creates new user accounts with encrypted passwords
- Automatically logs in user after successful registration
- Shows success/error messages with auto-dismiss

## Database Setup

### Option 1: Let Spring Boot Auto-Create Tables
1. Ensure `application.properties` has:
   ```properties
   spring.jpa.hibernate.ddl-auto=create
   ```
2. Start the backend - tables will be created automatically
3. Run `insert-demo-users.sql` to add demo accounts:
   ```bash
   mysql -u root -p ayubot < insert-demo-users.sql
   ```

### Option 2: Manual Setup
1. Run `database.sql` to create the database structure
2. Run `insert-demo-users.sql` to add demo accounts

## Demo Accounts
Both demo accounts use password: `123`

- **Patient Account**
  - Email: `patient@ayubot.com`
  - Password: `123`
  
- **Doctor Account**
  - Email: `doctor@ayubot.com`
  - Password: `123`

## How to Test

### 1. Start the Backend
```bash
cd backend/ayubot
./mvnw spring-boot:run
```
Backend will run on `http://localhost:8080`

### 2. Setup Database
Run the demo users SQL script:
```bash
mysql -u root -p ayubot < insert-demo-users.sql
```

### 3. Open Frontend
Open `frontend/login.html` or `frontend/register.html` in a web browser

### 4. Test Registration
1. Go to `register.html`
2. Fill in the form:
   - Name: Your Name
   - Email: youremail@example.com
   - Password: yourpassword
   - Role: PATIENT or DOCTOR
3. Click "Create Account"
4. Should redirect to appropriate dashboard

### 5. Test Login
1. Go to `login.html`
2. Enter credentials (use demo accounts or your registered account)
3. Click "LOGIN"
4. Should redirect to appropriate dashboard

## API Endpoints

### POST /api/register
**Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securepassword",
  "role": "PATIENT"
}
```

**Success Response (201):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "PATIENT",
  "email": "john@example.com",
  "name": "John Doe",
  "userId": 1,
  "message": "Registration successful"
}
```

**Error Response (400):**
```json
{
  "error": "Email already registered"
}
```

### POST /api/login
**Request:**
```json
{
  "email": "john@example.com",
  "password": "securepassword"
}
```

**Success Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "PATIENT",
  "email": "john@example.com",
  "name": "John Doe",
  "userId": 1,
  "message": "Login successful"
}
```

**Error Response (401):**
```json
{
  "error": "Invalid email or password"
}
```

### POST /api/verify-token
**Headers:**
```
Authorization: Bearer <jwt-token>
```

**Success Response (200):**
```json
{
  "valid": true,
  "email": "john@example.com",
  "role": "PATIENT",
  "userId": 1
}
```

## Security Features

1. **Password Hashing**: All passwords are hashed using BCrypt (strength: 10)
2. **JWT Tokens**: Secure token-based authentication
3. **Token Expiration**: Tokens expire after 24 hours
4. **CORS Enabled**: Cross-origin requests allowed for development
5. **Input Validation**: Both client-side and server-side validation
6. **SQL Injection Prevention**: Using JPA/Hibernate parameterized queries
7. **Email Uniqueness**: Enforced at database level

## LocalStorage Data
After login/registration, the following data is stored:
- `token` - JWT authentication token
- `userRole` - User's role (PATIENT/DOCTOR)
- `userEmail` - User's email
- `userName` - User's full name
- `userId` - User's database ID

## Troubleshooting

### Issue: "Unable to connect to server"
- Ensure backend is running on port 8080
- Check if MySQL is running
- Verify database connection in `application.properties`

### Issue: "Email already registered"
- User with that email already exists
- Use a different email or login instead

### Issue: "Invalid email or password"
- Check credentials are correct
- Verify user exists in database
- For demo accounts, ensure `insert-demo-users.sql` was run

### Issue: Token validation fails
- Token may have expired (24 hours)
- Clear localStorage and login again
- Check JWT secret key matches in backend

## Next Steps (Optional Enhancements)

1. **Email Verification**: Send verification email on registration
2. **Password Reset**: Forgot password functionality
3. **Refresh Tokens**: Implement token refresh mechanism
4. **Session Management**: Track active sessions
5. **Role-Based Access Control**: Protect specific endpoints by role
6. **Account Settings**: Allow users to update profile
7. **Multi-Factor Authentication**: Add 2FA for enhanced security

## Production Considerations

Before deploying to production:

1. **Move JWT Secret to Environment Variable**
   ```properties
   jwt.secret=${JWT_SECRET}
   ```

2. **Enable HTTPS**
   - Use SSL/TLS certificates
   - Update CORS settings

3. **Increase Password Complexity Requirements**
   - Minimum 8 characters
   - Require uppercase, lowercase, numbers, special chars

4. **Rate Limiting**
   - Implement rate limiting on login/register endpoints

5. **Logging & Monitoring**
   - Log authentication attempts
   - Monitor for suspicious activity

6. **Database Security**
   - Use strong database passwords
   - Limit database user permissions
   - Enable SSL for database connections

---

**System is now fully functional with secure authentication! 🎉**
