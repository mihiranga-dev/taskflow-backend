---
title: Taskflow Backend
emoji: 🚀
colorFrom: blue
colorTo: purple
sdk: docker
pinned: false
app_port: 7860
---

# 🛡️ TaskMaster - Backend API

<!-- BADGES -->

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

<!-- BADGES END -->

> A secure, high-performance REST API built with Spring Boot 3, managing authentication and data persistence for the TaskMaster application.

### **🔴 Live Demo:**

👉 [TaskMaster](https://task-master-lite.netlify.app)

---

## 📖 Overview

This repository houses the server-side logic for **TaskMaster**. It is a **RESTful API** designed to follow modern microservices principles. It handles user identity, data validation, and secure database transactions.

Unlike simple CRUD apps, this backend implements **Stateless Authentication** using JWTs and enforces strict **User Data Isolation** (users can never access or modify another user's tasks).

---

## 🛠️ Tech Stack & Architecture

- **Framework:** Spring Boot 3 (Java 17)
- **Security:** Spring Security + JWT (JSON Web Tokens)
- **Database:**
  - _Dev:_ H2 In-Memory Database
  - _Prod:_ PostgreSQL (hosted on Neon.tech)
- **ORM:** Hibernate / Spring Data JPA
- **Deployment:** Dockerized container running on Hugging Face Spaces

---

## 🔐 Key Features

- **JWT Authentication:** Custom `JwtRequestFilter` intercepts requests to validate Bearer tokens.
- **Stateless Architecture:** No server-side sessions, making the API easy to scale.
- **Global CORS Configuration:** Configured to allow secure communication with the Frontend.
- **User Isolation:** Service layer logic ensures all database queries are scoped to the authenticated user.
- **Exception Handling:** Graceful error messages for invalid requests.

---

## 📡 API Endpoints

### 1. Authentication

| Method | Endpoint             | Description                   | Protected? |
| :----- | :------------------- | :---------------------------- | :--------- |
| `POST` | `/api/auth/register` | Create a new user account     | ❌ No      |
| `POST` | `/api/auth/login`    | Login and receive a JWT Token | ❌ No      |

**Login Request Body:**

```json
{
  "username": "user",
  "password": "userpassword"
}
```

### 2. Tasks

| Method   | Endpoint          | Description                    | Protected? |
| :------- | :---------------- | :----------------------------- | :--------- |
| `GET`    | `/api/tasks`      | Get all tasks for current user | ✅ Yes     |
| `POST`   | `/api/tasks`      | Create a new task              | ✅ Yes     |
| `PUT`    | `/api/tasks/{id}` | Update task status/details     | ✅ Yes     |
| `DELETE` | `/api/tasks/{id}` | Delete a task                  | ✅ Yes     |

**Task Object Structure:**

```json
{
  "title": "Task 1",
  "description": " Task 1 Description",
  "completed": false
}
```

## 🚀 How to Run Locally

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/mihiranga-dev/taskflow-backend.git
    ```

2.  **Configure Database (Optional):**
    The app uses H2 (In-Memory) by default for local development. No setup required!

    - _To use Postgres locally, update `application.properties` with your DB credentials._

3.  **Run the App:**

    ```bash
    mvn spring-boot:run
    ```

    The server will start on `http://localhost:8080`.

4.  **Access Database Console (H2):**
    - **URL:** `http://localhost:8080/h2-console`
    - **JDBC URL:** `jdbc:h2:mem:taskflowdb`
    - **User:** `sa`
    - **Password:** `password`

---

## 🐳 Docker Deployment

The application is containerized using a multi-stage Dockerfile.

**Build Image:**

```bash
docker build -t taskflow-backend .
```

**Run Container:**

```bash
docker run -p 8080:8080 -e DB_URL="your_postgres_url" taskflow-backend
```

## 🔗 Related Repository

This API powers the **TaskFlow Frontend**.
👉 **[View Frontend Repository](https://github.com/mihiranga-dev/taskflow-frontend)**

---

## 👤 Author

**Mihiranga**

- [LinkedIn](https://www.linkedin.com/in/mihiranga-dev/)
- [GitHub](https://github.com/mihiranga-dev/)
