#!/usr/bin/env bash

BIN_DIR=$(dirname $(dirname $(realpath $0)));
source "$BIN_DIR/config.sh";

# generate dev css from sources
postcss "$SCSS_SRC_DIR/*.{s,}css" --base "$SCSS_SRC_DIR" --ext css --dir "$CSS_TARGET_DIR" --watch --verbose