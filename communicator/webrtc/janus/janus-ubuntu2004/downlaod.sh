#!/bin/bash
# Core Dependencies
sudo apt-get install git
sudo apt-get install build-essential
sudo apt-get install g++
sudo apt-get install libmicrohttpd-dev
sudo apt-get install libjansson-dev
sudo apt-get install libssl-dev
sudo apt-get install libsrtp-dev
sudo apt-get install libsofia-sip-ua-dev
sudo apt-get install libglib2.0-dev
sudo apt-get install libopus-dev
sudo apt-get install libogg-dev
sudo apt-get install libcurl4-openssl-dev
sudo apt-get install liblua5.3-dev
sudo apt-get install libconfig-dev
sudo apt-get install pkg-config
sudo apt-get install gengetopt
sudo apt-get install libtool
sudo apt-get install automake
sudo apt-get install gtk-doc-tools
sudo apt-get install cmake
#libnice
git clone https://gitlab.freedesktop.org/libnice/libnice
# libsrtp
wget https://github.com/cisco/libsrtp/archive/v2.2.0.tar.gz
tar xfv v2.2.0.tar.gz
#usrsctp
git clone https://github.com/sctplab/usrsctp
#libwebsockets
git clone https://github.com/warmcat/libwebsockets.git
# mqtt
git clone https://github.com/eclipse/paho.mqtt.c.git
#rabbitMQ
git clone https://github.com/alanxz/rabbitmq-c