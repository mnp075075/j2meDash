#!/bin/bash
echo "setting up the environment"
sudo apt update
sudo apt-get install -y openjdk-8-jdk

RAW_URL="https://raw.githubusercontent.com/mnp075075/j2meDash/refs/heads/test-level-loader/lib/j2sdk-1_4_2_19-linux-i586.bin"
wget -q --show-progress "$RAW_URL"
chmod +x j2sdk-1_4_2_19-linux-i586.bin

yes | ./j2sdk-1_4_2_19-linux-ia64.bin > /dev/null
if [ -d "j2sdk1.4.2_19" ]; then
    mv j2sdk1.4.2_19 "$INSTALL_DIR/j2sdk1.4.2_19"
fi

echo "succeed"