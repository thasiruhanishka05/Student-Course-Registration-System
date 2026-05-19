# 🛡️ Admin & Lecturer Management Module
### Student Course Registration System — EduPortal

> This module covers the **Admin Management** and **Lecturer Management** features of the EduPortal system, built using **Java 21**, **Spring Boot 4.0.5**, and **Thymeleaf** with flat-file (`.txt`) data storage.

---

## 📋 Table of Contents

- [My Contribution](#-my-contribution)
- [Features](#-features)
- [OOP Concepts Applied](#-oop-concepts-applied)
- [Module File Structure](#-module-file-structure)
- [How It Works — Layer by Layer](#-how-it-works--layer-by-layer)
- [Data Storage Format](#-data-storage-format)
- [API Endpoints](#-api-endpoints)
- [UI Overview](#-ui-overview)
- [Authentication & Security](#-authentication--security)

---

## 👨‍💻 My Contribution

My responsibility in this group project was to design and implement **two complete modules**:

| Module | Description |
|---|---|
| **Admin Management** | Full CRUD for system administrators, including login logic and access level control |
| **Lecturer Management** | Full CRUD for lecturers, with department & specialization management and role-based UI |

I also implemented the **Login System**, **Session Management**, **AuthInterceptor**, **Dashboard**, and **Profile Update** — which are shared features that all other modules depend on.

---

## ✨ Features

### 🔐 Login & Authentication System
- Single login page handles all three roles: **Admin**, **Student**, **Lecturer**
- Email + password verification checked against file storage
- On success, session stores `userRole`, `userName`, and `userId`
- On failure, displays an error message on the login page
- Logout invalidates the session and redirects to login
- **Back button protection** — cached pages are blocked after logout using cache-control headers

### 👨‍💼 Admin Management
- View a list of all admins in a data table
- **Add** a new admin with: Name, Email, Phone, Address, Password, Access Level (1–5)
- **Edit** an existing admin — password field is optional (keeps old password if blank)
- **Delete** an admin (last admin cannot be deleted — protected by service logic)
- **Search** admins by name (case-insensitive)
- Admin IDs are **auto-generated** in the format `A001`, `A002`, etc.
- Duplicate email check before saving

### 👨‍🏫 Lecturer Management
- View a list of all lecturers with ID, name, email, phone, department, and specialization
- **Add** a new lecturer with full details — department and specialization use linked dropdowns
- **Edit** an existing lecturer — password field is optional
- **Delete** a lecturer
- **Search** lecturers by name or by department
- Lecturer IDs are **auto-generated** in the format `L001`, `L002`, etc.
- Duplicate email check before saving
- **Role-based UI**: Add, Edit, Delete buttons are hidden from Lecturer view — visible only to Admins
- Departments supported: Computing, Engineering, Business, Human Science, Health Science, Law, Education, Arts & Design
- Each department has mapped specializations loaded dynamically via JavaScript

### 🖥️ Dashboard (Admin View)
- Displays live summary statistics:
  - Total Students, Lecturers, Courses, Registrations
  - Pending Registrations count
  - Pending Payments count
  - Total Feedbacks count

### 👤 Profile Management
- Any logged-in user (Admin/Student/Lecturer) can view and update their own profile
- Password update is optional — blank leaves current password unchanged
- Admin profile includes access level; Lecturer profile includes department & specialization

---

## 🧠 OOP Concepts Applied

| Concept | Where I Used It |
|---|---|
| **Abstraction** | `Person` is an **abstract class** with the abstract method `getDetails()`. Neither `Admin` nor `Lecturer` can be used without implementing it. |
| **Inheritance** | `Admin extends Person` and `Lecturer extends Person` — both inherit `name`, `email`, `phone`, `address`, `password`, and `role` from the parent class. |
| **Encapsulation** | All fields in `Admin` and `Lecturer` are `private`. Only relevant setters are exposed — for example, `Admin` only has `setAccessLevel()`, not setters for immutable fields like `adminId`. |
| **Polymorphism** | `getDetails()` is overridden differently in `Admin` (prints Admin ID and access level) and `Lecturer` (prints department and specialization). |
| **Composition** | The `Person` class holds a `Role` enum — combining a role object inside a person rather than using a plain string. |

### Class Hierarchy Diagram

```
Person  (abstract)
├── Admin
│     fields: adminId, accessLevel
│     methods: manageUsers(), getDetails()
│
└── Lecturer
      fields: lecturerId, department, specialization
      methods: updateProfile(), getDetails()
```

---

## 📁 Module File Structure

```
src/main/java/com/example/Student_Course_Registration_System/
│
├── model/
│   ├── Person.java              ← Abstract base class (Abstraction + Encapsulation)
│   ├── Admin.java               ← Extends Person (Inheritance + Polymorphism)
│   └── Lecturer.java            ← Extends Person (Inheritance + Polymorphism)
│
├── enums/
│   └── Role.java                ← STUDENT, LECTURER, ADMIN
│
├── repository/
│   ├── AdminRepository.java     ← File I/O: save, findAll, findById, update, delete
│   └── LecturerRepository.java  ← File I/O: save, findAll, findById, update, delete
│
├── service/
│   ├── AdminService.java        ← Business logic: login, duplicate check, last-admin guard
│   └── LecturerService.java     ← Business logic: add, update, delete, search by name/dept
│
├── controller/
│   ├── AdminController.java     ← Handles login, logout, dashboard, profile, admin CRUD
│   └── LecturerController.java  ← Handles lecturer CRUD and search
│
├── interceptor/
│   └── AuthInterceptor.java     ← Blocks unauthenticated access to all protected routes
│
└── config/
    └── WebConfig.java           ← Registers the AuthInterceptor with Spring MVC

src/main/resources/
├── data/
│   ├── admins.txt               ← Admin records (comma-separated)
│   └── lecturers.txt            ← Lecturer records (comma-separated)
│
└── templates/
    ├── login.html               ← Login page (shared)
    ├── dashboard.html           ← Role-aware dashboard
    ├── admin.html               ← Admin management page
    ├── lecturer.html            ← Lecturer management page
    └── profile.html             ← Profile view & update page
```

---

## ⚙️ How It Works — Layer by Layer

### 1. Model Layer — `Admin.java` & `Lecturer.java`

Both extend the abstract `Person` class. The `Person` class defines shared attributes and enforces that every subclass implement `getDetails()`.

```java
// Person.java (abstract)
public abstract class Person {
    protected String name, email, phone, address, password;
    protected Role role;
    public abstract void getDetails();  // Must be implemented by subclasses
}

// Admin.java
public class Admin extends Person {
    private String adminId;
    private int accessLevel;  // Range: 1 to 5

    @Override
    public void getDetails() {
        System.out.println("Admin ID: " + adminId + " | Level: " + accessLevel);
    }
}

// Lecturer.java
public class Lecturer extends Person {
    private String lecturerId;
    private String department;
    private String specialization;

    @Override
    public void getDetails() {
        System.out.println("Lecturer: " + name + " | Dept: " + department);
    }
}
```

### 2. Repository Layer — File-Based Storage

No database is used. Data is stored in plain `.txt` files. Each repository handles reading, writing, updating, and deleting records using Java's `BufferedReader` and `BufferedWriter`.

**ID Auto-Generation Logic:**
- Reads all existing records and finds the highest numeric part of existing IDs
- Increments by 1 and formats with zero-padding: `A001`, `A002` ... `A100`

### 3. Service Layer — Business Logic

The service layer enforces rules before any data is saved:

**AdminService rules:**
- Cannot add an admin if the email already exists
- Cannot delete the last remaining admin (system protection)
- Login checks both email and password match

**LecturerService rules:**
- Cannot add a lecturer if the email already exists
- Supports two search methods: `searchByName()` and `searchByDepartment()`

### 4. Controller Layer — HTTP Endpoints

The `AdminController` handles: login, logout, dashboard, profile update, and all admin CRUD.

The `LecturerController` handles: all lecturer CRUD operations and search.

Both use Spring MVC `@GetMapping` / `@PostMapping`, pass data to Thymeleaf templates via `Model`, and read session data via `HttpSession`.

---

## 💾 Data Storage Format

### `admins.txt`
```
A001,Admin,admin@gmail.com,0771234568,kandy,admin123,1
```
| Field | Example | Notes |
|---|---|---|
| adminId | `A001` | Auto-generated, sequential |
| name | `Admin` | Full name |
| email | `admin@gmail.com` | Must be unique |
| phone | `0771234568` | Must start with `07`, 10 digits |
| address | `kandy` | Free text |
| password | `admin123` | Plain text |
| accessLevel | `1` | Integer 1–5 |

### `lecturers.txt`
```
L001,sajeer,sajeer@gmail.com,0723445654,colombo,s123,Computing,Artificial Intelligence
```
| Field | Example | Notes |
|---|---|---|
| lecturerId | `L001` | Auto-generated, sequential |
| name | `sajeer` | Full name |
| email | `sajeer@gmail.com` | Must be unique |
| phone | `0723445654` | Must start with `07`, 10 digits |
| address | `colombo` | Free text |
| password | `s123` | Plain text |
| department | `Computing` | From fixed dropdown list |
| specialization | `Artificial Intelligence` | Linked to department |

---

## 🔌 API Endpoints

### Authentication (AdminController)

| Method | URL | Description |
|---|---|---|
| `GET` | `/` | Redirects to `/login` |
| `GET` | `/login` | Show login page |
| `POST` | `/login` | Authenticate user (Admin / Student / Lecturer) |
| `GET` | `/logout` | Invalidate session and redirect to login |
| `GET` | `/dashboard` | Show role-aware dashboard |
| `GET` | `/profile` | View current user's profile |
| `POST` | `/profile/update` | Update current user's profile |

### Admin CRUD (AdminController)

| Method | URL | Description |
|---|---|---|
| `GET` | `/admins` | List all admins |
| `POST` | `/admins/add` | Add a new admin |
| `GET` | `/admins/view/{adminId}` | View a single admin |
| `GET` | `/admins/edit/{adminId}` | Show edit form for an admin |
| `POST` | `/admins/edit/{adminId}` | Submit updated admin data |
| `GET` | `/admins/delete/{adminId}` | Delete an admin |
| `GET` | `/admins/search?name=` | Search admins by name |

### Lecturer CRUD (LecturerController)

| Method | URL | Description |
|---|---|---|
| `GET` | `/lecturers` | List all lecturers |
| `POST` | `/lecturers/add` | Add a new lecturer |
| `POST` | `/lecturers/edit/{lecturerId}` | Update a lecturer |
| `GET` | `/lecturers/delete/{lecturerId}` | Delete a lecturer |
| `GET` | `/lecturers/search?name=` | Search by name |
| `GET` | `/lecturers/search?department=` | Search by department |

---

## 🖥️ UI Overview

The UI is built with **Thymeleaf** + **Tailwind CSS** and supports **dark/light mode toggle** (preference saved in `localStorage`).

| Page | Route | Access |
|---|---|---|
| Login | `/login` | Public |
| Dashboard | `/dashboard` | All roles (content differs by role) |
| Admin Management | `/admins` | Admin only |
| Lecturer Management | `/lecturers` | Admin (full CRUD) / Lecturer (view only) |
| Profile | `/profile` | All roles |

**Key UI features:**
- Glassmorphism-style cards with `backdrop-filter: blur`
- Fade-in animations on page load
- Inline **modal forms** for Add and Edit (no page reload needed)
- Action buttons appear on table row hover
- Delete actions show a confirmation dialog
- Empty state message when no records are found
- Dark / Light theme toggle persisted across sessions

---

## 🔒 Authentication & Security

Authentication is handled by `AuthInterceptor.java` — a Spring MVC `HandlerInterceptor` that runs before every HTTP request.

**How it works:**
1. Allows public access to: `/login`, `/logout`, `/css/**`, `/js/**`, `/error`
2. For all other routes, checks if the session has a valid `userRole` attribute
3. If session is missing or expired → redirects to `/login`
4. Cache-control headers prevent the browser from showing protected pages after logout

```java
// AuthInterceptor.java (simplified)
@Override
public boolean preHandle(HttpServletRequest request, ...) {
    String path = request.getRequestURI();
    if (path.equals("/login") || path.startsWith("/css") ...) return true;

    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("userRole") == null) {
        response.sendRedirect("/login");
        return false;
    }
    return true;
}
```

> ⚠️ **Note:** Passwords are stored as plain text in `.txt` files. This is acceptable for this academic assignment but would need hashing (e.g., BCrypt) in a production system.

---

## 🚀 How to Run

```bash
# Clone the project
git clone https://github.com/your-username/Student-Course-Registration-System.git
cd Student-Course-Registration-System

# Run with Maven Wrapper
./mvnw spring-boot:run
```

Open your browser at `http://localhost:8080`

**Default Admin Login:**
```
Email:    admin@gmail.com
Password: admin123
```

**Sample Lecturer Login:**
```
Email:    sajeer@gmail.com
Password: s123
```

---

