# Banking Application Modernization: Java JSP/Servlets to Spring Boot

### <em>Video Walkthrough: </em>https://youtu.be/wd9cKoyIxMw
<a href="https://youtu.be/wd9cKoyIxMw">
<img width="3446" height="1916" alt="banking_modernization_screenshot" src="https://github.com/user-attachments/assets/f004cea6-c020-4482-bdf5-e82e906c747b"></a>

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)


**Incremental modernization of a legacy Java JSP/Servlets banking application to Spring Boot 3.x**

## Overview

A legacy banking application modernization project that demonstrates working with unfamiliar codebases, configuration troubleshooting, and rebuilding enterprise systems with modern architecture. Starting from an abandoned GitHub repository using JSP/Servlets, this project involves deploying legacy code to AWS EC2, implementing CI/CD workflows with GitHub Actions and Jenkins, and building a Spring Boot replacement.

## Motivation

Having worked with legacy systems at DoD, this project focuses on working with existing code to demonstrate::
- Codebase comprehension without original developers
- Production deployment and troubleshooting across different environments
- Database migration and resource optimization
- Modern architecture implementation using Spring Boot patterns

This project applies experience from working with legacy systems at DoD.

---

## Technical Journey

### Phase 1: Legacy System Deployment

**Starting Point**: Abandoned GitHub repository with JSP/Servlets, no build tools, manual dependency management

**Setup Requirements**:
- Apache Tomcat 8 installation and configuration
- JDBC drivers and servlet API JAR dependencies
- Shell script creation for compiling 20+ servlet classes
- MySQL database initialization from SQL dump

**Outcome**: Functional banking application with three user roles (Customer, Banker, Admin) running locally

**Issue Encountered**:
```
Problem: BankRate.jsp failed with FileNotFoundException
Cause: Hard-coded Windows file paths (C:/tomcat/apache-tomcat-7.0.34/...)
instead of platform-independent resource loading

Resolution: Replaced hard-coded paths with application.getRealPath("/")
for cross-platform file access

Modernization: Spring Boot uses ResourceLoader and classpath resources
(@Value("classpath:BankRate.txt")) eliminating hard-coded paths
```

### Phase 2: AWS Production Deployment

Deployment to EC2 t3.micro instance revealed several environment-specific issues:

#### Database Case Sensitivity Issue
```
Environment Difference:
- macOS MySQL: Case-insensitive → 'bns' and 'BNS' both work
- Linux MySQL: Case-sensitive → requires exact 'BNS' match

Resolution: Updated MySqlDataStoreUtilities.java connection string
to use uppercase database name for Linux compatibility
```

#### Servlet API Version Conflict
```
Problem: Tomcat 10 uses jakarta.servlet.* packages
         Legacy code uses javax.servlet.* packages
Result: Application deployed successfully but all pages returned 404

Resolution: Converted all javax.servlet imports
to jakarta.servlet and recompiled servlet classes
```

### Phase 3: Database Migration & Optimization

**Challenge**: MySQL consuming 380MB on t3.micro instance

**Solution**:
- Migrated from MySQL to PostgreSQL
- Memory usage reduced from 380MB to 180MB (50% reduction)
- Preserved original MySQL implementation as backup
- Created PostgreSqlDataStoreUtilities.java with enhanced error handling
- Updated all servlets to use PostgreSQL connections
- Converted schema syntax (AUTO_INCREMENT → SERIAL, etc.)

**Business Impact**: Deferred EC2 instance upgrade requirement, resulting in approximately $180-240 annual infrastructure cost savings

### Phase 4: Spring Boot Modernization

Building modern replacement with current enterprise Java standards:

**Architectural Comparison**:
```
LEGACY SERVLET:
- Single servlet handles multiple concerns:
  * Session validation
  * Database queries
  * Business logic
  * HTML generation

MODERN SPRING BOOT:
- Separation of concerns:
  * Controller: HTTP handling
  * Service: Business logic
  * Repository: Database access
  * Entity: Banking domain model classes
  * Template: View rendering
```

**Current Implementation**:
- Spring Security for role-based authentication (Customer/Banker/Admin)
- Role-specific dashboards replacing legacy servlet pages
- Reusable Thymeleaf fragments for consistent UI components
- JPA/Hibernate for database abstraction
- Repository pattern for clean data access layer

---

## Technology Stack

**Legacy System**:
- JSP/Servlets with manual HTTP handling
- Apache Tomcat 10
- PostgreSQL 15 (migrated from MySQL 5.7)
- Direct JDBC for database operations

**Modern System**:
- Spring Boot 3.2
- Spring Security 6 for authentication
- Spring Data JPA for ORM
- Thymeleaf templating engine
- PostgreSQL 15

**Infrastructure**:
- AWS EC2 (t3.micro instance)
- GitHub Actions for automated testing
- Jenkins for controlled production deployments
- Shell scripts for deployment automation

---

## Key Learnings

**Environment Matters**: Code working locally may fail in production due to case sensitivity differences, file path handling, and API version conflicts. Testing across environments is essential.

**Legacy Code Context**: Manual dependency management and JSP servlets were standards of that era, but are not always ideal in modern environments. Understanding historical context helps with problem-solving.

**Database Migration Complexity**: Database schema conversion involves more than just syntax - JDBC driver configuration and SQL dialect differences require careful attention.

**Framework Value Proposition**: Spring Boot eliminates boilerplate code. At the same time, understanding what it does 'under the hood' (from servlet experience) provides deeper comprehension of modern frameworks.

---

## Running the Applications

### Legacy System (PostgreSQL)
```bash
# Database setup
createdb banking_system
psql banking_system < legacy-system/schema_postgresql.sql

# Build and deploy
cd scripts/legacy
./build-legacy-war.sh
./deploy-legacy-manual.sh

# Access application
open http://localhost:8082/banking
```

**Test Credentials**:
- Customer: `Adi` / `yaji`
- Banker: `banker` / `urvi`
- Admin: `admin` / `admin`

### Modern Spring Boot System
```bash
# Start application
cd modern-system
./mvnw spring-boot:run

# Access application
open http://localhost:8080
```

---

## Project Structure

```
banking-modernization/
├── legacy-system/              # Production legacy system
│   ├── BankingSystem1/        # JSP/Servlet application
│   ├── schema_postgresql.sql  # Database schema
│
├── modern-system/             # Spring Boot replacement
│   └── src/main/java/com/banking/
│       ├── controller/        # HTTP request handling
│       ├── service/           # Business logic layer
│       ├── repository/        # Data access layer
│       ├── model/             # JPA entities
|       ├── dto/               # Data Transfer Object 
│       └── config/            # Spring configuration
│
└── scripts/                   # Deployment automation
    ├── legacy/                # Legacy system scripts
    └── modern/                # Modern system scripts
```

---

## Roadmap

**In Progress**:
- Fund transfer functionality implementation
- Customer complaint system with MongoDB
- Transaction history with pagination
- Comprehensive integration test suite

**Future Enhancements**:
- RESTful API layer for mobile applications
- Enhanced security with JWT tokens
- Performance monitoring and logging
- Containerization with Docker

---

**Original Legacy Application**: [Banking-application by ayaji](https://github.com/ayaji/Banking-application)

*This project is for educational and portfolio demonstration purposes.*



