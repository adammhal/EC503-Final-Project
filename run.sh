#!/usr/bin/env bash
set -euo pipefail

pkill -f 'java RBServer'        2>/dev/null || true     
pkill -f 'http\.server 3000'    2>/dev/null || true   

javac *.java
java RBServer &                 # listens on http://localhost:8080/tree

python3 -m http.server 3000 --directory frontend &

sleep 1
open http://localhost:3000/index.html