# Rajarata Digital Banking

Project scaffold organized by layers: models, services, security, utils, exceptions, interfaces, and data.

## Building the Project

### Prerequisites
- Java 17 or later
- Apache Maven 3.6+

### Build JAR
```bash
mvn clean package
```

### Build Windows Executable (.exe)
The project is configured with the [Launch4j](http://launch4j.sourceforge.net/) Maven plugin to automatically generate a Windows `.exe` file during the `package` phase.

```bash
mvn clean package
```

After a successful build, you will find:
- **JAR file**: `target/rajarata-digital-banking-1.0-SNAPSHOT.jar`
- **Windows executable**: `target/rajarata-digital-banking.exe`

### Running
- **JAR**: `java -jar target/rajarata-digital-banking-1.0-SNAPSHOT.jar`
- **EXE** (Windows): Double-click `rajarata-digital-banking.exe` or run it from the command line. Requires Java 17+ installed on the system.
