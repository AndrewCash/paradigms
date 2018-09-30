#!/bin/bash
set -e -x

echo "Compiling..."
javac Game.java View.java Controller.java Model.java Json.java Sprite.java Brick.java Mario.java Coin.java
echo "Done."
