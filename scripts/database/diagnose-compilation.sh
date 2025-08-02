#!/bin/bash

echo "🔍 Diagnosing Compilation Issues"
echo "==============================="

cd /Applications/tomcat8/webapps/BankingSystem1/WEB-INF/classes

echo "1. Checking if Utilities.class exists..."
if [ -f "Utilities.class" ]; then
    echo "✅ Utilities.class found"
else
    echo "❌ Utilities.class missing - need to compile Utilities.java"
fi

echo ""
echo "2. Checking if PostgreSqlDataStoreUtilities.class exists..."
if [ -f "PostgreSqlDataStoreUtilities.class" ]; then
    echo "✅ PostgreSqlDataStoreUtilities.class found"
else
    echo "❌ PostgreSqlDataStoreUtilities.class missing"
fi

echo ""
echo "3. Checking all .java files..."
ls -la *.java | grep -E "(Utilities|PostgreSql|Welcome|ViewTransactions)"

echo ""
echo "4. Checking all .class files..."
ls -la *.class | grep -E "(Utilities|PostgreSql|Welcome|ViewTransactions)"

echo ""
echo "5. Checking for servlet API conflicts..."
grep -r "javax.servlet" *.java | head -3
echo ""
grep -r "jakarta.servlet" *.java | head -3

echo ""
echo "🔧 Recommended fixes:"
echo "1. Compile Utilities.java if it exists"
echo "2. Fix servlet API imports (javax vs jakarta)"
echo "3. Recompile all dependent classes in correct order"