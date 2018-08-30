#!/bin/bash
set -e -x

echo "Compiling..."
javac Game.java View.java Controller.java Model.java Brick.java Json.java

echo "Done."
