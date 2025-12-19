# MealOps -- Role-Based Food Ordering System

## Overview

MealOps is a full‑stack application built using **React + ShadCN UI
(Frontend)** and **Spring Boot + PostgreSQL (Backend)**.\
It simulates a real-world food ordering platform with **role-based
access control (RBAC)** and **JWT authentication**, designed as an
interview-ready production-grade system.

------------------------------------------------------------------------

## 🚀 Tech Stack

### **Frontend**

-   React (Vite)
-   ShadCN UI (Radix + Tailwind)
-   Axios
-   React Router

### **Backend**

-   Java 17\
-   Spring Boot\
-   Spring Security (JWT)\
-   Spring Data JPA (Hibernate)\
-   PostgreSQL\
-   Maven\
-   Swagger / OpenAPI

------------------------------------------------------------------------

## 👥 Roles & Permissions

| Feature                 | Admin | Manager | Member |
|-------------------------|-------|---------|--------|
| View restaurants & menu | ✅     | ✅       | ✅      |
| Create order            | ✅     | ✅       | ✅      |
| Checkout order          | ✅     | ✅       | ❌      |
| Cancel order            | ✅     | ✅       | ❌      |
| Update payment method   | ✅     | ❌       | ❌      |

**Country Isolation:**\
Manager & Member can only view data belonging to their **assigned
country**.

------------------------------------------------------------------------

## 🔌 Core API Endpoints

### **Authentication**

`POST /auth/login`

### **Restaurants**

`GET /restaurants`\
`GET /restaurants/{id}/menu`

### **Menu Management (Admin/Manager)**

`POST /restaurants/{id}/menu`

### **Orders**

`POST /orders/create`\
`GET /orders/{id}`\
`POST /orders/{id}/checkout`\
`POST /orders/cancel/{id}`

### **Payment**

`GET /orders/payment/default`\
`PUT /orders/payment/default?method=UPI` (Admin only)

------------------------------------------------------------------------

## 🧱 Architecture Diagram

**Frontend**\
→ React Components\
→ API Layer (Axios)\
→ Protected Routes (Role based)

**Backend**\
Controller → Service → Repository → Database\
Security: JWT Filter → Spring Security Chain

------------------------------------------------------------------------

## 🗄️ Database (PostgreSQL)

Tables: - users\
- restaurants\
- menu_items\
- orders\
- order_items\
- country\
- payment_config

Indexes added for: - username\
- restaurant_id\
- country_id

------------------------------------------------------------------------

## 🏗️ How to Run Locally

### **1. Setup PostgreSQL**

Create a DB:

``` sql
CREATE DATABASE mealops;
```

### **2. Update Spring Boot config**

Edit `application.yml`:

``` yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mealops
    username: postgres
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### **3. Run Backend**

``` sh
mvn spring-boot:run
```

Swagger docs:\
`http://localhost:8080/swagger-ui/index.html`

------------------------------------------------------------------------

## 🖥️ Run Frontend

``` sh
npm install
npm run dev
```

Runs at:\
`http://localhost:5173/`

------------------------------------------------------------------------
## Notes
- Payment gateway is mocked for simplicity.
- Enums used for payment methods to maintain type safety.
- Designed for extensibility (can migrate to DB-driven payment methods).