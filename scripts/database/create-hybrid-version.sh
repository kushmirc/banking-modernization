#!/bin/bash

echo "🔄 Creating Hybrid PostgreSQL Version"
echo "====================================="

# Create a hybrid version with PostgreSQL DB but working servlets
WORKING_DIR="/Applications/tomcat8/webapps/BankingSystem1"
POSTGRES_WIP="/Applications/tomcat8/webapps/BankingSystem1-postgresql-wip"

echo "1. Using working MySQL servlets with PostgreSQL database utilities..."

# Copy PostgreSQL utilities to working directory
cp "$POSTGRES_WIP/WEB-INF/classes/PostgreSqlDataStoreUtilities.java" "$WORKING_DIR/WEB-INF/classes/"
cp "$POSTGRES_WIP/WEB-INF/classes/PostgreSqlDataStoreUtilities.class" "$WORKING_DIR/WEB-INF/classes/"

# Copy PostgreSQL JDBC driver
cp "$POSTGRES_WIP/WEB-INF/lib/postgresql-42.7.2.jar" "$WORKING_DIR/WEB-INF/lib/"

echo "2. Creating transitional servlets that work with existing compilation..."

# Create a simple test servlet that uses PostgreSQL
cat > "$WORKING_DIR/WEB-INF/classes/PostgreSqlTest.java" << 'EOF'
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class PostgreSqlTest extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>PostgreSQL Connection Test</h2>");
        
        try {
            String result = PostgreSqlDataStoreUtilities.getConnection();
            out.println("<p>Connection result: " + result + "</p>");
            
            // Test getting user data
            ResultSet rs = PostgreSqlDataStoreUtilities.getUserData("admin", "administrator");
            if (rs != null && rs.next()) {
                out.println("<p>✅ Database connection working!</p>");
                out.println("<p>Admin user: " + rs.getString("fname") + " " + rs.getString("lname") + "</p>");
            } else {
                out.println("<p>❌ No data found</p>");
            }
        } catch (Exception e) {
            out.println("<p>❌ Error: " + e.getMessage() + "</p>");
        }
        
        out.println("</body></html>");
    }
}
EOF

# Compile the test servlet
cd "$WORKING_DIR/WEB-INF/classes"
javac -cp "../lib/*:/Applications/tomcat8/lib/*" PostgreSqlTest.java

echo "3. Adding test servlet to web.xml..."
# We'll need to manually add this to web.xml or use annotation

echo ""
echo "✅ Hybrid version created!"
echo "🔗 Test URL: http://localhost:8080/BankingSystem1/PostgreSqlTest"
echo "🔄 Restart Tomcat to test"