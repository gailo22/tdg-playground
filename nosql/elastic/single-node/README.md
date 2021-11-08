# single-node cluster

### install elasticsearch
```
$ docker network create elastic
$ docker pull docker.elastic.co/elasticsearch/elasticsearch:7.15.1
$ docker run --name es01-test --net elastic -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.15.1

```

### install kibana
```
$ docker pull docker.elastic.co/kibana/kibana:7.15.1
$ docker run --name kib01-test --net elastic -p 5601:5601 -e "ELASTICSEARCH_HOSTS=http://es01-test:9200" docker.elastic.co/kibana/kibana:7.15.1

```

### testing

```
$ curl -X GET http://localhost:9200/
```