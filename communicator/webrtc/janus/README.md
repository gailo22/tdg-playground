# janus-gateway intallation on ubuntu 20.04 LTS

$ root@janus-ubuntu-2004:~/usrsctp# cat /etc/os-release
NAME="Ubuntu"
VERSION="20.04.5 LTS (Focal Fossa)"
ID=ubuntu
ID_LIKE=debian
PRETTY_NAME="Ubuntu 20.04.5 LTS"
VERSION_ID="20.04"
HOME_URL="https://www.ubuntu.com/"
SUPPORT_URL="https://help.ubuntu.com/"
BUG_REPORT_URL="https://bugs.launchpad.net/ubuntu/"
PRIVACY_POLICY_URL="https://www.ubuntu.com/legal/terms-and-policies/privacy-policy"
VERSION_CODENAME=focal
UBUNTU_CODENAME=focal

### update dependencies

```
$ apt update
$ bash install_dependencies.sh
```

### install libsrtp
```
$ bash install_libsrtp.sh

```

### install libnice
```
$ apt install meson
$ bash install_libnice.sh
```

### install usrsctp
```
$ bash install_usrsctp.sh

(optional)
$ libtool --finish /usr/lib64
$ apt install libusrsctp-dev
```

### install libwebsockets
```
$ bash install_libwebsockets.sh

(optional)
$ libtool --finish /opt/janus/lib/janus/transports

```

### install mqtt (optional)
```
$ bash install_mqtt.sh
```

### install nanomsg (optional)
```
$ apt install libnanomsg-dev
```

### install RabbitMQ C AMQP (optional)
```
$ bash install_rabbitmqc.sh

```

```
$ echo "export PKG_CONFIG_PATH=/usr/lib/pkgconfig:/usr/lib64/pkgconfig" >> ~/.bashrc
$ echo "export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/lib64" >> ~/.bashrc
$ source ~/.bashrc
```

### Compiling the Janus Gateway

```
$ git clone https://github.com/meetecho/janus-gateway.git
$ cd janus-gateway
$ sh autogen.sh
$ ./configure --prefix=/opt/janus
OR
# $ ./configure --prefix=/opt/janus --disable-data-channels --disable-rabbitmq --disable-mqtt

$ make
$ make install
$ make configs

```

### installed

 location
 - /opt/janus
 - /opt/janus/etc/janus  - configs


sudo vi /opt/janus/etc/janus/janus.jcfg

add:
log_to_file = "/var/log/janus/janus.log


### testing janus
```
$ /opt/janus/bin/janus --help
```


### create as a service
```
$ vi /etc/systemd/system/webrtcserver.service

[Unit]
Description=My Janus WebRTC Server
After=network.target

[Service]
User=root
Nice=1
Restart=on-abnormal
LimitNOFILE=65536
# If you need to specify the current working directory of the
WorkingDirectory=/opt/janus/share/janus/recordings
ExecStart=/usr/bin/sudo /opt/janus/bin/janus

[Install]
WantedBy=multi-user.target

```

### add libs
```
$ vi /etc/ld.so.conf.d/libc.conf

add /usr/lib64 to the list

$ ldconfig
```

```
$ systemctl daemon-reload
$ systemctl start webrtcserver
$ systemctl enable webrtcserver

```


### echo test
```
$ cd janus-gateway/test
$ apt install python3-pip


$ apt -y install python3-pip 
$ pip3 install PyOpenSSL


$ pip3 install setuptools websockets aiortc

$ python3 echo.py ws://localhost:8188/ --verbose
$ python3 echo.py ws://localhost:8188/ --play-from media_file --verbose
```

### install netstat
```
$ apt update
$ apt install net-tools
```

### start localhost webserver
```
$ cd janus-gateway/html
$ python3 -m http.server 8000

```

### references
 - https://ourcodeworld.com/articles/read/1197/how-to-install-janus-gateway-in-ubuntu-server-18-04
 - https://github.com/meetecho/janus-gateway
 - https://gitlab.freedesktop.org/libnice/libnice
 - https://github.com/sctplab/usrsctp/blob/master/Manual.md
 - https://ourcodeworld.com/articles/read/1263/how-to-configure-the-janus-webrtc-server-as-a-service-with-systemd-in-ubuntu-18-04

