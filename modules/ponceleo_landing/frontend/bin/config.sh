#!/usr/bin/env bash

ROOT_DIR=$(dirname $(dirname ${BASH_SOURCE[0]}));
SCSS_SRC_DIR="$ROOT_DIR/src/resources/style";
CSS_TARGET_DIR="$ROOT_DIR/target/public/css";

IMG_SRC_DIR="$ROOT_DIR/src/resources/img";
IMG_TARGET_DIR="$ROOT_DIR/target/public/img";

set -e;
set -x;
