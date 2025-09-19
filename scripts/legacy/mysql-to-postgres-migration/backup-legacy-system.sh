#!/bin/bash

# Banking System PostgreSQL Migration - Backup Script
# This script creates a backup of the current MySQL-based legacy system
# before converting it to PostgreSQL

echo "🔄 Starting Banking System Backup Process..."
echo "=================================================="

# Define paths
SOURCE_DIR="/Users/kushmirchandani/IdeaProjects/banking-modernization/legacy-system"
BACKUP_DIR="/Users/kushmirchandani/IdeaProjects/banking-modernization/legacy-system-original-mysql"

# Check if source exists
if [ ! -d "$SOURCE_DIR" ]; then
    echo "❌ Error: Source directory not found: $SOURCE_DIR"
    exit 1
fi

# Check if backup already exists
if [ -d "$BACKUP_DIR" ]; then
    echo "⚠️  Warning: Backup directory already exists: $BACKUP_DIR"
    echo "   This will overwrite the existing backup."
    read -p "   Continue? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "❌ Backup cancelled."
        exit 1
    fi
    rm -rf "$BACKUP_DIR"
fi

# Create backup
echo "📋 Copying legacy-system to legacy-system-original-mysql..."
cp -r "$SOURCE_DIR" "$BACKUP_DIR"

# Verify backup
if [ $? -eq 0 ]; then
    echo "✅ Backup completed successfully!"
    echo ""
    echo "📊 Backup Statistics:"
    echo "   Source: $SOURCE_DIR"
    echo "   Backup: $BACKUP_DIR"
    
    # Count files in backup
    FILE_COUNT=$(find "$BACKUP_DIR" -type f | wc -l)
    DIR_COUNT=$(find "$BACKUP_DIR" -type d | wc -l)
    TOTAL_SIZE=$(du -sh "$BACKUP_DIR" | cut -f1)
    
    echo "   Files: $FILE_COUNT"
    echo "   Directories: $DIR_COUNT"
    echo "   Total Size: $TOTAL_SIZE"
    echo ""
    
    echo "📁 Top-level backup contents:"
    ls -la "$BACKUP_DIR" | head -10
    
    echo ""
    echo "🎯 Next Steps:"
    echo "   1. The original MySQL version is now safely backed up"
    echo "   2. You can proceed with PostgreSQL conversion on legacy-system/"
    echo "   3. All deployment scripts will continue to work unchanged"
    echo "   4. Portfolio will show both database technologies"
    
else
    echo "❌ Backup failed!"
    exit 1
fi

echo ""
echo "=================================================="
echo "✅ Backup process completed successfully!"
