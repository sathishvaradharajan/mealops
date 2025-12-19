
# MealOps – Role-Based Food Ordering System

## Overview
MealOps is a full-stack backend application that simulates a real-world food ordering platform with role-based access control (RBAC), country-level data isolation, and JWT-based authentication.

Built as an interview-ready project demonstrating clean architecture, security, and real business rules.

---

## Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Swagger (OpenAPI)

---

## Roles & Access
| Feature                 | Admin | Manager | Member |
|-------------------------|-------|---------|--------|
| View restaurants & menu | ✅     | ✅       | ✅      |
| Create order            | ✅     | ✅       | ✅      |
| Checkout order          | ✅     | ✅       | ❌      |
| Cancel order            | ✅     | ✅       | ❌      |
| Update payment method   | ✅     | ❌       | ❌      |

**Bonus:**  
Managers & Members can only access data related to their own country.

---

## Core APIs

### Authentication
POST `/auth/login`

### Restaurants & Menu
GET `/restaurants`  
GET `/restaurants/{id}/menu`  
POST `/restaurants/{id}/menu` (ADMIN, MANAGER)

### Orders
POST `/orders/create`  
POST `/orders/checkout/{orderId}`  
POST `/orders/cancel/{orderId}`  
PUT `/orders/payment/{orderId}` (ADMIN only)

### Payment Config
PUT `/orders/payment/default` (ADMIN)

---

## Architecture
Controller → Service → Repository → Database  
Security handled via JWT filter and Spring Security configuration.  
Business rules enforced at **service layer**, not controller.

---

## How to Run
1. Create PostgreSQL DB
2. Update `application.yml`
3. Run Spring Boot app
4. Access Swagger at:
   http://localhost:8080/swagger-ui.html

## Notes
- Payment gateway is mocked for simplicity.
- Enums used for payment methods to maintain type safety.
- Designed for extensibility (can migrate to DB-driven payment methods).

---

