# semester-work-assignment-tuesday-15-00-17-50-kd
# Virtual Library Project

## Project Overview
The Virtual Library system is designed to facilitate the management of books and user accounts in a digital library. It includes:
- User Roles:
    - Regular Users: Can search, borrow, and return books.
    - Administrators: Can manage books, users, and transactions.
- Application Goals:
    - Streamline library operations through a user-friendly interface.
    - Provide secure and efficient client-server communication.
    - Support scalable and maintainable software architecture.

---

## Technology Stack
The project leverages the following technologies:
1. JavaFX: For building a responsive and interactive user interface.
2. Spring Boot: For backend logic, RESTful API development, and business rule implementation.
3. SQLite: For lightweight and fast relational database management.
4. RESTful APIs: For seamless client-server communication.

---

## Classes and Functions
Key classes and their roles:
1. Book.java
    - Represents a book in the system.
    - Functions:
      public void addBook(String title, String author, int year);
      public void borrowBook(int bookId, int userId);
      public List<Book> listBooks();


2. User.java
    - Represents a user in the system (both regular and admin).
    - Functions:
      public boolean register(String username, String password, String role);
      public User login(String username, String password);


3. Transaction.java
    - Handles borrowing and returning books.
    - Functions:
      public void rentBook(int bookId, int userId);
      public void returnBook(int transactionId);


---

## RESTful API
The backend exposes the following endpoints:

### User Management


### Book Management

### Transaction Management


---

## Database Design
The database consists of three main tables:
1. Users Table
    - Columns: id, username, password, role.
2. Books Table
    - Columns: id, title, author, year, availability.
3. Transactions Table
    - Columns: id, book_id, user_id, transaction_date.

### Visual Representation
+------------+     +-------+      +-------------+
|  Users     |     | Books |      | Transactions |
+------------+     +-------+      +-------------+
| id         |<--->| id    |<---->| id           |
| username   |     | title |      | book_id      |
| password   |     | ...   |      | user_id      |
+------------+     +-------+      +-------------+

---

## User Interface
### Key Screens
1. Login Screen:
    - Allows users to log in securely.
2. Book List Screen:
    - Displays a searchable list of books.
3. Admin Dashboard:
    - Provides features to manage books and users.

### Sample Images


---

## Challenges and Approach
### Challenges:
1. Secure Communication:
    - Risk: User credentials could be exposed.
    - Approach: Use HTTPS for API requests and hash passwords.
2. Preventing SQL Injection:
    - Risk: Malicious SQL queries could compromise the database.
    - Approach: Use prepared statements in database queries.
3. Scalability:
    - Risk: Increasing users or books might slow the system.
    - Approach: Optimize queries and use indexing in the database.

---

## How to Run
### Backend (Spring Boot API)

### Frontend (JavaFX Application)



---


### Frontend (JavaFX Application)

---

