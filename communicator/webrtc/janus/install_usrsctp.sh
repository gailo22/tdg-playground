#!/bin/bash

# install_usrsctp.sh
git clone https://github.com/sctplab/usrsctp
cd usrsctp
git checkout tags/0.9.5.0
./bootstrap
./configure --prefix=/usr && make && sudo make install