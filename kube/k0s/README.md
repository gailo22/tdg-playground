# K0S

### start vagrant with virtualbox provider
```
$ vagrant up
```

## installation
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

## deploy sample nginx
```
$ sudo k0s kubectl create deploy nginx --image nginx
$ sudo k0s kubectl get pods

```
