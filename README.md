
# 🐾 Pet Store REST API

A simple, layered **Spring Boot REST API** for managing a pet store. It supports customer registration, pet inventory, and ordering/returning pets.

---

## 📦 Features

- 🔐 Customer registration with secure password encoding
- 🐶 CRUD operations for Pets
- 🧾 Create and manage Orders
- 🧩 Layered architecture (Controller → Service → Repository)
- 🔄 DTOs and Entity conversion with ModelMapper
- 💾 PostgreSQL integration
- ❌ Global exception handling with custom error messages

---

## 🛠️ Tech Stack

| Tech               | Description                  |
|--------------------|------------------------------|
| Java 17            | Programming Language         |
| Spring Boot 3.4.4  | Backend Framework            |
| Spring Data JPA    | ORM and database operations  |
| PostgreSQL         | Relational Database          |
| ModelMapper        | DTO-Entity mapping           |
| Spring Security    | Password encoding only       |
| JUnit + Mockito    | Unit Testing                 |

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Java 17+
- Maven 3+
- PostgreSQL instance (e.g., [Avien Online](https://avien.cloud))

### 📁 Clone the Repository

```bash
git clone https://github.com/your-username/pet-store-api.git
cd pet-store-api
```

### ⚙️ Configure application.properties

Edit the `application.properties` file to configure your PostgreSQL connection:

```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DB_NAME>
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 🏁 Run the Application

To run the application, execute:

```bash
mvn spring-boot:run
```

The app will start on: [http://localhost:8080](http://localhost:8080)

---

## 📬 API Endpoints

### 🔐 Customer

| Method | Endpoint          | Description                |
|--------|-------------------|----------------------------|
| POST   | /customers         | Register a new customer    |
| GET    | /customers         | List all customers         |
| DELETE | /customers/{id}    | Delete a customer          |

#### ✅ Example: Register Customer

```json
POST /customers
{
  "customerName": "Pranav C",
  "customerEmail": "pranav@example.com",
  "customerPassword": "secret",
  "customerAddress": "123 Main St",
  "customerPhone": "9876543210"
}
```

### 🐶 Pet

| Method | Endpoint        | Description           |
|--------|-----------------|-----------------------|
| POST   | /pets           | Add a new pet         |
| GET    | /pets           | List all pets         |
| GET    | /pets/{id}      | Get a pet by ID       |
| DELETE | /pets/{id}      | Delete a pet          |

### 🧾 Orders

| Method | Endpoint          | Description                |
|--------|-------------------|----------------------------|
| POST   | /orders           | Create a new order         |
| PUT    | /orders/{id}      | Mark order as returned     |
| GET    | /orders           | Get all orders             |
| GET    | /orders/{id}      | Get order by ID            |

#### ✅ Example: Create Order

```json
POST /orders
{
  "customerId": 1,
  "petId": 3
}
```

---

## 🧪 Running Tests

To run the unit tests for service layer using JUnit and Mockito, execute:

```bash
mvn test
```

---

## 📁 Project Structure

```text
com.example.petstore
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── exception/
├── config/
└── PetStoreApplication.java
```

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## ✍️ Author

Your Name

---

### Additional Sections (Optional)

If you need to add **Swagger UI setup**, **Docker support**, or **CI/CD badge sections**, feel free to let me know, and I can generate those sections too!
