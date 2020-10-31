#!/usr/bin/env bash

BIN_DIR=$(dirname $(dirname $(realpath $0)));
source "$BIN_DIR/config.sh";

rm -rf "$IMG_TARGET_DIR";
mkdir -p "$IMG_TARGET_DIR";
cp -R "$IMG_SRC_DIR/." "$IMG_TARGET_DIR";
