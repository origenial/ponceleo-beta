#!/usr/bin/env bash
set -e

# cleanup output dir
rm -rf resources/public/out

# generate full tailwind css
find src/**/ -name '*.scss' -exec sh -c 'NODE_ENV=production npx tailwind build $0  --output resources/public/out/$(basename $0 .scss).css' {} \;

# minify css
find resources/public/out -name '*.css' -exec sh -c 'npx cleancss -o $0 $0' {} \;

# build cljs in advanced mode
lein do clean, uberjar

# cleanup cljs-runtime (not required)
rm -rf resources/public/out/cljs-runtime