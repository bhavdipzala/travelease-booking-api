# TravelEase

A RESTful API backend for a travel package booking system, built with Java and Spring Boot.
Supports two roles — **User** and **Admin** — with HTTP Basic Authentication and role-based 
access control. Users can browse, wishlist, and book travel packages, while admins manage 
packages, users, and bookings through dedicated admin endpoints.

---

## Tech Stack

- **Java**
- **Spring Boot**
- **Spring Security** (HTTP Basic Auth + Role-Based Access Control)
- **Spring Data JPA** (Hibernate)
- **PostgreSQL**
- **Lombok**
- **Swagger / OpenAPI 3** (API Documentation)
- **Maven**

---

## Features

### User
- Register and login
- Browse & filter all travel packages
- Book a travel package
- View booking history
- Filter bookings by status

### Admin
- Add, update, and delete travel packages
- View all users, search by email
- Block / unblock users
- View all bookings with filters (by user, by status)
- Cancel or update any booking

---

## Project Structure

```
src/
└── main/
    └── java/com/bhavdip/travelease/
        ├── config/          # Security, Swagger, Password encoder config
        ├── controller/      # REST controllers (User, Package, Booking)
        ├── dto/             # Request and Response DTOs
        ├── exception/       # Global exception handler + custom exceptions
        ├── model/           # JPA entities (User, TravelPackage, Booking)
        ├── repository/      # Spring Data JPA repositories
        └── service/         # Business logic services
```

---

## API Endpoints

### Auth
All endpoints use **HTTP Basic Authentication** (email + password).  
Register via `POST /api/users` (no auth required), then use credentials on subsequent requests.

---

### Users — `/api/users`

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/api/users` | Public | Register a new user |
| GET | `/api/users` | ADMIN | Get all users |
| GET | `/api/users/{userId}` | ADMIN | Get user by ID |
| GET | `/api/users/search?email=` | ADMIN | Search user by email |
| PATCH | `/api/users/{userId}/update` | ADMIN | Update user name and email |
| PATCH | `/api/users/{userId}/block` | ADMIN | Block a user |
| PATCH | `/api/users/{userId}/unblock` | ADMIN | Unblock a user |
| DELETE | `/api/users/{userId}` | ADMIN | Delete a user |

---

### Travel Packages — `/api/packages`

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/api/packages` | Public | Get all packages (filterable by `destination`, `title`) |
| GET | `/api/packages/{packageId}` | Public | Get package by ID |
| POST | `/api/packages` | ADMIN | Create a new package |
| PATCH | `/api/packages/{packageId}` | ADMIN | Update a package |
| DELETE | `/api/packages/{packageId}` | ADMIN | Delete a package |

#### Package filter query params (Public):
- `GET /api/packages?destination=Paris` — packages by destination
- `GET /api/packages?title=Summer Adventure` — packages by title

---

### Bookings — `/api/bookings`

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/api/bookings` | Authenticated | Create a booking |
| GET | `/api/bookings` | ADMIN | Get all bookings (filterable) |
| GET | `/api/bookings/{bookingId}` | Authenticated | Get booking by ID |
| PATCH | `/api/bookings/{bookingId}/cancel` | ADMIN | Cancel a booking |
| PATCH | `/api/bookings/{bookingId}/update?packageId=` | ADMIN | Update booking package |

#### Booking filter query params (Admin):
- `GET /api/bookings?userId=1` — bookings by user
- `GET /api/bookings?bookingStatus=CANCELLED` — bookings by status
- `GET /api/bookings?userId=1&bookingStatus=BOOKED` — combined filter

---

## Getting Started

### Prerequisites
- Java
- PostgreSQL
- Maven

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/travelease.git
   cd travelease
   ```

2. **Create a PostgreSQL database**
   ```sql
   CREATE DATABASE travelease;
   ```

3. **Configure environment**

   Update `src/main/resources/application.properties` with your DB credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/travelease
   spring.datasource.username=YOUR_DB_USERNAME
   spring.datasource.password=YOUR_DB_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   ```
   > ⚠️ Do not commit real credentials to version control. Use environment variables in production.

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access Swagger UI**

   Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to explore and test all endpoints interactively.

---

## Sample Request — Register User

```json
POST /api/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

## Sample Request — Create Booking

```json
POST /api/bookings
Authorization: Basic <base64(email:password)>
Content-Type: application/json

{
  "userId": 1,
  "packageId": 2
}
```

---

## Booking Status Values

| Status | Description |
|--------|-------------|
| `BOOKED` | Booking confirmed |
| `CANCELLED` | Booking cancelled |
| `PENDING` | Awaiting confirmation |
| `FAILED` | Booking failed |

---

## Author

**Bhavdip Zala**  
MSc Computer Science — University of Kent  
[zalabhavdip01@gmail.com](mailto:zalabhavdip01@gmail.com)
