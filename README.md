# Medical Prescription Management System

A Spring Boot REST API application for managing medical prescriptions, patients, doctors, and pharmacists with JWT-based authentication.

## Features

- **User Authentication & Authorization**
  - JWT-based security
  - Role-based access control (Doctor, Patient, Pharmacist)
  - Secure login and registration endpoints

- **Patient Management**
  - Create and manage patient records
  - View patient information
  - Link prescriptions to patients

- **Prescription Management**
  - Doctors can create prescriptions for patients
  - Pharmacists can view and process prescriptions
  - Track prescription status

- **Pharmacist Operations**
  - Manage pharmacist profiles
  - Access to prescription information

## Technologies Used

- **Java** - Programming language
- **Spring Boot** - Backend framework
- **Spring Security** - Authentication and authorization
- **JWT (JSON Web Tokens)** - Secure token-based authentication
- **JPA/Hibernate** - ORM for database operations
- **Maven** - Dependency management and build tool
- **MySQL/PostgreSQL** - Database (configurable)

## Project Structure

```
Medical_b/
├── src/
│   ├── main/
│   │   ├── java/com/example/Medical/
│   │   │   ├── MedicalApplication.java          # Main application class
│   │   │   ├── Config/
│   │   │   │   ├── JwtAuthFilter.java           # JWT authentication filter
│   │   │   │   └── SecurityConfig.java          # Security configuration
│   │   │   ├── Controller/
│   │   │   │   ├── AuthController.java          # Authentication endpoints
│   │   │   │   ├── PatientController.java       # Patient management endpoints
│   │   │   │   └── PharmacistController.java    # Pharmacist endpoints
│   │   │   ├── DTO/
│   │   │   │   ├── AuthResponse.java            # Authentication response
│   │   │   │   ├── DoctorSignupRequest.java     # Doctor registration DTO
│   │   │   │   ├── LoginRequest.java            # Login request DTO
│   │   │   │   ├── PatientRequest.java          # Patient data DTO
│   │   │   │   ├── PharmacistSignupRequest.java # Pharmacist registration DTO
│   │   │   │   └── PrescriptionRequest.java     # Prescription data DTO
│   │   │   ├── model/
│   │   │   │   ├── Doctor.java                  # Doctor entity
│   │   │   │   ├── Patient.java                 # Patient entity
│   │   │   │   ├── Pharmacist.java              # Pharmacist entity
│   │   │   │   └── Prescription.java            # Prescription entity
│   │   │   ├── Repository/
│   │   │   │   ├── DoctorRepository.java
│   │   │   │   ├── PatientRepository.java
│   │   │   │   ├── PharmacistRepository.java
│   │   │   │   └── PrescriptionRepository.java
│   │   │   ├── Security/
│   │   │   │   └── JwtService.java              # JWT utility service
│   │   │   └── Service/
│   │   │       ├── AuthService.java             # Authentication service
│   │   │       ├── PatientService.java          # Patient business logic
│   │   │       └── PrescriptionService.java     # Prescription business logic
│   │   └── resources/
│   │       └── application.properties           # Application configuration
│   └── test/
│       └── java/com/example/Medical/
│           ├── MedicalApplicationTests.java
│           ├── Controller/
│           ├── Integration/
│           ├── Repository/
│           └── Service/
└── pom.xml                                       # Maven dependencies
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL/PostgreSQL database
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Medical_b
   ```

2. **Configure the database**
   
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/medical_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   server.port=9090
   
   # JWT Configuration
   jwt.secret=your_secret_key
   jwt.expiration=86400000
   ```

3. **Create the database**
   ```sql
   CREATE DATABASE medical_db;
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   
   Or using Maven wrapper:
   ```bash
   ./mvnw spring-boot:run    # Linux/Mac
   .\mvnw.cmd spring-boot:run  # Windows
   ```

The application will start on `http://localhost:9090`


## Authentication

The API uses JWT (JSON Web Token) for authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

### Example Authentication Flow

1. **Register** (Doctor/Pharmacist)
   ```bash
   POST /auth/doctor/signup
   {
     "name": "Dr. John Doe",
     "email": "doctor@example.com",
     "password": "securePassword123",
     "specialization": "Cardiology"
   }
   ```

2. **Login**
   ```bash
   POST /auth/login
   {
     "email": "doctor@example.com",
     "password": "securePassword123"
   }
   ```
   
   Response:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     "type": "Bearer"
   }
   ```

3. **Use the token** in subsequent requests:
   ```bash
   GET /patients
   Headers: Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```


## Configuration

Key configuration properties in `application.properties`:

- **Server Port**: `server.port=9090`
- **Database URL**: `spring.datasource.url`
- **JWT Secret**: `jwt.secret`
- **JWT Expiration**: `jwt.expiration`
- **Hibernate DDL**: `spring.jpa.hibernate.ddl-auto`

## Development

### Running in Development Mode

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Building for Production

```bash
mvn clean package -DskipTests
java -jar target/Medical-0.0.1-SNAPSHOT.jar
```

## Security Features

- Password encryption using BCrypt
- JWT token-based authentication
- Role-based authorization
- Secure HTTP endpoints
- CORS configuration

