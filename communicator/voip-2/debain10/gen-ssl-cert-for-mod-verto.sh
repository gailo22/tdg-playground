#!/bin/bash
CONFDIR=/etc/freeswitch
FQDN=192.169.1.30
mkdir $CONFDIR/certs
mkdir $CONFDIR/tls
chown freeswitch:freeswitch $CONFDIR/certs $CONFDIR/tls
wget http://files.freeswitch.org/downloads/ssl.ca-0.1.tar.gz
tar zxfv ssl.ca-0.1.tar.gz
cd ssl.ca-0.1/
perl -i -pe 's/md5/sha1/g' *.sh
perl -i -pe 's/2048/2048/g' *.sh
./new-root-ca.sh
./new-server-cert.sh $FQDN
./sign-server-cert.sh $FQDN
cat $FQDN.crt $FQDN.key > $CONFDIR/certs/wss.pem
cat $FQDN.crt > $CONFDIR/tls/dtls-srtp.crt
chown freeswitch:freeswitch $CONFDIR/certs/wss.pem $CONFDIR/tls/dtls-srtp.crt

