#!/bin/bash

echo "🔍 Banking System Deployment Diagnostics"
echo "========================================"

echo ""
echo "1. Checking Tomcat status..."
TOMCAT_PID=$(ps aux | grep -i tomcat | grep -v grep | awk '{print $2}' | head -1)
if [ ! -z "$TOMCAT_PID" ]; then
    echo "✅ Tomcat is running (PID: $TOMCAT_PID)"
else
    echo "❌ Tomcat is not running"
fi

echo ""
echo "2. Checking deployed applications..."
if [ -d "/Applications/tomcat8/webapps/BankingSystem1" ]; then
    echo "✅ BankingSystem1 is deployed"
    echo "   Files in WEB-INF/classes:"
    ls -la /Applications/tomcat8/webapps/BankingSystem1/WEB-INF/classes/*.java 2>/dev/null | head -5
else
    echo "❌ BankingSystem1 is not deployed"
fi

echo ""
echo "3. Checking for compilation errors in logs..."
if [ -f "/Applications/tomcat8/logs/catalina.out" ]; then
    echo "Recent errors in catalina.out:"
    tail -50 /Applications/tomcat8/logs/catalina.out | grep -i "error\|exception\|fail" | tail -5
else
    echo "❌ No catalina.out found"
fi

echo ""
echo "4. Checking localhost log..."
if [ -f "/Applications/tomcat8/logs/localhost.$(date +%Y-%m-%d).log" ]; then
    echo "Recent localhost errors:"
    tail -20 "/Applications/tomcat8/logs/localhost.$(date +%Y-%m-%d).log" | grep -i "error\|exception" | tail -3
else
    echo "ℹ️  No localhost log for today"
fi

echo ""
echo "5. Testing basic connectivity..."
echo "Testing http://localhost:8080/BankingSystem1/"
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" http://localhost:8080/BankingSystem1/ 2>/dev/null || echo "❌ Cannot connect to Tomcat"

echo ""
echo "6. Checking PostgreSQL status..."
if command -v psql >/dev/null 2>&1; then
    if psql -U postgres -d banking_system -c '\q' 2>/dev/null; then
        echo "✅ PostgreSQL database 'banking_system' is accessible"
    else
        echo "❌ PostgreSQL database 'banking_system' is not accessible"
        echo "   Available databases:"
        psql -U postgres -l 2>/dev/null | grep banking || echo "   No banking databases found"
    fi
else
    echo "❌ PostgreSQL psql command not found"
fi

echo ""
echo "🔧 Recommended next steps:"
echo "========================="
echo "1. Check the specific error in catalina.out"
echo "2. Set up PostgreSQL database if missing"
echo "3. Temporarily switch back to MySQL version for testing"
echo "4. Compile Java files if needed"
