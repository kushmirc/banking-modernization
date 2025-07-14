#!/bin/bash
# Compile script for legacy banking system

cd /Users/kushmirchandani/IdeaProjects/banking-modernization/legacy-system/BankingSystem1/WEB-INF/classes

echo "Compiling Java files..."
javac -cp "../lib/*" *.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
else
    echo "Compilation failed!"
fi
