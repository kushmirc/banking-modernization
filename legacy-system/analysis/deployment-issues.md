# Legacy Deployment Issues & Fixes

## Issue 1: Servlet Mapping Conflicts
The application has conflicting servlet mappings between web.xml and @WebServlet annotations:
- AboutUs servlet: Both defined in web.xml and has @WebServlet("/aboutus") 
- Login servlet: Both "customerlogin" and "Login" mapped to /customerlogin

### Root Cause
This app was likely developed on Windows (case-insensitive) and has issues on Mac/Linux.
Mix of old-style web.xml mappings and newer annotation-based mappings.

### Fix Applied
Added metadata-complete="true" to web.xml to disable annotation scanning.
This forces Tomcat to only use web.xml mappings, ignoring @WebServlet annotations.

### Files Modified
1. /Applications/tomcat8/webapps/BankingSystem1/WEB-INF/web.xml
   - Removed AboutUs servlet mapping
   - Added metadata-complete="true" 

### Removed Files
- AboutUs.java and AboutUs.class moved to legacy-system/temp-removed/

## Issue 2: Database Case Sensitivity on EC2

### Problem Description
Login functionality that worked perfectly on local Mac environment failed on EC2 Ubuntu deployment.

### Symptoms
- Login attempts failed silently (no error shown to user)
- Application pages loaded correctly
- Database was accessible via command line

### Root Cause Analysis
1. **Environment Difference**: 
   - Mac/Windows MySQL: Case-insensitive database names by default
   - Linux MySQL: Case-sensitive database names when `lower_case_table_names=0`

2. **Code Issue**:
   ```java
   // MySqlDataStoreUtilities.java used lowercase
   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bns","root","Passw0rd!");
   ```
   
3. **Database Created As**: `BNS` (uppercase)

### Debugging Process
1. **Initial Checks**:
   - Verified MySQL running: `sudo systemctl status mysql` ✓
   - Verified database exists: `SHOW DATABASES;` showed `BNS` ✓
   - Verified user data exists: `SELECT * FROM BNS.customer;` ✓

2. **Added Debug Logging**:
   ```java
   // In Login.java doPost method
   System.out.println("Login.doPost called - username: " + s1 + ", usertype: " + s3);
   
   // In MySqlDataStoreUtilities.selectUser method
   System.out.println("MySqlDataStoreUtilities.selectUser called - user: " + username + ", type: " + usertype);
   System.out.println("Database connection: " + message);
   ```

3. **Key Discovery in Logs**:
   ```
   [2025-07-22 00:48:36] Database connection: unsuccessful
   [2025-07-22 00:48:37] Exception: Cannot invoke "java.sql.Connection.prepareStatement" because "MySqlDataStoreUtilities.conn" is null
   ```

### Solution Implemented
1. Updated MySqlDataStoreUtilities.java:
   ```java
   // Changed to uppercase to match actual database name
   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BNS","root","Passw0rd!");
   ```

2. Recompiled on EC2:
   ```bash
   sudo javac -cp "/var/lib/tomcat10/webapps/banking/WEB-INF/lib/*:/usr/share/tomcat10/lib/*" \
     /var/lib/tomcat10/webapps/banking/WEB-INF/classes/MySqlDataStoreUtilities.java
   ```

3. Restarted Tomcat:
   ```bash
   sudo systemctl restart tomcat10
   ```

### Lessons for Modernization
1. **Configuration Management**: Database names should be externalized to properties files
2. **Environment Parity**: Use Docker to ensure consistent environments
3. **Better Error Handling**: Connection failures should show meaningful error messages
4. **Naming Standards**: Use lowercase for all database objects to avoid OS-specific issues
