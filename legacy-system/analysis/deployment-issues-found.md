# Legacy System Deployment Issues

## Issue #1: BankRate.jsp Hard-coded Paths (Fixed)

### Problem
- The `BankRate.jsp` page contains hard-coded Windows paths
- Paths assume specific Tomcat installation directory structure
- Causes FileNotFoundException on Mac/Linux systems
- Makes application non-portable across environments

### Hard-coded Paths Found
```java
String txtFilePath ="C:/tomcat/apache-tomcat-7.0.34/webapps/BankingSystem1/BankRate.txt";
```

### Why This Is a Critical Enterprise Problem
1. **Platform Dependency**: Only works on Windows with specific directory structure
2. **Environment Coupling**: Tightly coupled to developer's local setup
3. **Deployment Fragility**: Breaks when moving between environments
4. **Maintenance Burden**: Requires manual path changes for each deployment

### Solution Implemented
- Replace hard-coded absolute paths with relative paths using ServletContext
- Use `application.getRealPath()` to get current deployment directory
- Make paths platform-independent

### Cross-Platform File Path Best Practices
- **Never hard-code path separators** (`/` or `\`)
- Use `File.separator` for platform independence
- Use `File` constructor or `Paths.get()` for subdirectories
- Example: `new File(webAppPath, "data" + File.separator + "rates.txt")`

### Modern Alternative (for Spring Boot migration)
- Use `classpath:` resources
- Externalize configuration with `application.properties`
- Use Spring's `Resource` abstraction
- Store content in database instead of files

### Business Impact
- **Before**: Page crashes on non-Windows systems
- **After**: Works across all platforms and deployment scenarios
- **Learning**: Demonstrates importance of environment-agnostic code
