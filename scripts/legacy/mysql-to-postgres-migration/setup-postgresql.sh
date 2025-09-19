#!/bin/bash

echo "🐘 Setting up PostgreSQL for Banking System"
echo "==========================================="

# Check if PostgreSQL is installed
if ! command -v psql &> /dev/null; then
    echo "❌ PostgreSQL is not installed"
    echo "Install with: brew install postgresql"
    exit 1
fi

# Check if PostgreSQL service is running
if ! pgrep -x "postgres" > /dev/null; then
    echo "🔄 Starting PostgreSQL service..."
    brew services start postgresql
    sleep 3
fi

echo "✅ PostgreSQL service is running"

# Create database
echo "📄 Creating banking_system database..."
if createdb banking_system 2>/dev/null; then
    echo "✅ Database 'banking_system' created successfully"
else
    echo "ℹ️  Database 'banking_system' already exists or creation failed"
fi

# Run schema
echo "🗃️  Setting up database schema..."
if psql banking_system < /Users/kushmirchandani/IdeaProjects/banking-modernization/legacy-system/schema_postgresql.sql; then
    echo "✅ Schema loaded successfully"
else
    echo "❌ Schema loading failed"
    exit 1
fi

# Verify data
echo "🔍 Verifying database setup..."
psql banking_system -c "SELECT 'Administrator count:', count(*) FROM administrator UNION ALL SELECT 'Customer count:', count(*) FROM customer;"

echo ""
echo "✅ PostgreSQL setup complete!"
echo "🔄 Now restart Tomcat to test the application"