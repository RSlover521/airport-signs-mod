#!/usr/bin/env bash
set -e

echo "Building Mod..."
./gradlew build

echo "Running RunClient..."
./gradlew runClient