#!/bin/bash
# resource-monitoring.sh
# Monitor system resources and all running applications

echo "=== System Resource Monitoring ==="
echo "Timestamp: $(date)"
echo ""

# Memory usage
echo "=== Memory Usage ==="
free -h
echo ""

# Disk usage
echo "=== Disk Usage ==="
df -h | grep -E "(Filesystem|/dev/root)"
echo ""

# CPU usage
echo "=== CPU Usage ==="
top -bn1 | head -3
echo ""

# Java applications
echo "=== Java Applications ==="
ps aux | grep java | grep -v grep | while read line; do
    pid=$(echo $line | awk '{print $2}')
    cmd=$(echo $line | awk '{for(i=11;i<=NF;i++) printf $i" "}')
    mem=$(echo $line | awk '{print $4}')
    echo "PID: $pid | Memory: $mem% | Command: $cmd"
done
echo ""

# Python applications
echo "=== Python Applications ==="
ps aux | grep python | grep -v grep | while read line; do
    pid=$(echo $line | awk '{print $2}')
    cmd=$(echo $line | awk '{for(i=11;i<=NF;i++) printf $i" "}')
    mem=$(echo $line | awk '{print $4}')
    echo "PID: $pid | Memory: $mem% | Command: $cmd"
done
echo ""

# Network ports
echo "=== Network Ports ==="
echo "Active services:"
sudo netstat -tlnp | grep LISTEN | grep -E "(8080|8081|8082|8000|8001|3306|5432)" | while read line; do
    port=$(echo $line | awk '{print $4}' | cut -d':' -f2)
    process=$(echo $line | awk '{print $7}')
    echo "Port $port: $process"
done
echo ""

# Service status
echo "=== Service Status ==="
echo "MySQL: $(sudo systemctl is-active mysql || echo 'not installed')"
echo "Tomcat9: $(sudo systemctl is-active tomcat9 || echo 'not installed')"
echo "PostgreSQL: $(sudo systemctl is-active postgresql || echo 'not installed')"
echo "Nginx: $(sudo systemctl is-active nginx || echo 'not installed')"
echo ""

# Banking application specific checks
echo "=== Banking Application Status ==="
if [ -d "/opt/banking-modernization/legacy-system" ]; then
    echo "Legacy system directory: ✅"
    if [ -f "/var/lib/tomcat9/webapps/banking.war" ]; then
        echo "Legacy WAR deployed: ✅"
    else
        echo "Legacy WAR deployed: ❌"
    fi
    
    if [ -d "/var/lib/tomcat9/webapps/banking" ]; then
        echo "Legacy app expanded: ✅"
    else
        echo "Legacy app expanded: ❌"
    fi
else
    echo "Legacy system directory: ❌"
fi

if [ -d "/opt/banking-modernization/modern-system" ]; then
    echo "Modern system directory: ✅"
else
    echo "Modern system directory: ❌"
fi

# Test banking application connectivity
echo ""
echo "=== Application Connectivity Tests ==="
if curl -s -o /dev/null -w "%{http_code}" http://localhost:8082/banking | grep -q "200"; then
    echo "Banking application (8082): ✅ Responding"
else
    echo "Banking application (8082): ❌ Not responding"
fi

if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080 | grep -q "200"; then
    echo "Sensei Search (8080): ✅ Responding"
else
    echo "Sensei Search (8080): ❌ Not responding"
fi

if curl -s -o /dev/null -w "%{http_code}" http://localhost:8081 | grep -q "200"; then
    echo "Bungaku Kensaku (8081): ✅ Responding"
else
    echo "Bungaku Kensaku (8081): ❌ Not responding"
fi

echo ""
echo "=== Resource Summary ==="
TOTAL_MEM=$(free -m | grep Mem | awk '{print $2}')
USED_MEM=$(free -m | grep Mem | awk '{print $3}')
AVAILABLE_MEM=$(free -m | grep Mem | awk '{print $7}')
MEM_PERCENT=$(echo "scale=1; $USED_MEM * 100 / $TOTAL_MEM" | bc -l)

echo "Memory: ${USED_MEM}MB / ${TOTAL_MEM}MB (${MEM_PERCENT}%) - Available: ${AVAILABLE_MEM}MB"

DISK_USAGE=$(df -h | grep '/dev/root' | awk '{print $5}' | sed 's/%//')
echo "Disk: ${DISK_USAGE}% used"

if [ "$DISK_USAGE" -gt 85 ]; then
    echo "⚠️  Warning: Disk usage is high (${DISK_USAGE}%)"
fi

if [ "$AVAILABLE_MEM" -lt 200 ]; then
    echo "⚠️  Warning: Available memory is low (${AVAILABLE_MEM}MB)"
fi
