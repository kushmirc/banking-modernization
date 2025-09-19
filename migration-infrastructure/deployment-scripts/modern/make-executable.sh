#!/bin/bash

# Make all modern deployment scripts executable
echo "Making modern deployment scripts executable..."

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

chmod +x "$SCRIPT_DIR/deploy-modern.sh"
chmod +x "$SCRIPT_DIR/health-check.sh"
chmod +x "$SCRIPT_DIR/rollback.sh"

echo "✓ All scripts are now executable"
echo ""
echo "You can now run:"
echo "  ./deploy-modern.sh dev      - Deploy to dev environment"
echo "  ./deploy-modern.sh prod     - Deploy to production"
echo "  ./health-check.sh           - Check application health"
echo "  ./rollback.sh               - Restore previous version"