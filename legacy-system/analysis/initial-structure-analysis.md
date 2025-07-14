# Legacy Banking System - Initial Structure Analysis

## Discovery: Plain Servlet Application (Not Struts!)

Initial investigation reveals this is a traditional Java Servlet application, not Struts as originally thought.

## Technology Stack
- **Framework**: None - Plain Java Servlets (javax.servlet)
- **Java Version**: Appears to be Java 7/8 era based on servlet 3.0 spec
- **Database**: MySQL (primary) + MongoDB (complaints)
- **Frontend**: JSP pages with Bootstrap CSS and jQuery
- **Build**: Manual compilation with javac (no Maven/Gradle)

## Architecture Pattern
- **Pattern**: Traditional Servlet-based MVC without framework
- **Controllers**: Individual Servlet classes (Login.java, Home.java, etc.)
- **Views**: Mix of JSP files and HTML generated in servlets
- **Model**: Direct JDBC calls in utility classes

## Key Components Identified

### Authentication & Session Management
- `Login.java` - Customer login servlet
- `CustomerLogin.java` - Login page handler
- `LogOut.java` - Session termination
- Manual session management using HttpSession

### Core Banking Operations
- `FTWithin.java` - Funds transfer within bank
- `FTOtherBank.java` - External bank transfers
- `ViewTransactions.java` - Transaction history
- `addtransactions.java` - Transaction recording

### Customer Management (Banker Role)
- `createCustomer.java` - New account creation
- `updateCustomer.java` - Customer info updates  
- `deleteCustomer.java` - Account deletion
- `customerdetails.java` - Customer information view

### Complaint System
- `Complaint.java` - Complaint registration
- `ComplaintBase.java` - Complaint management
- `Complaint_POJO.java` - Complaint data model
- Uses MongoDB for storage

### Data Access Layer
- `MySqlDataStoreUtilities.java` - MySQL database operations
- `MongoDBDataStoreUtilities.java` - MongoDB operations
- Direct JDBC without connection pooling
- No transaction management framework

## Security Concerns
- Passwords likely stored in plain text (based on credentials.txt)
- SQL queries appear to use string concatenation (SQL injection risk)
- No apparent input validation framework
- Session management is manual

## Deployment Requirements
1. Tomcat server (servlet container)
2. MySQL database (schema: bns)
3. MongoDB (database: complaint, collection: complaintStored)
4. Manual JAR dependencies:
   - servlet-api.jar
   - mysql-connector-java-5.1.23-bin.jar
   - mongo-java-driver-3.2.2.jar
   - gson-2.6.2.jar

## Modernization Opportunities
1. **Framework Migration**: Servlets → Spring Boot controllers
2. **Data Access**: JDBC → Spring Data JPA/MongoDB
3. **Security**: Add Spring Security with BCrypt
4. **Build System**: Manual compilation → Maven
5. **Configuration**: Hard-coded → application.properties
6. **Testing**: None visible → Spring Boot Test
7. **API Design**: URL patterns → RESTful endpoints

## Next Steps
1. Deploy and run the application to understand runtime behavior
2. Map each servlet to its business function
3. Identify shared code patterns for extraction
4. Document database schema from dump_bk.sql
5. Create feature-by-feature migration plan
