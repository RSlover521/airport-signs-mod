# PLEASE READ THIS BEFORE YOU DO ANYTHING:
# --------------------------------------------------------------------------------------------
# Run this script to launch minecraft in a virtual desktop, run stop.sh if you want to stop
# 
# To run this script, type the following code in the terminal:
# chmod +x start.sh
# ./start.sh
#
# To run stop.sh, type the following code in the terminal:
# chmod +x stop.sh
# ./stop.sh
# 
# Please email wulance58@gmail.com for any errors encountered 
# during the build or create an issue directly on github. Link to the issue page: 
# https://github.com/RSlover52111/ForgeProject/issues
# --------------------------------------------------------------------------------------------

#!/usr/bin/env bash
set -e

echo "🔨 Building Mod..."
./gradlew build

echo "🎮 Launching Minecraft with Sunshine streaming..."

MC_DIR="workspaces/ForgeProject/Forge-Project-1.20.X"
XPRA_DISPLAY=":100"
XPRA_PORT=8080

# -----------------------------
# 0. Install Xpra
# -----------------------------
if ! command -v xpra &> /dev/null; then
  echo "--------------------------"
  echo " 📦 Installing Xpra... "
  echo "---------------------------" 
  sudo apt-get update
  sudo apt-get install -y xpra
  # Add Xpra signing key
  wget -q https://xpra.org/gpg.asc -O- | sudo apt-key add -

  # Detect your Ubuntu codename
  lsb_release -cs
  # On Codespaces with Ubuntu 24.04, it should print: noble

  # Add the repo (replace noble with what your system printed)
  echo "deb http://xpra.org/ noble main" | sudo tee /etc/apt/sources.list.d/xpra.list

  # Update and install
  sudo apt-get update
  sudo apt-get install xpra
fi

echo "--------------------------------"
echo " 📦 Installing lxterminal..."
echo "--------------------------------"
sudo apt-get install lxterminal

# -----------------------------
# 1. Clean up old sessions
# -----------------------------
echo "------------------------------------------------"
echo "🧹 Cleaning old Xpra and Sunshine sessions..."
echo "------------------------------------------------"
xpra stop $XPRA_DISPLAY || true
pkill -f sunshine || true
sleep 2

# ------------------------------------------
# 2. Start Xpra virtual desktop + Minecraft
# ------------------------------------------
# Try lxterminal, fallback to xterm if missing

echo "----------------------------------------------------"
echo " 🚀 Launching Xpra virtual desktop and Minecraft..."
echo "----------------------------------------------------"

if command -v lxterminal &>/dev/null; then
    TERMINAL="lxterminal"
    echo "✅ LXTerminal found, launching LXTerminal inside Xpra display..."
else
    echo "❌ LXTerminal not found / autostart failed."
    echo "⚠ Please open terminal as directed once you opened the virtual desktop:"
    echo "🖱 Click Start -> System Tools -> LXTerminal"
fi

echo "----------------------------------------------"
echo " 🖥️ Starting Xpra display on $XPRA_DISPLAY ..."
echo "----------------------------------------------"

export DISPLAY=$XPRA_DISPLAY
xpra start $XPRA_DISPLAY \
  --bind-tcp=0.0.0.0:8080 \
  --html=on \
  --opengl=yes \
  --input-method=raw \
  --exit-with-children \
  --start-child=$TERMINAL

# Give Xpra & Minecraft some time to boot
sleep 15

# ---------------------------------
# 3. Start Sunshine capturing Xpra
# ---------------------------------
echo "----------------------------------------------"
echo "🌞 Starting Sunshine (capturing $DISPLAY)..."
echo "----------------------------------------------"
DISPLAY=$XPRA_DISPLAY nohup ~/sunshine/build/sunshine > /tmp/sunshine.log 2>&1 &

# -----------------------------
# 4. Done
# -----------------------------

echo "\n"
echo "----------------------------------------------"
echo "✅ Setup complete!"
echo "👉 Open Codespaces port $XPRA_PORT for Xpra web desktop."
echo "👉 Sunshine is running on port 47989, capturing display $XPRA_DISPLAY."
echo "👉 Logs: /tmp/sunshine.log"
echo "----------------------------------------------"
echo "Once you opened Xpra port 8080, if LXTerminal is not already opened, Click Start -> System Tools -> LXTerminal."
echo "Then enter the following commands:"
echo "cd Forge-Project-1.20.X"
echo "./gradlew runClient"
echo "----------------------------------------------"