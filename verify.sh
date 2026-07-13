#!/usr/bin/env bash
#
# Local check: compile the plugin and run the unit tests.
# No Minecraft server is started.
#
# Requirements: Maven 3.9+ and a JDK 21 on PATH
# Usage: ./verify.sh
#
set -euo pipefail

cd "$(dirname "$0")"

echo "==> Compiling and running tests (mvn clean verify)"
mvn -B -ntp clean verify

echo
echo "==> Done. Build compiled and all tests passed."
