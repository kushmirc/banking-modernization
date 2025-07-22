# EC2 Deployment Troubleshooting Guide

## Quick Reference: Common Issues

### 1. Login Not Working
**Symptoms**: Pages load but login fails silently

**Check these first**:
```bash
# 1. Check database name case
mysql -u root -pPassw0rd! -e "SHOW DATABASES;"
# Look for BNS vs bns

# 2. Check logs for connection errors
sudo tail -f /var/log/tomcat10/catalina.out
# Look for "unsuccessful" or "null" connection

# 3. Verify database has data
mysql -u root -pPassw0rd! -e "SELECT * FROM BNS.customer;"
```

### 2. Cannot Access Application Files
**Issue**: Permission denied when accessing /var/lib/tomcat10/webapps/banking/

**Solution**: Always use sudo
```bash
sudo ls -la /var/lib/tomcat10/webapps/banking/
sudo nano /var/lib/tomcat10/webapps/banking/WEB-INF/classes/SomeFile.java
```

### 3. Compilation Errors
**Issue**: Cannot find symbol errors when compiling

**Solution**: Include all dependencies
```bash
# Find missing class
sudo ls /var/lib/tomcat10/webapps/banking/WEB-INF/classes/ | grep ClassName

# Compile with all required classes
sudo javac -cp "/var/lib/tomcat10/webapps/banking/WEB-INF/lib/*:/usr/share/tomcat10/lib/*" \
  File1.java File2.java File3.java
```

## Step-by-Step Debugging Process

### Step 1: Verify Basic Connectivity
```bash
# Check if Tomcat is running
sudo systemctl status tomcat10

# Check if app is deployed
curl http://localhost:8082/banking/

# Check MySQL
sudo systemctl status mysql
mysql -u root -pPassw0rd! -e "SHOW DATABASES;"
```

### Step 2: Add Debug Logging
Example for login debugging:
```java
// Add to servlet methods
System.out.println("Method called with params: " + param1 + ", " + param2);

// Add to database methods
System.out.println("Connection status: " + (conn != null ? "connected" : "null"));
System.out.println("Query: " + sqlQuery);
System.out.println("Result: " + (resultSet.next() ? "found" : "not found"));
```

### Step 3: Compile and Deploy Changes
```bash
# Edit file
sudo nano /var/lib/tomcat10/webapps/banking/WEB-INF/classes/ClassName.java

# Compile
sudo javac -cp "/var/lib/tomcat10/webapps/banking/WEB-INF/lib/*:/usr/share/tomcat10/lib/*" \
  /var/lib/tomcat10/webapps/banking/WEB-INF/classes/ClassName.java

# Restart
sudo systemctl restart tomcat10

# Watch logs
sudo tail -f /var/log/tomcat10/catalina.out
```

### Step 4: Common Fixes

#### Database Connection Issues
```bash
# Check connection string in code
sudo grep -r "jdbc:mysql" /var/lib/tomcat10/webapps/banking/WEB-INF/classes/

# Common fixes:
# 1. Case sensitivity: bns → BNS
# 2. Add useSSL=false: jdbc:mysql://localhost:3306/BNS?useSSL=false
# 3. Check password quotes in connection string
```

#### Session/Cookie Issues
```bash
# Check for session creation
sudo grep -r "getSession" /var/lib/tomcat10/webapps/banking/WEB-INF/classes/

# Clear browser cookies for the domain
# Try incognito/private browsing mode
```

## Environment Verification Checklist

- [ ] Tomcat version matches expected (10 for Jakarta, 8 for javax)
- [ ] Java version compatible (17 for Tomcat 10)
- [ ] MySQL database name case matches code
- [ ] Database user has proper permissions
- [ ] All JAR dependencies present in WEB-INF/lib
- [ ] No port conflicts (8082 is free)
- [ ] Sufficient disk space and memory

## Useful Commands Reference

```bash
# Logs
sudo journalctl -u tomcat10 -f          # System logs
sudo tail -f /var/log/tomcat10/*        # All Tomcat logs

# Process Management
sudo systemctl restart tomcat10          # Restart
sudo systemctl stop tomcat10             # Stop
sudo systemctl start tomcat10            # Start

# File Operations
sudo -s                                  # Root shell for multiple commands
sudo sh -c 'cd /path && command'         # Run command in specific directory

# Database
mysql -u root -pPassw0rd! BNS            # Connect to database
mysqldump -u root -pPassw0rd! BNS > backup.sql  # Backup

# Network
netstat -tulpn | grep 8082               # Check if port is listening
curl -I http://localhost:8082/banking/   # Test HTTP response
```

## When All Else Fails

1. Check the original working local environment
2. Compare file sizes and dates between environments
3. Enable Tomcat debug logging in /etc/tomcat10/logging.properties
4. Use `strace` to trace system calls if needed
5. Restart the entire EC2 instance (last resort)

Remember: Most issues are environment-specific configuration differences!
