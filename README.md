Payment Gateway Simulator

A console-based Payment Gateway Simulator developed using Java, JDBC, and MySQL.
This project simulates the basic functionality of a digital payment system, including user registration, authentication, wallet balance management, and peer-to-peer transactions.

---

Features

- User Registration
- User Login Authentication
- Add Money to Wallet
- Send Money to Another User
- Transaction History Tracking
- Database Integration with MySQL

---

Technologies Used

- Java
- JDBC (Java Database Connectivity)
- MySQL
- IntelliJ IDEA

---

Project Structure

payment-gateway-simulator
│
├── src
│   ├── dao
│   │   ├── UserDAO.java
│   │   └── TransactionDAO.java
│   │
│   ├── service
│   │   └── PaymentService.java
│   │
│   ├── util
│   │   └── DBConnection.java
│   │
│   └── Main.java
│
├── database.sql
└── README.md

---

Database Setup

1. Install MySQL.
2. Open MySQL Workbench.
3. Run the "database.sql" file included in the project.
4. This will create the required database and tables.

---

How to Run the Project

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Add the MySQL Connector JAR to the project libraries.
4. Update database credentials in "DBConnection.java".
5. Run "Main.java".

---

Example Menu

1. Register
2. Login
3. Add Money
4. Send Money
5. View Transactions
6. Exit

---

Author

Nikhil Pal

GitHub: https://github.com/NikhilPal1914
