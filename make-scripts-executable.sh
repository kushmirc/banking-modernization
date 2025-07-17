#!/bin/bash
# make-scripts-executable.sh
# Make all deployment scripts executable

chmod +x migration-infrastructure/deployment-scripts/*.sh
chmod +x make-scripts-executable.sh
echo "All deployment scripts are now executable!"
echo "Scripts ready:"
ls -la migration-infrastructure/deployment-scripts/*.sh
