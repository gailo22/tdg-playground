# Seat Service Saga Orchestrator

## prerequisite
- kafka
- docker mysql
- create mysql db and table using files in script directory
- start eventuate-tram-cdc-mysql-service jar file

## start kafka and zookeeper
```
$ bin/zookeeper-server-start.sh config/zookeeper.properties
$ bin/kafka-server-start.sh config/server-0.properties
```

### kafdrop
```
$ java -jar kafdrop-3.28.0-SNAPSHOT.jar --kafka.brokerConnect=localhost:9092
```

### CDC MySQL
```shell
$ cd cdc-mysql
$ java -jar eventuate-tram-cdc-mysql-service-0.21.3.RELEASE.jar --spring.config.location=application.properties
```

### testing
```shell
$ curl -XPOST -H "Accept: application/json" -H "Content-Type: application/json" -d'{"seatId": "A001", "customerId": "S9001"}' localhost:8088/book-seat
{"bookingId":"f6d47b01-8ba4-4399-9d8b-76d92a0fefd4","seatId":"A001"}
```