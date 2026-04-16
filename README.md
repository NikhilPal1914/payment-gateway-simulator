# Payment Gateway Simulator

A console-based payment gateway simulator built with Java, JDBC, and MySQL.

## Features

- User registration and login
- Wallet balance management
- Peer-to-peer money transfers
- Transaction history tracking
- Password hashing for stored credentials
- Transaction-safe balance updates

## Project Structure

```text
payment-gateway-simulator/
|-- src/
|   |-- dao/
|   |   |-- TransactionDAO.java
|   |   `-- UserDAO.java
|   |-- Service/
|   |   `-- PaymentService.java
|   |-- util/
|   |   |-- DBConnection.java
|   |   `-- PasswordUtil.java
|   `-- Main.java
|-- database.sql
|-- db.properties.example
`-- README.md
```

## Prerequisites

- Java 8 or higher
- MySQL
- MySQL Connector/J added to the project classpath

## Database Setup

1. Create the schema by running [database.sql](./database.sql).
2. Create a local `db.properties` file in the project root using [db.properties.example](./db.properties.example) as the template.
3. Update the database credentials in `db.properties`.

You can also use environment variables instead of `db.properties`:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

## Running the Project

1. Open the project in IntelliJ IDEA, or compile the source files with `javac`.
2. Add MySQL Connector/J to the classpath.
3. Run [Main.java](./src/Main.java).

## Notes

- The repository ignores local IDE files and `db.properties`, so machine-specific settings do not need to be committed.
- Existing plain-text passwords in older databases are upgraded to hashed passwords the next time that user logs in successfully.

## Author

Nikhil Pal

GitHub: <https://github.com/NikhilPal1914>
