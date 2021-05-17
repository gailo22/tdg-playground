# Installation using docker

```
$ docker network create --subnet=192.168.0.0/16 rabbit-network

$ docker run -d -h node1.rabbit \
           --net rabbit-network \
           --ip 192.168.0.10 \
           --name rabbit-node1 \
           --add-host node2.rabbit:192.168.0.11 \
           -p 4369:4369 \
           -p 5672:5672 \
           -p 15672:15672 \
           -p 25672:25672 \
           -p 35672:35672 \
           -e RABBITMQ_USE_LONGNAME="true" \
           -e RABBITMQ_ERLANG_COOKIE="cookie" \
           rabbitmq:3-management

$ docker run -d -h node2.rabbit \
           --net rabbit-network --ip 192.168.0.11 \
           --name rabbit-node2 \
           --add-host node1.rabbit:192.168.0.10 \
           -p "4370:4369" \
           -p "5673:5672" \
           -p "15673:15672" \
           -p "25673:25672" \
           -p "35673:35672" \
           -e RABBITMQ_USE_LONGNAME="true" \
           -e RABBITMQ_ERLANG_COOKIE="cookie" \
           rabbitmq:3-management
           
$ docker exec rabbit-node2 rabbitmqctl stop_app

$ docker exec rabbit-node2 rabbitmqctl join_cluster rabbit@node1.rabbit

$ docker exec rabbit-node2 rabbitmqctl start_app

$ docker exec rabbit-node1 rabbitmqctl cluster_status

```

### HA 

```
$ docker exec rabbit-node1 rabbitmqctl set_policy ha "." '{"ha-mode":"all"}'
```


### Nginx
```
$ cd ha/nginx
$ docker build -t nginx-rabbit .
$ docker run -d -h nginx.rabbit \
    --net rabbit-network \
    --name nginx-rabbit \
    -p 5000:5000 \
    -p 15000:15000 \
    nginx-rabbit

```
