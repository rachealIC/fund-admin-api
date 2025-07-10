

````markdown
# 💼 Fund Administration System

A simple backend system for managing investment **funds and their transactions**, built using **Java + Spring Boot**. This project helps reinforce backend development skills while exploring key domain concepts in fund management.

---

## 🚀 Features

- ✅ Create and manage funds
- ✅ Add transactions (Deposit, Withdrawal, Fee, Gain, Loss)
- ✅ Auto-adjust fund balances
- ✅ Fund summary (Net Profit calculation)
- ✅ Idempotent transaction creation
- ✅ Input validation & custom error handling
- ✅ Clean architecture using DTOs and layered services

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot 3
- Spring Data JPA + Hibernate
- PostgreSQL
- Flyway (DB migrations)
- SLF4J (Logging)
- Maven

---

## 📁 Project Structure

```bash
src/
└── main/
    ├── java/com/racheal/fundadmin/
    │   ├── fundadmin/
    │   │   ├── dto/           # Data Transfer Objects (request/response)
    │   │   ├── models/        # JPA Entities (Fund, Transaction)
    │   │   ├── repository/    # JPA Repository interfaces
    │   │   ├── service/       # Business logic (transaction & fund services)
    │   │   ├── resources/     # Custom exception classes
    │   │   └── web/           # Controllers and GlobalExceptionHandler
    └── resources/
        ├── application.properties
        └── db/migration/      # Flyway SQL migration files
````

---

## 📦 Getting Started

### 1. Clone the project

```bash
git clone https://github.com/your-username/fund-admin.git
cd fund-admin
```

### 2. Set up PostgreSQL

Create a database:

```sql
CREATE DATABASE fundadmin;
```

### 3. Configure `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fundadmin
spring.datasource.username=youruser
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
spring.flyway.schemas=public
```

> Tip: Never commit sensitive configs — use `.env` or profiles and add them to `.gitignore`.

---

## ▶️ Run the app

```bash
./mvnw spring-boot:run
```

---

## 🔀 API Endpoints

| Method | Endpoint            | Description       |
| ------ | ------------------- | ----------------- |
| GET    | `/api/funds`        | Get all funds     |
| POST   | `/api/funds`        | Create a fund     |
| GET    | `/api/fund?name=X`  | Get fund by name  |
| POST   | `/api/transactions` | Add a transaction |

Sample payload for a transaction:

```json
{
  "fund_id": "uuid-here",
  "type": "DEPOSIT",
  "amount": 500.00,
  "idempotencyKey": "unique-key-here"
}
```

---

## 📊 Domain Logic

* A **Fund** represents an investment account with a balance.
* A **Transaction** is linked to a fund and can be:

    * `DEPOSIT`, `WITHDRAWAL`, `FEE`, `INVESTMENT_GAIN`, `INVESTMENT_LOSS`
* **Net profit** is calculated as:

```java
netProfit = deposits + gains - withdrawals - losses - fees
```

* Withdrawal or fees are only processed if the **fund has enough balance**.

---

## 🧠 Error Handling

* Custom exceptions like `ResourceNotFoundException`, `DuplicateRequestException`, etc. are in:

  ```
  com.racheal.fundadmin.fundadmin.resources.exceptions
  ```

* Global exception handling lives in:

  ```
  com.racheal.fundadmin.fundadmin.web.GlobalExceptionHandler
  ```

* Errors return structured responses with HTTP status codes like:

    * `400 Bad Request` for validation
    * `404 Not Found` for missing resources
    * `409 Conflict` for duplicates (idempotency)

---

## 🧪 Example Response

```json
{
  "message": "Transaction created",
  "data": {
    "transaction_id": "uuid...",
    "type": "DEPOSIT",
    "amount": 1000.00,
    "fund_id": "uuid...",
    "created_at": "2025-07-10T14:00:00"
  }
}
```

---

## 🧾 Future Improvements

* [ ] Add Swagger/OpenAPI for API docs
* [ ] Add Spring Security for auth
* [ ] Add integration and unit tests
* [ ] Add Docker support
* [ ] Export reports (PDF/CSV)

---

## 💡 Useful Commands

### Format SQL migrations

```bash
V1__Create_funds_table.sql
V2__Create_transactions_table.sql
```

### Sample Git ignore setup:

```gitignore
/target/
*.log
*.iml
.vscode/
.env
application*.properties
```

---

## 👩🏽‍💻 Author

**Racheal Kuranchie**
Backend Developer & Cloud Enthusiast

---

## 📄 License

This project is licensed under the MIT License.

````



