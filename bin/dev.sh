#!/usr/bin/env bash
set -e

if [ "$1" != "skip-css" ]
then
	# cleanup output dir
	rm -rf resources/public/out
	# generate full tailwind css
	find src/**/ -name '*.scss' -exec sh -c 'postcss $0  -o resources/public/out/$(basename $0 .scss).dev.css' {} \;
fi

if [ "$1" != "only-css" ]
then
  # start figwheel
  lein figwheel
fi