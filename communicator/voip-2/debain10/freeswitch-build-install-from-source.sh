#!/bin/bash
## Script for building/installing FreeSWITCH from source.
## URL: https://gist.github.com/mariogasparoni/2f21fd03afa27beae656b70ff7826f24
## Freely distributed under the MIT license
##
##
set -xe
FREESWITCH_SOURCE=https://github.com/signalwire/freeswitch.git
FREESWITCH_RELEASE=master #or set this to any other version, for example: v1.10.5
PREFIX=/usr/share/freeswitch

#Clean old prefix and build
sudo rm -rf $PREFIX
rm -rf ~/build-$FREESWITCH_RELEASE

#install dependencies
sudo apt-get update && sudo apt-get install -y git-core build-essential python python2-dev python3-dev autoconf automake libtool libncurses5 libncurses5-dev make libjpeg-dev pkg-config zlib1g-dev sqlite3 libsqlite3-dev libpcre3-dev libspeexdsp-dev libedit-dev libldns-dev liblua5.1-0-dev libcurl4-gnutls-dev libapr1-dev yasm libsndfile-dev libopus-dev libtiff-dev libavformat-dev libswscale-dev libavresample-dev libpq-dev zip

#clone source and prepares it
mkdir -p ~/build-$FREESWITCH_RELEASE
cd ~/build-$FREESWITCH_RELEASE

PVERSION=( ${FREESWITCH_RELEASE//./ } )
MIN_VERSION=${PVERSION[1]}
PATCH_VERSION=${PVERSION[2]}

if [[ $FREESWITCH_RELEASE = "master" ]] || [[ $MIN_VERSION -ge 10  &&  $PATCH_VERSION -ge 3 ]]
then
    echo "VERSION => 1.10.3 - need to build spandsp and sofia-sip separatedly"

    #build and install libspandev
    git clone https://github.com/freeswitch/spandsp.git
    cd spandsp
    ./bootstrap.sh
    ./configure --prefix=$PREFIX
    make
    sudo make install
    cd ..
    
    #build and install mod_sofia
    git clone https://github.com/freeswitch/sofia-sip.git
    cd sofia-sip
    ./bootstrap.sh
    ./configure --prefix=$PREFIX
    make
    sudo make install
    cd ..
fi

#avoid git access's denied error
touch .config && sudo chown $USER:$USER .config
if [ ! -d freeswitch ]
then
    git clone $FREESWITCH_SOURCE freeswitch
    cd freeswitch
else
    cd freeswitch
    git fetch origin
fi
git reset --hard $FREESWITCH_RELEASE && git clean -d -x -f

#remove mod_signalwire from building
sed -i "s/applications\/mod_signalwire/#applications\/mod_signalwire/g" build/modules.conf.in
sed -i "s/databases\/mod_pgsql/#databases\/mod_pgsql/g" build/modules.conf.in

./bootstrap.sh

#configure , build and install
env PKG_CONFIG_PATH=$PREFIX/lib/pkgconfig ./configure --prefix=$PREFIX --disable-libvpx
env C_INCLUDE_PATH=$PREFIX/include make
sudo make install config-vanilla

#package
cd ~/build-$FREESWITCH_RELEASE
zip -r freeswitch-$FREESWITCH_RELEASE.zip $PREFIX
