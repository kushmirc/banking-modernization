# Lessons Learned

## Database Case Sensitivity Issue (July 2025)

### The Problem
Login functionality worked perfectly on local development environment but failed on EC2 deployment with no obvious error messages in the UI.

### Root Cause
MySQL database name case sensitivity differences between operating systems:
- **Local Environment (Mac/Windows)**: MySQL treats database names as case-insensitive by default
- **EC2 Environment (Ubuntu Linux)**: MySQL treats database names as case-sensitive when `lower_case_table_names=0`

The application code used lowercase `bns` while the database was created as uppercase `BNS`:
```java
// In MySqlDataStoreUtilities.java
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bns","root","Passw0rd!");
```

### Discovery Process
1. Initial assumption was database connectivity, but MySQL was running and accessible
2. Added debug logging to servlet classes to trace execution flow
3. Discovered "Database connection: unsuccessful" in logs
4. Found NullPointerException due to failed database connection
5. Identified case mismatch between code (`bns`) and actual database (`BNS`)

### Solution
Changed the connection string to use uppercase database name:
```java
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BNS","root","Passw0rd!");
```

### Key Takeaways
1. **Environment Parity**: Always verify database configurations match exactly between environments
2. **Case Sensitivity**: Be aware of OS-specific differences, especially with Linux deployments
3. **Debug Logging**: Adding systematic debug statements is crucial for troubleshooting
4. **MySQL Configuration**: Check `lower_case_table_names` variable when debugging database issues

### Prevention for Future
- Use consistent naming conventions (preferably lowercase) for database names
- Document environment-specific configurations
- Include environment verification in deployment procedures
- Add connection testing as part of deployment validation

## Servlet Naming Confusion

### The Problem
The legacy application has confusing and counterintuitive servlet mappings that made debugging difficult.

### What We Found
```xml
<!-- /customerlogin URL is handled by Login class -->
<servlet>
    <servlet-name>customerlogin</servlet-name>
    <servlet-class>Login</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>customerlogin</servlet-name>
    <url-pattern>/customerlogin</url-pattern>
</servlet-mapping>

<!-- /login URL is handled by CustomerLogin class -->
<servlet>
    <servlet-name>CustomerLogin</servlet-name>
    <servlet-class>CustomerLogin</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>CustomerLogin</servlet-name>
    <url-pattern>/login</url-pattern>
</servlet-mapping>
```

### Issues Identified
1. URL patterns don't match their handler class names (backwards/confusing)
2. Inconsistent naming conventions (lowercase vs CamelCase)
3. Two different servlets handling similar functionality
4. Makes debugging and maintenance difficult

### Modernization Recommendations
- Use consistent naming: servlet name = class name
- URL patterns should intuitively match their purpose
- Consolidate duplicate functionality
- Follow REST conventions in Spring Boot migration

## Tomcat Version Migration

### The Challenge
- Local development used Tomcat 8 (javax.servlet.*)
- EC2 deployment used Tomcat 10 (jakarta.servlet.*)

### What Worked Well
The migration script successfully converted all imports from javax to jakarta, showing the value of automated migration tools.

### Learning Points
1. Always document the exact version of application servers used
2. Be aware of major API changes between versions (Java EE → Jakarta EE)
3. Test deployments on matching server versions when possible
