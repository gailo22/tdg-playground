VoIP install and cofiguration
* FreeSwitch ip: 192.168.1.19 -> centos7
* Kamailio ip: 192.168.1.20 -> centos7
* RTPEngine ip: 192.168.1.21 -> centos8
* CoTurn ip: 192.168.1.22 -> centos8
* Homer ip: 192.168.1.23 -> centos8

### vagrant
- centos7
  - freeswitch-vm
  - rtpengine-vm
- centos8
  - kamailio-vm
  - coturn-vm
  - homer-vm

## freeswitch

```
$ fs_cli
$ tail -f /var/log/freeswitch/freeswitch.log
```


## rtpengin
```
$ echo "OPTIONS=\"-i 192.168.1.21 -n 192.168.1.21:5050 -m 10000 -M 60000 -L 4 --log-facility=local1 --table=8 --delete-delay=0 --timeout=10 --silent-timeout=600 --final-timeout=7200 –offer-timeout=60 --num-threads=12 --tos=184 –no-fallback\"" > /etc/sysconfig/rtpengine
```

## coturn - centos 8
```
$ dnf install epel-release epel-next-release
$ dnf --enablerepo="epel" install coturn
```

### command line
```
$ journalctl -exlf -u rtpengine
$ journalctl -exlf -u kamailio

```

### reference
* https://blog.kolmisoft.com/rtpengine-install-on-centos-7/
* https://github.com/sipwise/rtpengine/wiki/Install-RTPEngine-on-Centos-7 
* https://sillycodes.com/install-sngrep-centos-7-yum-package/
* https://thesumof.it/blog/2019-01-14-vagrant-instance-names 
* https://blog.kolmisoft.com/homer-install-on-centos-8/
* https://www.informaticar.net/install-turn-server-for-synapse-matrix-on-centos-rhel/