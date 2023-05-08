# install freeswitch on debian 10


## edit config

```
$ vi /etc/freeswitch/autoload_configs/event_socket.conf.xml

change listen-ip to 127.0.0.1 and then restart the freeswitch

```

## test verto websocket
```
$ wscat --no-check -c wss://192.168.1.30:8082
Connected (press CTRL+C to quit)

> {"jsonrpc":"2.0","method":"login","id":1,"params":{"login":"1000","loginParams":{},"userVariables":{},"passwd":"1234","sessid":"6B3DC5AB-58E0-4375-82C1-E1A98ACBA5ED"}}
```

## use verto client
```
$ git clone https://github.com/freeswitch/verto-client.git
$ cd verto-client/verto_communicator
$ ./debian8-install.sh
$ grunt serve

```

open browser url: https://192.168.1.44:9001 and login
try dial number 3000

### references
* https://blog.kolmisoft.com/freeswitch-1-10-installation-on-debian-10/
* https://developer.signalwire.com/freeswitch/FreeSWITCH-Explained/Installation/Linux/Deprecated-Installation-Instructions/25460805/
