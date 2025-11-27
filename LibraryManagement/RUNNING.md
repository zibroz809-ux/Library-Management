# Library Management System - Running Guide

## System Requirements
- **Java**: Version 21 or higher
- **Maven**: Version 3.6.0 or higher
- **Operating System**: Windows, macOS, or Linux

## Quick Start

### Option 1: Using Batch Script (Windows Only)
Simply run the provided batch file:
```bash
run.bat
```

This script will:
1. Automatically download all dependencies
2. Compile the project
3. Run the application

### Option 2: Using Maven Command
```bash
mvn clean install
java -jar target/LibraryManagement.jar
```

### Option 3: Direct Java Execution (Advanced)
```bash
mvn dependency:copy-dependencies -DoutputDirectory=target/libs -q
java -cp "target/classes;target/libs/*" com.library.App
```

---

## Build Instructions

### Full Build Process
```bash
mvn clean package
```

This will:
- Compile the source code
- Run tests (if any)
- Create a fat JAR with all dependencies

### Output Files
After building, you'll find:
- `target/LibraryManagement.jar` - Executable JAR file with all dependencies
- `target/classes/` - Compiled Java classes

---

## Troubleshooting

### Problem: "Error: JavaFX runtime components are missing"
**Solution**: Make sure JavaFX 21 libraries are downloaded:
```bash
mvn dependency:copy-dependencies -DoutputDirectory=target/libs
```

### Problem: "Could not find or load main class"
**Solution**: Rebuild the project:
```bash
mvn clean package
```

### Problem: Port already in use
**Solution**: The application uses local file storage (JSON files in `data/` folder), not ports. Check if another instance is running.

### Problem: Build fails with compilation errors
**Solution**: Ensure Java 21 is installed and selected:
```bash
java -version  # Should show Java 21
```

---

## Project Structure

```
LibraryManagement/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/library/
│   │   │       ├── App.java                  # Entry point
│   │   │       ├── controllers/              # FXML controllers
│   │   │       ├── models/                   # Data models
│   │   │       ├── services/                 # Business logic
│   │   │       └── database/                 # JSON database
│   │   └── resources/
│   │       └── views/                        # FXML UI files
│   └── test/                                 # Unit tests
├── data/                                     # JSON data files
├── target/                                   # Build output
├── pom.xml                                   # Maven configuration
└── run.bat                                   # Windows batch script
```

---

## Data Storage
The application uses JSON files for data storage:
- `data/books.json` - Book catalog
- `data/book_copies.json` - Physical copies
- `data/borrowers.json` - Borrower information
- `data/branches.json` - Library branches
- `data/loans.json` - Loan records

These files are created automatically on first run.

---

## Dependencies
Main dependencies:
- **JavaFX 21** - UI framework
- **Gson 2.10.1** - JSON processing
- **Java 21** - Runtime

---

## Development

### To modify the UI
Edit FXML files in `src/main/resources/views/`

### To modify business logic
Edit Java files in `src/main/java/com/library/services/`

### To modify data models
Edit Java files in `src/main/java/com/library/models/`

### To add new features
1. Add controller class in `controllers/`
2. Add FXML view in `resources/views/`
3. Add service class if needed in `services/`

### To rebuild after changes
```bash
mvn clean package
java -jar target/LibraryManagement.jar
```

---

## License
This project is part of PPBO Praktikum (Semester 3)

---

## Contact/Support
For issues, check the error messages or verify:
1. Java version: `java -version`
2. Maven installation: `mvn -version`
3. Dependencies: `mvn dependency:tree`
