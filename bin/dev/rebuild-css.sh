#!/usr/bin/env bash

# cleanup output dir
rm -rf resources/public/out
# generate full tailwind css
postcss src/style/*.{s,}css --base src/style --ext css --dir resources/public/out