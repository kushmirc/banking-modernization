#!/bin/bash
# database-setup.sh
# Import the banking database schema and data

set -e  # Exit on any error

echo "=== Banking Database Setup ==="
echo "Starting database setup at $(date)"

# Configuration
BANKING_DIR="/opt/banking-modernization/legacy-system"
SQL_FILE="$BANKING_DIR/dump_bk.sql"
DB_NAME="BNS"
DB_USER="bankuser"
DB_PASS="bankpass"

# Check if banking directory exists
if [ ! -d "$BANKING_DIR" ]; then
    echo "❌ Error: Banking directory not found. Please run setup-ec2-environment.sh first."
    exit 1
fi

# Check if SQL dump file exists
if [ ! -f "$SQL_FILE" ]; then
    echo "❌ Error: SQL dump file not found at $SQL_FILE"
    echo "Please ensure dump_bk.sql has been uploaded to $BANKING_DIR"
    exit 1
fi

# Check if MySQL is running
if ! sudo systemctl is-active --quiet mysql; then
    echo "❌ Error: MySQL is not running. Please run setup-ec2-environment.sh first."
    exit 1
fi

# Test database connection
echo "Testing database connection..."
if ! mysql -u "$DB_USER" -p"$DB_PASS" -e "SELECT 1;" > /dev/null 2>&1; then
    echo "❌ Error: Cannot connect to database. Please check setup-ec2-environment.sh was run successfully."
    exit 1
fi

# Backup existing database if it exists
echo "Creating backup of existing database (if any)..."
mysqldump -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" > "$BANKING_DIR/backup_$(date +%Y%m%d_%H%M%S).sql" 2>/dev/null || echo "No existing database to backup"

# Import database
echo "Importing database from $SQL_FILE..."
mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" < "$SQL_FILE"

# Verify import
echo "Verifying database import..."
TABLES=$(mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" -e "SHOW TABLES;" | wc -l)
if [ "$TABLES" -gt 1 ]; then
    echo "✅ Database imported successfully!"
    echo "Tables found: $((TABLES - 1))"
else
    echo "❌ Database import may have failed. No tables found."
    exit 1
fi

# Display table information
echo ""
echo "=== Database Status ==="
echo "Database: $DB_NAME"
echo "User: $DB_USER"
echo "Tables:"
mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" -e "SHOW TABLES;"

echo ""
echo "Sample data from administrator table:"
mysql -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" -e "SELECT * FROM administrator LIMIT 3;"

echo ""
echo "✅ Database setup complete!"
echo "Application should now be able to connect to the database."
