# multi-node cluster

```
$ docker-compose up -d

$ curl -X GET "localhost:9200/_cat/nodes?v=true&pretty"
ip         heap.percent ram.percent cpu load_1m load_5m load_15m node.role   master name
172.19.0.3           46          79  64    2.75    1.21     0.45 cdfhilmrstw *      es01
172.19.0.4           44          79  64    2.75    1.21     0.45 cdfhilmrstw -      es03
172.19.0.2           46          79  63    2.75    1.21     0.45 cdfhilmrstw -      es02
```

### install kibana
```
$ docker run --name kib01 --net multi-node_elastic -p 5601:5601 -e "ELASTICSEARCH_HOSTS=http://es01:9200" docker.elastic.co/kibana/kibana:7.15.1

```