#!/bin/bash

echo "🔧 Fixing PostgreSQL Version Compilation"
echo "========================================"

CLASSES_DIR="/Applications/tomcat8/webapps/BankingSystem1-postgresql-wip/WEB-INF/classes"
cd "$CLASSES_DIR"

echo "1. Compiling base utilities first..."

# Compile Utilities.java (base dependency)
if [ -f "Utilities.java" ]; then
    echo "Compiling Utilities.java..."
    javac -cp "../lib/*:/Applications/tomcat8/lib/*" Utilities.java
    if [ $? -eq 0 ]; then
        echo "✅ Utilities.java compiled successfully"
    else
        echo "❌ Utilities.java compilation failed"
    fi
else
    echo "❌ Utilities.java not found"
fi

# Compile PostgreSQL utilities
echo "Compiling PostgreSqlDataStoreUtilities.java..."
javac -cp "../lib/*:/Applications/tomcat8/lib/*" PostgreSqlDataStoreUtilities.java
if [ $? -eq 0 ]; then
    echo "✅ PostgreSqlDataStoreUtilities.java compiled successfully"
else
    echo "❌ PostgreSqlDataStoreUtilities.java compilation failed"
fi

echo ""
echo "2. Compiling servlets..."

# Compile WelcomeServlet
echo "Compiling WelcomeServlet.java..."
javac -cp "../lib/*:/Applications/tomcat8/lib/*" WelcomeServlet.java
if [ $? -eq 0 ]; then
    echo "✅ WelcomeServlet.java compiled successfully"
else
    echo "❌ WelcomeServlet.java compilation failed"
    echo "Errors:"
    javac -cp "../lib/*:/Applications/tomcat8/lib/*" WelcomeServlet.java 2>&1 | head -10
fi

# Compile ViewTransactions
echo "Compiling ViewTransactions.java..."
javac -cp "../lib/*:/Applications/tomcat8/lib/*" ViewTransactions.java
if [ $? -eq 0 ]; then
    echo "✅ ViewTransactions.java compiled successfully"
else
    echo "❌ ViewTransactions.java compilation failed"
    echo "Errors:"
    javac -cp "../lib/*:/Applications/tomcat8/lib/*" ViewTransactions.java 2>&1 | head -5
fi

echo ""
echo "3. Checking compilation results..."
ls -la *.class | grep -E "(Utilities|PostgreSql|Welcome|ViewTransactions)"

echo ""
echo "✅ Compilation attempt complete!"
echo "If successful, copy this back to main webapps directory"