#!/usr/bin/env bash

set -x;

MODULE_DIR=$(dirname $(dirname $(realpath $0)));
source "$MODULE_DIR/.env.local";

# Variable that should be set :
# SSH_PORT, SSH_USER, SSH_TARGET, SSH_DIRECTORY

rsync --recursive \
      --links \
      --partial --progress \
      --verbose \
      -e "ssh -p ${SSH_PORT}" \
      --rsync-path=/usr/bin/rsync \
      --delete \
      --info=progress0,stats \
      target/* \
      ${SSH_USER}@${SSH_TARGET}:${SSH_DIRECTORY}/exec;

rsync --verbose \
      -e "ssh -p ${SSH_PORT}" \
      --rsync-path=/usr/bin/rsync \
      --info=progress0,stats \
      env/secret.config.edn \
      ${SSH_USER}@${SSH_TARGET}:${SSH_DIRECTORY}/env;
