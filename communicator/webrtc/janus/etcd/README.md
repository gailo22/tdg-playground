### commands
```
$ docker-compose up -d
$ curl -L http://127.0.0.1:2379/version

$ curl -L http://localhost:2379/v3/kv/put \
  -X POST -d '{"key": "Zm9v", "value": "YmFy"}'
  
$ curl -L http://localhost:2379/v3/kv/put \
-X POST -d '{"key": "bG9nbGV2ZWw=", "value": "aW5mbw=="}'

```


### references
- https://betterprogramming.pub/organize-your-application-configuration-efficiently-with-etcd-89dad3fa3dcf
