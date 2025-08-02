#!/bin/bash

echo "📦 Setting up PostgreSQL JDBC Driver"
echo "==================================="

WEBAPP_LIB="/Applications/tomcat8/webapps/BankingSystem1/WEB-INF/lib"

# Check if lib directory exists
if [ ! -d "$WEBAPP_LIB" ]; then
    echo "❌ WEB-INF/lib directory not found: $WEBAPP_LIB"
    exit 1
fi

echo "📁 Current JAR files in WEB-INF/lib:"
ls -la "$WEBAPP_LIB"

# Check if PostgreSQL driver exists
if ls "$WEBAPP_LIB"/postgresql*.jar 1> /dev/null 2>&1; then
    echo "✅ PostgreSQL JDBC driver already present"
else
    echo "❌ PostgreSQL JDBC driver missing"
    echo ""
    echo "🔽 Downloading PostgreSQL JDBC driver..."
    
    # Download PostgreSQL JDBC driver
    curl -o "$WEBAPP_LIB/postgresql-42.7.2.jar" \
         "https://jdbc.postgresql.org/download/postgresql-42.7.2.jar"
    
    if [ $? -eq 0 ]; then
        echo "✅ PostgreSQL JDBC driver downloaded successfully"
    else
        echo "❌ Failed to download JDBC driver"
        echo "💡 Manual download: https://jdbc.postgresql.org/download.html"
        echo "   Save as: $WEBAPP_LIB/postgresql-42.7.2.jar"
        exit 1
    fi
fi

# Remove MySQL driver to avoid conflicts
if ls "$WEBAPP_LIB"/mysql*.jar 1> /dev/null 2>&1; then
    echo "🗑️  Removing MySQL JDBC driver to avoid conflicts..."
    rm "$WEBAPP_LIB"/mysql*.jar
    echo "✅ MySQL driver removed"
fi

echo ""
echo "📦 Final JAR files in WEB-INF/lib:"
ls -la "$WEBAPP_LIB"

echo ""
echo "✅ JDBC driver setup complete!"
echo "🔄 Restart Tomcat to load the new driver"