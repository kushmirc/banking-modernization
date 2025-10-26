#!/bin/bash

# ================================================================
# Modern Banking System Deployment Script
# ================================================================
# Deploys the Spring Boot modern banking application
# Can be run manually or called by Jenkins
# 
# Usage: ./deploy-modern.sh [environment]
#   environment: dev|prod (default: dev)
# ================================================================

set -e  # Exit on error

# Basic Configuration
APP_NAME="modern-system"
APP_DIR="/opt/banking-modernization"
BACKUP_DIR="$APP_DIR/modern-system/backups"
LOG_DIR="$APP_DIR/modern-system/logs"
JAR_NAME="banking-modern-0.0.1-SNAPSHOT.jar"
APP_PORT="8083"
ENVIRONMENT=${1:-dev}

# Database settings (in production, use environment variables or secrets)
DB_HOST="localhost"
DB_NAME="banking_system"
DB_USER="banking_user"
DB_PASSWORD="Passw0rd!"  # Should be externalized in real deployment

echo "========================================"
echo "Deploying Modern Banking System"
echo "Environment: $ENVIRONMENT"
echo "Time: $(date)"
echo "========================================"

# 1. Check Prerequisites
echo "Checking prerequisites..."

# Check if Java 17 is installed
if ! java -version 2>&1 | grep -q "version \"17"; then
    echo "Error: Java 17 is required"
    exit 1
fi

# Check if directories exist
if [ ! -d "$APP_DIR" ]; then
    echo "Creating app directory..."
    sudo mkdir -p "$APP_DIR"
    sudo chown $USER:$USER "$APP_DIR"
fi

if [ ! -d "$LOG_DIR" ]; then
    echo "Creating logs directory..."
    sudo mkdir -p "$LOG_DIR"
fi

if [ ! -d "$BACKUP_DIR" ]; then
    echo "Creating backup directory..."
    sudo mkdir -p "$BACKUP_DIR"
    sudo chown $USER:$USER "$BACKUP_DIR"
fi

# 2. Get the JAR file
echo "Getting application JAR..."

# If running from Jenkins, the JAR should be in workspace
if [ -n "$WORKSPACE" ]; then
    # Jenkins environment - JAR built by GitHub Actions and downloaded
    JAR_SOURCE="$WORKSPACE/modern-system/target/$JAR_NAME"
    echo "Using JAR from Jenkins workspace: $JAR_SOURCE"
elif [ -f "/opt/banking-modernization/modern-system/target/$JAR_NAME" ]; then
    # JAR already exists, use it
    echo "Using existing JAR from target directory..."
    JAR_SOURCE="/opt/banking-modernization/modern-system/target/$JAR_NAME"
else
    # Build from source (only if mvnw exists)
    echo "Building from source..."
    cd /opt/banking-modernization/modern-system
    ./mvnw clean package -DskipTests
    JAR_SOURCE="target/$JAR_NAME"
fi

if [ ! -f "$JAR_SOURCE" ]; then
    echo "Error: JAR file not found at $JAR_SOURCE"
    exit 1
fi

# 3. Backup Current Version
if [ -f "$APP_DIR/modern-system/$JAR_NAME" ]; then
    echo "Backing up current version..."
    BACKUP_NAME="${JAR_NAME}.$(date +%Y%m%d_%H%M%S)"
    cp "$APP_DIR/modern-system/$JAR_NAME" "$BACKUP_DIR/$BACKUP_NAME"
    echo "Backup saved as: $BACKUP_NAME"
fi

# 4. Stop Current Application
echo "Stopping current application..."

# Check if app is running and stop it
if pgrep -f "$JAR_NAME" > /dev/null; then
    echo "Found running instance, stopping..."
    pkill -f "$JAR_NAME" || true
    sleep 3
else
    echo "No running instance found"
fi

# 5. Deploy New Version
echo "Deploying new version..."
cp "$JAR_SOURCE" "$APP_DIR/modern-system/$JAR_NAME"
chmod +x "$APP_DIR/modern-system/$JAR_NAME"

# 6. Start Application
echo "Starting application..."

cd "$APP_DIR"
nohup java -Xmx200m -jar "$APP_DIR/modern-system/$JAR_NAME" \
    --spring.profiles.active="$ENVIRONMENT" \
    --server.port="$APP_PORT" \
    --spring.datasource.url="jdbc:postgresql://${DB_HOST}:5432/${DB_NAME}" \
    --spring.datasource.username="$DB_USER" \
    --spring.datasource.password="$DB_PASSWORD" \
    > "$LOG_DIR/app.log" 2>&1 &

APP_PID=$!
echo $APP_PID > "$APP_DIR/modern-system/app.pid"
echo "Application started with PID: $APP_PID"


# 7. Health Check
echo "Waiting for application to start..."
sleep 10

echo "Performing health check..."
MAX_ATTEMPTS=5
ATTEMPT=1

while [ $ATTEMPT -le $MAX_ATTEMPTS ]; do
    if curl -f -s http://localhost:${APP_PORT}/actuator/health > /dev/null; then
        echo "✓ Health check passed! Application is running."
        break
    fi
    
    echo "  Attempt $ATTEMPT/$MAX_ATTEMPTS failed. Waiting..."
    sleep 5
    ATTEMPT=$((ATTEMPT + 1))
done

if [ $ATTEMPT -gt $MAX_ATTEMPTS ]; then
    echo "✗ Health check failed after $MAX_ATTEMPTS attempts"
    echo "Rolling back..."
    
    # Simple rollback: stop new version and restore backup
    pkill -f "$JAR_NAME" || true
    
    LATEST_BACKUP=$(ls -t "$BACKUP_DIR"/*.jar 2>/dev/null | head -1)
    if [ -n "$LATEST_BACKUP" ]; then
        cp "$LATEST_BACKUP" "$APP_DIR/modern-system/$JAR_NAME"
        echo "Restored from backup: $(basename $LATEST_BACKUP)"
        echo "Please start the application manually"
    fi
    
    exit 1
fi

# 8. Basic Smoke Test
echo "Running basic smoke test..."

# Just check if the main endpoints respond
if curl -s http://localhost:${APP_PORT}/ | grep -q "Welcome"; then
    echo "✓ Homepage is responding"
else
    echo "⚠ Homepage check failed (non-critical)"
fi

echo "========================================"
echo "Deployment completed successfully!"
echo "Application running on port $APP_PORT"
echo "PID: $APP_PID"
echo "========================================"

# Exit successfully for Jenkins
exit 0