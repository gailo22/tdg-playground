# DAPR - Distributed Application Runtime

### install
```
$ brew install dapr/tap/dapr-cli
$ dapr init
$ dapr --version

$ docker ps
CONTAINER ID   IMAGE               COMMAND                  CREATED             STATUS                       PORTS                                                 NAMES
807ed824eb22   openzipkin/zipkin   "start-zipkin"           About an hour ago   Up About an hour (healthy)   9410/tcp, 0.0.0.0:9411->9411/tcp, :::9411->9411/tcp   dapr_zipkin
2fd1514cdf05   daprio/dapr:1.4.3   "./placement"            About an hour ago   Up About an hour             0.0.0.0:50005->50005/tcp, :::50005->50005/tcp         dapr_placement
5950e88264cc   redis               "docker-entrypoint.s…"   About an hour ago   Up About an hour             0.0.0.0:6379->6379/tcp, :::6379->6379/tcp             dapr_redis

$ ls $HOME/.dapr
bin         components  config.yaml

```
