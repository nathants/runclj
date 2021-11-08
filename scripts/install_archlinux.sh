#!/bin/bash
set -eou pipefail
sudo pacman -Sy --noconfirm \
     python3
     npm
cd /tmp
npm -g install http-server
rm -rf runclj
git clone https://github.com/nathants/runclj
sudo mv runclj/bin/* runclj/bin/.shadow-cljs/ /usr/local/bin
