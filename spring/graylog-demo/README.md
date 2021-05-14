# Graylog

### Setup multi-node graylog using docker
```
$ docker run --name mongo -d mongo:4.2
$ docker run --name elasticsearch \
    -e "http.host=0.0.0.0" \
    -e "discovery.type=single-node" \
    -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
    -d docker.elastic.co/elasticsearch/elasticsearch-oss:7.10.2

$ docker run --link mongo --link elasticsearch --name graylog-master \
    -p 9000:9000 -p 12201:12201 -p 1514:1514 -p 5555:5555 \
    -e GRAYLOG_HTTP_EXTERNAL_URI="http://127.0.0.1:9000/" \
    -e GRAYLOG_IS_MASTER="true" \
    -d graylog/graylog:4.0

$ docker run --link mongo --link elasticsearch --name graylog-1 \
    -p 9001:9000 -p 12202:12201 -p 1515:1514 -p 5556:5555 \
    -e GRAYLOG_HTTP_EXTERNAL_URI="http://127.0.0.1:9001/" \
    -e GRAYLOG_IS_MASTER="false" \
    -d graylog/graylog:4.0


$ echo 'Second log message' | nc localhost 5555
```
