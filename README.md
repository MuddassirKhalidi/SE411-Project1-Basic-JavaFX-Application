# Team Manager Application

A simple JavaFX application for managing teams with basic CRUD operations.

## Features

- Add, edit, and delete teams
- Display teams in a tabular format
- File logging for all operations
- Menu-based navigation
- About window with development team information

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Team Data

Each team consists of:
- Team ID
- Team Name
- Number of Members

## Building and Running

### Build the project:
```bash
mvn clean compile
```

### Run the application:
```bash
mvn javafx:run
```

### Create a JAR file:
```bash
mvn clean package
```

## Development Team

- Muddassir Khalidi, 222110775
- Abdulrahman Hamza, <student ID>
- Fahad Turki, <student ID>
- Assem Alhanash, <student ID>

## Logging

The application creates a log file named `application.log` in the root directory. It logs:
- Errors
- Warnings
- Info messages (actions like add, delete, update)

