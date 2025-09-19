#!/bin/bash

# ================================================================
# Health Check Script for Modern Banking System
# ================================================================
# Simple script to verify the application is running properly
# Used after deployment to ensure everything is working
# ================================================================

APP_PORT="8081"
APP_NAME="banking-modern"

echo "Running health check for $APP_NAME..."

# 1. Check if process is running
if pgrep -f "$APP_NAME" > /dev/null; then
    echo "✓ Process is running"
else
    echo "✗ Process is not running"
    exit 1
fi

# 2. Check if port is listening
if netstat -tuln | grep -q ":$APP_PORT"; then
    echo "✓ Port $APP_PORT is listening"
else
    echo "✗ Port $APP_PORT is not listening"
    exit 1
fi

# 3. Check health endpoint
if curl -f -s http://localhost:${APP_PORT}/actuator/health > /dev/null; then
    echo "✓ Health endpoint responding"
    
    # Get detailed status
    STATUS=$(curl -s http://localhost:${APP_PORT}/actuator/health | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
    echo "  Status: $STATUS"
else
    echo "✗ Health endpoint not responding"
    exit 1
fi

# 4. Check database connectivity (if endpoint exists)
if curl -f -s http://localhost:${APP_PORT}/actuator/health/db > /dev/null 2>&1; then
    DB_STATUS=$(curl -s http://localhost:${APP_PORT}/actuator/health/db | grep -o '"status":"[^"]*"' | cut -d'"' -f4)
    echo "✓ Database connection: $DB_STATUS"
fi

echo ""
echo "Health check completed successfully!"
exit 0