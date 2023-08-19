#!/bin/bash
cwd=$(pwd)
tput setaf 1; tput setab 7; echo “Installing LIB NICE”;
tput sgr0;
sudo apt install libnice-dev
tput setaf 1; tput setab 7; echo “Installing LIB SRTP”;
tput sgr0;
cd libsrtp-2.2.0
./configure --prefix=/usr --enable-openssl
make shared_library && sudo make install
cd $cwd;
tput setaf 1; tput setab 7; echo “Installing USRSCTP”;
tput sgr0;
cd usrsctp
git checkout tags/0.9.5.0
./bootstrap
./configure --prefix=/usr && make && sudo make install
cd $cwd;
tput setaf 1;tput setab 7; echo “Installing LIB WEB SOCKETS”;
tput sgr0;
cd libwebsockets
git checkout v4.3-stable
mkdir build
cd build
cmake -DLWS_MAX_SMP=1 -DLWS_WITHOUT_EXTENSIONS=0 -DCMAKE_INSTALL_PREFIX:PATH=/usr -DCMAKE_C_FLAGS="-fpic" ..
make && sudo make install
cd $cwd;
tput setaf 1; tput setab 7; echo “Installing PAHO MQTT”;
tput sgr0;
cd paho.mqtt.c
sudo prefix=/usr make install
cd $cwd;
tput setaf 1; tput setab 7; echo “Installing Rabbit MQ”;
tput sgr0;
cd rabbitmq-c
git submodule init
git submodule update
mkdir build && cd build
cmake -DCMAKE_INSTALL_PREFIX=/usr ..
make && sudo make install
cd $cwd;
tput setaf 1; tput setab 7; echo “Installing Nano Msg”;
tput sgr0;
sudo apt-get update
sudo apt-get install libnanomsg-dev