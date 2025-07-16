# Banking Application Deployment Scripts

This directory contains deployment scripts for the banking application modernization project.

## Overview

These scripts deploy the legacy banking application to EC2 using manual, controlled processes that demonstrate enterprise deployment practices.

## Prerequisites

- Ubuntu 24.04 EC2 instance (t3.small or larger)
- SSH access to the instance
- Java 17 installed
- Git installed

## Script Execution Order

### 1. Environment Setup
```bash
./setup-ec2-environment.sh
```
**What it does:**
- Installs MySQL Server and creates banking database
- Installs Tomcat 9 configured for port 8082
- Creates directory structure at `/opt/banking-modernization/`
- Configures conservative memory settings (128MB max heap)

### 2. Application Deployment
```bash
./deploy-legacy-manual.sh
```
**What it does:**
- Deploys the banking WAR file to Tomcat
- Handles proper start/stop of Tomcat service
- Validates successful deployment
- Provides access URLs

**Prerequisites:**
- banking.war file must be present in `/opt/banking-modernization/legacy-system/`

### 3. Database Setup
```bash
./database-setup.sh
```
**What it does:**
- Imports the banking database from dump_bk.sql
- Creates backup of existing database (if any)
- Validates successful import
- Shows sample data

**Prerequisites:**
- dump_bk.sql file must be present in `/opt/banking-modernization/legacy-system/`

### 4. Resource Monitoring
```bash
./resource-monitoring.sh
```
**What it does:**
- Shows memory, disk, and CPU usage
- Lists all Java and Python applications
- Checks service status
- Tests application connectivity
- Provides resource warnings

## File Upload Process

Before running the deployment scripts, upload the necessary files to EC2:

```bash
# From your local machine
scp -i "your-key.pem" legacy-system/banking.war ubuntu@your-ip:/opt/banking-modernization/legacy-system/
scp -i "your-key.pem" legacy-system/dump_bk.sql ubuntu@your-ip:/opt/banking-modernization/legacy-system/
```

## Complete Deployment Example

```bash
# 1. Upload files to EC2
scp -i "japanese-drama-source-key-pair.pem" legacy-system/banking.war ubuntu@13.223.22.205:~/
scp -i "japanese-drama-source-key-pair.pem" legacy-system/dump_bk.sql ubuntu@13.223.22.205:~/

# 2. SSH to EC2 and run deployment scripts
ssh -i "japanese-drama-source-key-pair.pem" ubuntu@13.223.22.205

# 3. Copy deployment scripts to EC2
scp -i "japanese-drama-source-key-pair.pem" migration-infrastructure/deployment-scripts/* ubuntu@13.223.22.205:~/scripts/

# 4. Run setup
cd ~/scripts
chmod +x *.sh
./setup-ec2-environment.sh

# 5. Move uploaded files to correct location
sudo mv ~/banking.war /opt/banking-modernization/legacy-system/
sudo mv ~/dump_bk.sql /opt/banking-modernization/legacy-system/

# 6. Deploy application
./deploy-legacy-manual.sh

# 7. Setup database
./database-setup.sh

# 8. Monitor resources
./resource-monitoring.sh
```

## Port Allocation

- **8080**: Sensei Search (existing)
- **8081**: Bungaku Kensaku (existing)
- **8082**: Banking Legacy Application (new)
- **8083**: Banking Modern Application (future)
- **3306**: MySQL (banking database)
- **5432**: PostgreSQL (existing applications)

## Resource Allocation

Designed for t3.small (2GB RAM) with existing applications:

- **Legacy Banking**: 128MB max heap (conservative)
- **Modern Banking**: 256MB max heap (priority)
- **MySQL**: ~100MB
- **Existing Apps**: Keep current allocation
- **Buffer**: ~400MB for system operations

## Troubleshooting

### Common Issues

1. **Port conflicts**: Check with `sudo netstat -tlnp | grep LISTEN`
2. **Memory issues**: Run `./resource-monitoring.sh` to check usage
3. **Database connection**: Verify MySQL is running with `sudo systemctl status mysql`
4. **Tomcat deployment**: Check logs with `sudo journalctl -u tomcat9 -f`

### Useful Commands

```bash
# Check service status
sudo systemctl status tomcat9
sudo systemctl status mysql

# View application logs
sudo journalctl -u tomcat9 -f
sudo tail -f /var/log/tomcat9/catalina.out

# Test database connection
mysql -u bankuser -p BNS

# Check deployed applications
ls -la /var/lib/tomcat9/webapps/

# Restart services
sudo systemctl restart tomcat9
sudo systemctl restart mysql
```

## Security Notes

- Database password is hardcoded for development purposes
- Tomcat is configured with minimal security
- These scripts are for learning/development, not production use

## Next Steps

After successful deployment:
1. Test all banking application features
2. Document any issues or observations
3. Begin planning modern system deployment
4. Set up CI/CD pipeline for automated deployments
