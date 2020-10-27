#!/usr/bin/env bash
set -e

# cleanup output dir
rm -rf resources/public/out
# build minified assets
NODE_ENV=production postcss src/style/*.{s,}css --base src/style --ext css --dir resources/public/out --verbose