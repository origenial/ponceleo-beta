#!/usr/bin/env bash

BIN_DIR=$(dirname $(dirname $(realpath $0)));
source "$BIN_DIR/config.sh";

# cleanup output dir
rm -rf "$CSS_TARGET_DIR"
# generate full tailwind css
postcss "$SCSS_SRC_DIR/*.{s,}css" --base "$SCSS_SRC_DIR" --ext css --dir "$CSS_TARGET_DIR"