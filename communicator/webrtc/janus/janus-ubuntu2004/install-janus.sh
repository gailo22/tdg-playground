#!/bin/bash
#get the source from git
git clone https://github.com/meetecho/janus-gateway.git
cd janus-gateway
# generates the configuration file
sh autogen.sh
./configure --prefix=/opt/janus
# compile and install
sudo make
sudo make install
# copy default config
sudo make configs