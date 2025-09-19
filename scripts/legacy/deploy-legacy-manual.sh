#!/bin/bash
# deploy-legacy-manual.sh
# Deploy the legacy banking application WAR file to Tomcat

set -e  # Exit on any error

echo "=== Legacy Banking Application Deployment ==="
echo "Starting deployment at $(date)"

# Configuration
TOMCAT_DIR="/var/lib/tomcat9"
WEBAPPS_DIR="$TOMCAT_DIR/webapps"
BANKING_DIR="/opt/banking-modernization/legacy-system"
APP_NAME="banking"
WAR_FILE="$BANKING_DIR/banking.war"

# Check if setup was run first
if [ ! -d "$BANKING_DIR" ]; then
    echo "❌ Error: Banking directory not found. Please run setup-ec2-environment.sh first."
    exit 1
fi

# Check if Tomcat is running
if ! sudo systemctl is-active --quiet tomcat9; then
    echo "❌ Error: Tomcat is not running. Please run setup-ec2-environment.sh first."
    exit 1
fi

# Check if WAR file exists
if [ ! -f "$WAR_FILE" ]; then
    echo "❌ Error: WAR file not found at $WAR_FILE"
    echo "Please ensure the banking.war file has been uploaded to $BANKING_DIR"
    exit 1
fi

# Stop Tomcat for deployment
echo "Stopping Tomcat for deployment..."
sudo systemctl stop tomcat9

# Remove existing deployment if it exists
if [ -d "$WEBAPPS_DIR/$APP_NAME" ]; then
    echo "Removing existing deployment..."
    sudo rm -rf "$WEBAPPS_DIR/$APP_NAME"
fi

if [ -f "$WEBAPPS_DIR/$APP_NAME.war" ]; then
    echo "Removing existing WAR file..."
    sudo rm -f "$WEBAPPS_DIR/$APP_NAME.war"
fi

# Deploy new WAR file
echo "Deploying new WAR file..."
sudo cp "$WAR_FILE" "$WEBAPPS_DIR/$APP_NAME.war"
sudo chown tomcat:tomcat "$WEBAPPS_DIR/$APP_NAME.war"

# Start Tomcat
echo "Starting Tomcat..."
sudo systemctl start tomcat9

# Wait for deployment
echo "Waiting for application to deploy..."
sleep 10

# Check if application deployed successfully
DEPLOYED=false
for i in {1..30}; do
    if [ -d "$WEBAPPS_DIR/$APP_NAME" ]; then
        DEPLOYED=true
        break
    fi
    echo "Waiting for deployment... ($i/30)"
    sleep 2
done

if [ "$DEPLOYED" = true ]; then
    echo "✅ Application deployed successfully!"
    echo "Application URL: http://localhost:8082/$APP_NAME"
    echo "External URL: http://13.223.22.205:8082/$APP_NAME"
else
    echo "❌ Deployment may have failed. Check Tomcat logs:"
    echo "sudo journalctl -u tomcat9 -f"
    exit 1
fi

# Display status
echo ""
echo "=== Deployment Status ==="
echo "Tomcat Status: $(sudo systemctl is-active tomcat9)"
echo "Application Directory: $WEBAPPS_DIR/$APP_NAME"
echo "Logs: sudo journalctl -u tomcat9 -f"
echo "To undeploy: sudo rm -rf $WEBAPPS_DIR/$APP_NAME*"
echo ""
echo "Next step: Run database-setup.sh to import the database"
