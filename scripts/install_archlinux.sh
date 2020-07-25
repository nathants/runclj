#!/bin/bash
set -eou pipefail
sudo pacman -Sy --noconfirm \
     leiningen \
     npm
cd /tmp
rm -rf runclj
git clone https://github.com/nathants/runclj
sudo mv runclj/bin/* runclj/bin/.lein/ /usr/local/bin
