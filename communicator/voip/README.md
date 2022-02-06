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

### freeswitch

```
$ yum install -y https://files.freeswitch.org/repo/yum/centos-release/freeswitch-release-repo-0-1.noarch.rpm epel-release
$ yum install -y freeswitch-config-vanilla freeswitch-lang-* freeswitch-sounds-*
$ systemctl enable freeswitch
$ fs_cli -rRS
$ tail -f /var/log/freeswitch/freeswitch.log
```


### rtpengin
```
$ echo "OPTIONS=\"-i 192.168.1.21 -n 192.168.1.21:5050 -m 10000 -M 60000 -L 4 --log-facility=local1 --table=8 --delete-delay=0 --timeout=10 --silent-timeout=600 --final-timeout=7200 –offer-timeout=60 --num-threads=12 --tos=184 –no-fallback\"" > /etc/sysconfig/rtpengine
```

### coturn - centos 8
```
$ dnf install epel-release epel-next-release
$ dnf --enablerepo="epel" install coturn
```
### sngrep

#### centos 7
```
$ vim /etc/yum.repos.d/sngrep.repo

[irontec]
name=Irontec RPMs repository
baseurl=http://packages.irontec.com/centos/$releasever/$basearch/

$ rpm --import http://packages.irontec.com/public.key
$ yum update
$ yum install sngrep
```

#### centos 8
```
$ dnf install epel-release epel-next-release
$ rpm -ivh http://repo.okay.com.mx/centos/8/x86_64/release/okay-release-1-5.el8.noarch.rpm
$ dnf install sngrep
```

### configuration

#### homer - sipcapture

***autoload_configs/sofia.conf.xml***
```
<param name="capture-server" value="udp:192.168.1.23:9060;hep=3;capture_id=100"/>
```

***sip_profiles/internal.xml***
```
<param name="sip-capture" value="yes"/>
```

***fs_cli console***
```
freeswitch@freeswitch> sofia global capture on
+OK Global capture on
freeswitch@freeswitch> sofia profile internal capture on
Enabled sip capturing on internal
```

***B2BUA Correlation***
```
<action application="set" data="sip_h_X-cid=${sip_call_id}"/>
```

***heplify-server***
```
vim /etc/heplify-server/heplify-server.toml

AlegIDs = ["X-CID","P-Charging-Vector,icid-value=\"?(.*?)(?:\"|;|$)","X-BroadWorks-Correlation-Info"]
```

***hepgen.js***
```
[root@homer hepgen.js]# ./hepgen.js -s 192.168.1.23 -p 9060 -c "./config/b2bcall_rtcp.js"
```


### command line
```
$ journalctl -exlf -u rtpengine
$ journalctl -exlf -u kamailio

```

### vagrant command
```
$ vagrant up
$ vagrant ssh freeswitch-vm
$ vagrant destroy freeswitch vm & vagrant up
$ vagrant vbguest --status

### reference
* https://blog.kolmisoft.com/rtpengine-install-on-centos-7/
* https://github.com/sipwise/rtpengine/wiki/Install-RTPEngine-on-Centos-7 
* https://sillycodes.com/install-sngrep-centos-7-yum-package/
* https://thesumof.it/blog/2019-01-14-vagrant-instance-names 
* https://blog.kolmisoft.com/homer-install-on-centos-8/
* https://www.informaticar.net/install-turn-server-for-synapse-matrix-on-centos-rhel/
* https://freeswitch.org/confluence/display/FREESWITCH/CentOS+7+and+RHEL+7#CentOS7andRHEL7-CentOS7andRHEL7-Stable