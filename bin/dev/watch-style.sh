#!/usr/bin/env bash

# generate dev css from sources
postcss src/style/*.{s,}css --base src/style --ext css --dir resources/public/out --watch --verbose