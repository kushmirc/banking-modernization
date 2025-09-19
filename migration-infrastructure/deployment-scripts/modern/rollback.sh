#!/bin/bash

# ================================================================
# Rollback Script for Modern Banking System
# ================================================================
# Simple script to restore the previous version if deployment fails
# 
# Usage: ./rollback.sh [backup-file]
#   If no backup file specified, uses the most recent backup
# ================================================================

APP_DIR="/opt/banking-modern"
BACKUP_DIR="/opt/backups/banking-modern"
JAR_NAME="banking-modern-0.0.1-SNAPSHOT.jar"

echo "========================================"
echo "Starting Rollback Process"
echo "Time: $(date)"
echo "========================================"

# 1. Find backup to restore
if [ -n "$1" ]; then
    # Specific backup requested
    BACKUP_FILE="$BACKUP_DIR/$1"
    if [ ! -f "$BACKUP_FILE" ]; then
        echo "Error: Backup file not found: $1"
        exit 1
    fi
else
    # Use most recent backup
    BACKUP_FILE=$(ls -t "$BACKUP_DIR"/*.jar 2>/dev/null | head -1)
    if [ -z "$BACKUP_FILE" ]; then
        echo "Error: No backup files found in $BACKUP_DIR"
        exit 1
    fi
fi

echo "Using backup: $(basename $BACKUP_FILE)"

# 2. Stop current application
echo "Stopping current application..."
pkill -f "$JAR_NAME" || true
sleep 3

# 3. Restore backup
echo "Restoring from backup..."
cp "$BACKUP_FILE" "$APP_DIR/$JAR_NAME"
chmod +x "$APP_DIR/$JAR_NAME"

# 4. Start application
echo "Starting restored version..."
cd "$APP_DIR"
nohup java -jar "$JAR_NAME" \
    --spring.profiles.active=dev \
    --server.port=8081 \
    > /var/log/banking-modern.log 2>&1 &

APP_PID=$!
echo "Application started with PID: $APP_PID"

# 5. Wait and verify
echo "Waiting for application to start..."
sleep 10

# 6. Check if running
if curl -f -s http://localhost:8081/actuator/health > /dev/null; then
    echo "✓ Rollback successful! Application is running."
else
    echo "✗ Rollback failed - manual intervention required"
    echo "Check logs at: /var/log/banking-modern.log"
    exit 1
fi

echo "========================================"
echo "Rollback completed successfully"
echo "Restored version: $(basename $BACKUP_FILE)"
echo "========================================"

exit 0