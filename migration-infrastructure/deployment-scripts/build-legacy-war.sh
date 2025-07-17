#!/bin/bash
# build-legacy-war.sh
# Compile Java source code and package into banking.war file

set -e  # Exit on any error

echo "=== Legacy Banking Application Build ==="
echo "Starting build at $(date)"

# Configuration
LEGACY_DIR="legacy-system"
SOURCE_DIR="$LEGACY_DIR/BankingSystem1"
BUILD_DIR="$LEGACY_DIR/build"
WAR_FILE="$LEGACY_DIR/banking.war"
WEB_INF_CLASSES="$BUILD_DIR/WEB-INF/classes"

# Check if we're in the right directory
if [ ! -d "$SOURCE_DIR" ]; then
    echo "❌ Error: Source directory $SOURCE_DIR not found."
    echo "Please run this script from the banking-modernization root directory."
    exit 1
fi

# Clean previous build
echo "Cleaning previous build..."
rm -rf "$BUILD_DIR"
rm -f "$WAR_FILE"

# Create build directory structure
echo "Creating build directory structure..."
mkdir -p "$BUILD_DIR"
mkdir -p "$WEB_INF_CLASSES"

# Copy web content (JSP files, static assets, etc.)
echo "Copying web content..."
cp -r "$SOURCE_DIR"/* "$BUILD_DIR"/
# Remove Java source files from web content (they shouldn't be in WAR)
find "$BUILD_DIR" -name "*.java" -not -path "*/WEB-INF/classes/*" -delete

# Ensure WEB-INF structure exists
mkdir -p "$BUILD_DIR/WEB-INF/classes"
mkdir -p "$BUILD_DIR/WEB-INF/lib"

# Copy compiled classes if they exist in WEB-INF/classes
if [ -d "$SOURCE_DIR/WEB-INF/classes" ]; then
    echo "Copying existing compiled classes..."
    cp -r "$SOURCE_DIR/WEB-INF/classes"/* "$WEB_INF_CLASSES"/
fi

# Find and compile Java source files
echo "Finding Java source files..."
JAVA_FILES=$(find "$SOURCE_DIR" -name "*.java" | tr '\n' ' ')

if [ -n "$JAVA_FILES" ]; then
    echo "Compiling Java source files..."
    echo "Found Java files: $JAVA_FILES"
    
    # Set up classpath (include servlet API and other dependencies)
    CLASSPATH="/usr/share/tomcat9/lib/*:$SOURCE_DIR/WEB-INF/lib/*"
    
    # Check if running on macOS or Linux and adjust classpath accordingly
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS - try to find servlet API in common locations
        if [ -d "/usr/local/Cellar/tomcat" ]; then
            CLASSPATH="/usr/local/Cellar/tomcat/*/libexec/lib/*:$SOURCE_DIR/WEB-INF/lib/*"
        else
            echo "⚠️  Warning: Tomcat not found in standard macOS location."
            echo "You may need to install Tomcat or adjust CLASSPATH manually."
            CLASSPATH="$SOURCE_DIR/WEB-INF/lib/*"
        fi
    fi
    
    # Compile Java files
    javac -cp "$CLASSPATH" -d "$WEB_INF_CLASSES" $JAVA_FILES
    
    if [ $? -eq 0 ]; then
        echo "✅ Java compilation successful!"
    else
        echo "❌ Java compilation failed!"
        echo ""
        echo "If you get classpath errors, you may need to:"
        echo "1. Install Tomcat locally: brew install tomcat (macOS) or apt install tomcat9 (Ubuntu)"
        echo "2. Download servlet-api.jar manually and place in $SOURCE_DIR/WEB-INF/lib/"
        echo "3. Or compile on the target EC2 server where Tomcat is installed"
        exit 1
    fi
else
    echo "No Java source files found to compile."
fi

# Create WAR file
echo "Creating WAR file..."
cd "$BUILD_DIR"
jar -cvf "../banking.war" *
cd - > /dev/null

# Verify WAR file was created
if [ -f "$WAR_FILE" ]; then
    WAR_SIZE=$(ls -lh "$WAR_FILE" | awk '{print $5}')
    echo "✅ WAR file created successfully!"
    echo "Location: $WAR_FILE"
    echo "Size: $WAR_SIZE"
    
    # Show WAR contents
    echo ""
    echo "WAR file contents:"
    jar -tf "$WAR_FILE" | head -20
    if [ $(jar -tf "$WAR_FILE" | wc -l) -gt 20 ]; then
        echo "... and $(( $(jar -tf "$WAR_FILE" | wc -l) - 20 )) more files"
    fi
else
    echo "❌ Failed to create WAR file!"
    exit 1
fi

# Clean up build directory
echo ""
echo "Cleaning up build directory..."
rm -rf "$BUILD_DIR"

echo ""
echo "=== Build Complete ==="
echo "WAR file ready for deployment: $WAR_FILE"
echo "Next step: Upload WAR file to EC2 and run deploy-legacy-manual.sh"
echo ""
echo "Upload command:"
echo "scp -i \"your-key.pem\" $WAR_FILE ubuntu@your-server:/opt/banking-modernization/legacy-system/"
