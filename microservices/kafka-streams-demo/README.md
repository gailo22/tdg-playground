# Kafka Streams State Store

```shell
$ ./kafka-run.sh
```

### list created topics
```shell
$  docker run --net=confluent --rm \
	confluentinc/cp-kafka \
	kafka-topics --bootstrap-server kafka:9092 --list

```

### consumer
```shell
$  docker run --net=confluent --rm \
	confluentinc/cp-kafka \
	kafka-console-consumer --bootstrap-server kafka:9092 --topic call-store-topic

```

### producer
```shell
$  docker run --net=confluent --rm \
    -i \
	confluentinc/cp-kafka \
	kafka-console-producer --broker-list kafka:9092 --topic call-store-topic

```


### producer without docker
```shell
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic PrintRequest --property "parse.key=true" --property "key.separator=:"

```
