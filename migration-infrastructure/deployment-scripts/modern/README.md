# Jenkins Deployment Setup for Modern Banking System

## Overview

This setup demonstrates basic CI/CD using Jenkins to deploy our Spring Boot application. GitHub Actions handles CI (building and testing), while Jenkins handles CD (deployment to EC2).

## Why Jenkins?

Jenkins is widely used in enterprise environments for deployment automation. This project demonstrates:
- Basic Jenkins pipeline setup
- Integration with GitHub
- Automated deployment to EC2
- Simple health checks and rollback capabilities

## Prerequisites

- EC2 instance with Java 17 installed
- PostgreSQL database running
- Modern banking application built and tested via GitHub Actions

## Quick Setup Guide

### 1. Install Jenkins on EC2

```bash
# Install Java 11 for Jenkins (Jenkins needs Java 11, our app needs Java 17)
sudo apt update
sudo apt install openjdk-11-jdk -y

# Add Jenkins repository
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
echo "deb https://pkg.jenkins.io/debian-stable binary/" | sudo tee /etc/apt/sources.list.d/jenkins.list

# Install Jenkins
sudo apt update
sudo apt install jenkins -y

# Start Jenkins
sudo systemctl start jenkins
sudo systemctl enable jenkins

# Get initial password
sudo cat /var/jenkins_home/secrets/initialAdminPassword
```

### 2. Configure Jenkins

1. Access Jenkins at `http://your-ec2-ip:8080`
2. Install suggested plugins
3. Create admin user
4. Install additional plugin: "GitHub plugin"

### 3. Create Deployment Job

1. Click "New Item"
2. Name: `deploy-banking-modern`
3. Type: "Pipeline"
4. Pipeline settings:
   - Definition: "Pipeline script from SCM"
   - SCM: Git
   - Repository URL: `https://github.com/your-username/banking-modernization`
   - Script Path: `modern-system/Jenkinsfile`

### 4. Set Up Deployment Scripts

Copy the deployment scripts to your EC2:

```bash
# Create directories
sudo mkdir -p /opt/banking-modern
sudo mkdir -p /opt/backups/banking-modern
sudo chown ubuntu:ubuntu /opt/banking-modern
sudo chown ubuntu:ubuntu /opt/backups

# Copy scripts (from local machine)
scp deploy-modern.sh ubuntu@your-ec2-ip:/opt/banking-modern/
scp health-check.sh ubuntu@your-ec2-ip:/opt/banking-modern/
scp rollback.sh ubuntu@your-ec2-ip:/opt/banking-modern/

# Make executable
ssh ubuntu@your-ec2-ip
chmod +x /opt/banking-modern/*.sh
```

## Usage

### Manual Deployment

```bash
# Deploy to dev environment
./deploy-modern.sh dev

# Check health
./health-check.sh

# Rollback if needed
./rollback.sh
```

### Jenkins Deployment

1. Go to Jenkins dashboard
2. Click on `deploy-banking-modern`
3. Click "Build with Parameters"
4. Select environment (dev/prod)
5. Click "Build"
6. Watch console output for progress

## Monitoring Deployment

```bash
# Watch application logs
tail -f /var/log/banking-modern.log

# Check if running
ps aux | grep banking-modern

# Check health endpoint
curl http://localhost:8081/actuator/health
```

## Troubleshooting

### Application Won't Start
- Check Java version: `java -version` (should be 17)
- Check port 8081 is free: `sudo lsof -i :8081`
- Check database connection settings

### Jenkins Build Fails
- Check Jenkins has access to GitHub (public repo)
- Verify Jenkinsfile path is correct
- Check console output for specific errors

### Rollback Needed
```bash
# List available backups
ls -la /opt/backups/banking-modern/

# Rollback to specific version
./rollback.sh banking-modern-0.0.1-SNAPSHOT.jar.20250101_120000

# Or rollback to most recent
./rollback.sh
```

## What This Demonstrates

For interviews, this setup shows:
1. **Understanding of CI/CD concepts** - separation of build (CI) and deploy (CD)
2. **Basic Jenkins knowledge** - pipeline creation, parameterized builds
3. **Deployment automation** - scripted deployments with health checks
4. **Risk mitigation** - backup and rollback procedures
5. **Integration skills** - connecting GitHub, Jenkins, and EC2

## Next Steps (Future Enhancements)

These are documented as potential improvements but not implemented:
- Add Slack notifications for deployment status
- Implement blue-green deployments for zero downtime
- Add automated database migrations
- Integrate with monitoring tools like CloudWatch
- Add more comprehensive smoke tests

## Key Files

- `deploy-modern.sh` - Main deployment script
- `health-check.sh` - Verifies application is running
- `rollback.sh` - Restores previous version
- `Jenkinsfile` - Jenkins pipeline configuration

## Important Notes

- Database password is currently hardcoded (would use secrets in production)
- This is a learning setup - production would have more security measures
- Feature flags for gradual rollout are planned but not yet implemented