# Legacy Deployment Issues & Fixes

## Issue 1: Servlet Mapping Conflicts
The application has conflicting servlet mappings between web.xml and @WebServlet annotations:
- AboutUs servlet: Both defined in web.xml and has @WebServlet("/aboutus") 
- Login servlet: Both "customerlogin" and "Login" mapped to /customerlogin

## Root Cause
This app was likely developed on Windows (case-insensitive) and has issues on Mac/Linux.
Mix of old-style web.xml mappings and newer annotation-based mappings.

## Fix Applied
Added metadata-complete="true" to web.xml to disable annotation scanning.
This forces Tomcat to only use web.xml mappings, ignoring @WebServlet annotations.

## Files Modified
1. /Applications/tomcat8/webapps/BankingSystem1/WEB-INF/web.xml
   - Removed AboutUs servlet mapping
   - Added metadata-complete="true" 

## Removed Files
- AboutUs.java and AboutUs.class moved to legacy-system/temp-removed/
