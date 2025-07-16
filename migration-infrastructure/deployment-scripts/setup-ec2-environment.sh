#!/bin/bash
# setup-ec2-environment.sh
# Install MySQL, Tomcat, and configure environment for banking application

set -e  # Exit on any error

echo "=== Banking Application EC2 Environment Setup ==="
echo "Starting setup at $(date)"

# Update system
echo "Updating system packages..."
sudo apt update
sudo apt upgrade -y

# Install MySQL Server
echo "Installing MySQL Server..."
sudo apt install -y mysql-server

# Install Tomcat 9
echo "Installing Tomcat 9..."
sudo apt install -y tomcat9 tomcat9-admin tomcat9-common tomcat9-user

# Configure MySQL
echo "Configuring MySQL..."
sudo mysql -e "CREATE DATABASE IF NOT EXISTS BNS;"
sudo mysql -e "CREATE USER IF NOT EXISTS 'bankuser'@'localhost' IDENTIFIED BY 'bankpass';"
sudo mysql -e "GRANT ALL PRIVILEGES ON BNS.* TO 'bankuser'@'localhost';"
sudo mysql -e "FLUSH PRIVILEGES;"

# Configure Tomcat to run on port 8082 (avoid conflict with existing apps)
echo "Configuring Tomcat for port 8082..."
sudo sed -i 's/port="8080"/port="8082"/' /etc/tomcat9/server.xml

# Configure Tomcat
echo "Configuring Tomcat..."
sudo systemctl enable tomcat9
sudo systemctl start tomcat9

# Create banking application directory structure
sudo mkdir -p /opt/banking-modernization/legacy-system
sudo mkdir -p /opt/banking-modernization/modern-system
sudo chown -R ubuntu:ubuntu /opt/banking-modernization

# Configure memory settings (conservative for t3.small - legacy system gets minimal resources)
echo "JAVA_OPTS=\"-Xms64m -Xmx128m -XX:PermSize=32m -XX:MaxPermSize=64m\"" | sudo tee -a /etc/default/tomcat9

# Restart Tomcat with new settings
sudo systemctl restart tomcat9

echo "=== Setup Complete ==="
echo "MySQL: Running on port 3306"
echo "Tomcat: Running on port 8082"
echo "Banking app directory: /opt/banking-modernization/legacy-system"
echo "Future modern system: /opt/banking-modernization/modern-system"
echo "Next step: Run deploy-legacy-manual.sh"
