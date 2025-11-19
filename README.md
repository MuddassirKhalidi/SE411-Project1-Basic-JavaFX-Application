# Team Manager Application

A JavaFX application integrated with a Spring Boot REST server for managing teams with basic CRUD operations.

## Features

- Add, edit, and delete teams
- Display teams in a tabular format
- File logging for all operations
- Menu-based navigation
- About window with development team information
- Spring Boot REST API server
- H2 embedded database
- RESTful API endpoints for team management

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Team Data

Each team consists of:
- Team ID
- Team Name
- Number of Members

## Architecture

The application consists of two parts:
1. **Spring Boot Server**: REST API server running on port 8080 with H2 embedded database
2. **JavaFX Client**: Desktop application that communicates with the server via REST API

## Building and Running

### Build the project:
```bash
mvn clean compile
```

### Running the Application

**Important**: The Spring Boot server must be started before the JavaFX client.

#### Step 1: Start the Spring Boot Server
```bash
mvn spring-boot:run
```
Or run the `TeamServerApplication` class directly. The server will start on `http://localhost:8080`

#### Step 2: Start the JavaFX Client
In a separate terminal:
```bash
mvn javafx:run
```

### REST API Endpoints

The server provides the following endpoints:

- `POST /api/teams` - Add a new team
- `GET /api/teams` - Get all teams
- `GET /api/teams/{id}` - Get a team by ID
- `PUT /api/teams/{id}` - Update a team
- `DELETE /api/teams/{id}` - Delete a team

### H2 Database Console

The H2 database console is available at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:teamdb`
- Username: `sa`
- Password: (empty)

### Create a JAR file:
```bash
mvn clean package
```

## Development Team

- Muddassir Khalidi, 222110775
- Abdulrahman Hamza, 221110874
- Fahad Turki, <student ID>
- Assem Alhanash, <student ID>

## Logging

The application creates a log file named `application.log` in the root directory. It logs:
- Errors
- Warnings
- Info messages (actions like add, delete, update)

