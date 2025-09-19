#!/bin/bash
# Jakarta EE Migration Script
# Converts javax.servlet imports to jakarta.servlet for Tomcat 10 compatibility

echo "Starting Jakarta EE migration..."
echo "Converting javax.servlet imports to jakarta.servlet..."

# Find all Java files and replace javax.servlet with jakarta.servlet
find . -name "*.java" -exec sed -i '' 's/javax\.servlet/jakarta.servlet/g' {} +

echo "Migration complete!"
echo "Files modified:"
grep -r "jakarta.servlet" . --include="*.java" | cut -d: -f1 | sort | uniq
