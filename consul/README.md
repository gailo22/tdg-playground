# consul configuration

```
$ consul agent -dev -node=machine

$ consul members -detailed
```

## run with config
```
$ consul agent -dev -config-dir=./consul.d -node=machine

```

## check
```
$ dig @127.0.0.1 -p 8600 web.service.consul

$ dig @127.0.0.1 -p 8600 web.service.consul SRV
```

## service mesh
```
$ socat -v tcp-l:8181,fork exec:"/bin/cat"

$ consul connect proxy -sidecar-for socat

$ consul connect proxy -service web -upstream socat:9191

$ nc 127.0.0.1 9191

$ consul connect proxy -sidecar-for web

$ nc 127.0.0.1 9191

```


## deny 
```
$ consul intention create -deny web socat
$ nc 127.0.0.1 9191
$ consul intention delete web socat
```