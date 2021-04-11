#!/usr/bin/env bash

set -x;

MODULE_DIR=$(dirname $(dirname $(realpath $0)));
source "$MODULE_DIR/.env.local";

# Variable that should be set :
# SSH_PORT, SSH_USER, SSH_TARGET, SSH_DIRECTORY

# Cleaning up dev files
rm -rf "$MODULE_DIR/dist/public/js/out/cljs-runtime"

rsync --recursive \
      --links \
      --partial --progress \
      --verbose \
      -e "ssh -p ${SSH_PORT}" \
      --rsync-path=/usr/bin/rsync \
      --delete \
      --info=progress0,stats \
      dist/public/* \
      ${SSH_USER}@${SSH_TARGET}:${SSH_DIRECTORY};
