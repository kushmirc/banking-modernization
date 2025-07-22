# Legacy System Deployment Notes

## Local Development Environment
- **OS**: macOS
- **Java Version**: 8
- **Application Server**: Tomcat 8 (located at `/Applications/tomcat8`)
- **Database**: MySQL (case-insensitive)
- **Build Method**: Manual compilation with shell scripts

### Starting Local Tomcat
```bash
cd /Applications/tomcat8/bin
./startup.sh

# To view logs
tail -f /Applications/tomcat8/logs/catalina.out
```

## EC2 Production Environment
- **OS**: Ubuntu 24.04 LTS
- **Instance Type**: t3.medium
- **Java Version**: OpenJDK 17
- **Application Server**: Tomcat 10
- **Database**: MySQL 8.0.42
- **Access**: Port 8082

### Key Configuration Details
- **Tomcat Location**: `/var/lib/tomcat10/`
- **Webapps Directory**: `/var/lib/tomcat10/webapps/`
- **Logs**: `/var/log/tomcat10/catalina.out`
- **MySQL Database Name**: `BNS` (uppercase - case sensitive!)

## Critical Deployment Issues Resolved

### 1. Database Case Sensitivity
**Issue**: Application failed to connect to database on EC2
**Root Cause**: MySQL on Linux is case-sensitive for database names
**Solution**: Updated `MySqlDataStoreUtilities.java` to use uppercase `BNS`:
```java
// Changed from:
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bns","root","Passw0rd!");
// To:
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BNS","root","Passw0rd!");
```

### 2. Servlet API Migration
**Issue**: Tomcat 10 uses Jakarta EE instead of Java EE
**Solution**: Ran migration script to convert all `javax.*` imports to `jakarta.*`

### 3. File Permissions
**Issue**: Cannot access deployed files without sudo
**Solution**: All commands require sudo as files are owned by tomcat:tomcat

## Deployment Commands

### Deploying Updates
```bash
# Copy WAR file to EC2
scp -i your-key.pem banking.war ubuntu@your-ec2-ip:/tmp/

# On EC2
sudo cp /tmp/banking.war /var/lib/tomcat10/webapps/
sudo systemctl restart tomcat10
```

### Monitoring
```bash
# Watch logs
sudo tail -f /var/log/tomcat10/catalina.out

# Check Tomcat status
sudo systemctl status tomcat10

# Check database connection
mysql -u root -pPassw0rd! -e "USE BNS; SHOW TABLES;"
```

### Debugging Login Issues
```bash
# Add debug statements to Java files
sudo nano /var/lib/tomcat10/webapps/banking/WEB-INF/classes/[ClassName].java

# Recompile
sudo javac -cp "/var/lib/tomcat10/webapps/banking/WEB-INF/lib/*:/usr/share/tomcat10/lib/*" \
  /var/lib/tomcat10/webapps/banking/WEB-INF/classes/[ClassName].java

# Restart and watch logs
sudo systemctl restart tomcat10
sudo tail -f /var/log/tomcat10/catalina.out
```

## Working Login Credentials
- **Customer**: Username: `Adi`, Password: `yaji`
- **Administrator**: Username: `admin`, Password: `admin`
- **Banker**: Username: `banker`, Password: `urvi`

## Environment Differences Summary
| Aspect | Local | EC2 |
|--------|-------|-----|
| OS | macOS | Ubuntu 24.04 |
| Tomcat | 8 | 10 |
| Servlet API | javax.* | jakarta.* |
| MySQL case sensitivity | No | Yes |
| Database name | bns (works) | BNS (required) |
| Port | 8080 | 8082 |

## Next Steps for Modernization
1. Standardize database naming (use lowercase)
2. Containerize with Docker for environment parity
3. Use Spring Boot to avoid servlet API version issues
4. Implement proper configuration management
5. Add health check endpoints
