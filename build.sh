#!/bin/bash

# Verify that the javac command is in path
if ! [ -x "$(command -v javac)" ]; then
  echo "Error: Command 'javac' not in path." >&2
  exit 2
fi

# Verify that the jar command is in path
if ! [ -x "$(command -v jar)" ]; then
  echo "Error: Command 'jar' not in path." >&2
  exit 2
fi

# Create Temporary Directory
mkdir tmp

# Compile StickRPG 1.5 Files
echo "Compiling StickRPG 1.5 classes..."
cp -R src/* tmp/
cp -R res/* tmp/
cd tmp/
find . -name "*.java" > sources.txt
javac -cp ../lib/lib-jtwod-static.jar @sources.txt >/dev/null 2>&1
rm sources.txt

file="StickRPG_1.5.jar"
echo "Removing source..."
find . -name '*.java' -delete

# Extract TinySound library and License
echo "Migrating JTwoD Library..."
jar -xf ../lib/lib-jtwod-static.jar
rm -rf META-INF
cd ..

# Compile final Jar
echo "Compiling final jar..."
jar -cvfe $file stickrpg.game.StickRPG -C tmp/ . >/dev/null

# Clean up
echo "Cleaning up..."
rm -rf tmp

echo "Done."
echo "Output: $file"
