# K0S

### start vagrant with libvirt provider
```
$ vagrant up --provider libvirt
```

## installation - setup kubernetes using k0s
```
$ curl -sSLf https://get.k0s.sh | sudo sh 
$ sudo systemctl list-unit-files | grep k0s
$ sudo k0s install controller --single
$ sudo ls /var/lib/k0s
$ sudo k0s start
$ sudo k0s status

$ sudo k0s kubectl get nodes -o wide
$ sudo k0s kubectl get pods -n kube-system -o wide

```

## deploy a sample nginx
```
$ sudo k0s kubectl create deploy nginx --image nginx
$ sudo k0s kubectl get pods

```


## install metallb
```
$ k0s kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.12.1/manifests/namespace.yaml
$ k0s kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.12.1/manifests/metallb.yaml

$ k0s kubectl get all -n metallb-system

```

## expose the nginx deployment as load balancer 
```
$ ip a s eth0
eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UP group default qlen 1000
    link/ether 52:54:00:69:c2:10 brd ff:ff:ff:ff:ff:ff
    inet 192.168.121.180/24 brd 192.168.121.255 scope global dynamic eth0
       valid_lft 2253sec preferred_lft 2253sec
    inet6 fe80::5054:ff:fe69:c210/64 scope link 
       valid_lft forever preferred_lft forever

$ sipcalc 192.168.121.180/24
-[ipv4 : 192.168.121.180/24] - 0

[CIDR]
Host address		- 192.168.121.180
Host address (decimal)	- 3232266676
Host address (hex)	- C0A879B4
Network address		- 192.168.121.0
Network mask		- 255.255.255.0
Network mask (bits)	- 24
Network mask (hex)	- FFFFFF00
Broadcast address	- 192.168.121.255
Cisco wildcard		- 0.0.0.255
Addresses in network	- 256
Network range		- 192.168.121.0 - 192.168.121.255
Usable range		- 192.168.121.1 - 192.168.121.254


$ vim metallb.yaml

apiVersion: v1
kind: ConfigMap
metadata:
  namespace: metallb-system
  name: config
data:
  config: |
    address-pools:
    - name: default
      protocol: layer2
      addresses:
      - 192.168.212.240-192.168.212.250

$ k0s kubectl apply -f metallb.yaml

$ k0s kubectl expose deploy/nginx --port=80 --type LoadBalancer

$ k0s kubectl get svc
NAME         TYPE           CLUSTER-IP     EXTERNAL-IP       PORT(S)        AGE
kubernetes   ClusterIP      10.96.0.1      <none>            443/TCP        29m
nginx        LoadBalancer   10.98.187.54   192.168.212.240   80:32189/TCP   18s
```

$ curl 192.168.212.240
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
html { color-scheme: light dark; }
body { width: 35em; margin: 0 auto;
font-family: Tahoma, Verdana, Arial, sans-serif; }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>


## reference
* https://docs.k0sproject.io/v1.21.2+k0s.1/install/
* https://metallb.universe.tf/installation/