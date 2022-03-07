## saga with go


### start kafka docker
```
$ cd deploy
$ docker-compose up -d

create a topic 'saga'
$ docker exec broker \
kafka-topics --bootstrap-server broker:9092 \
             --create \
             --topic saga

write message
$ docker exec -it broker \
kafka-console-producer --bootstrap-server broker:9092 \
                       --topic saga

read message
$ docker exec -it broker \
kafka-console-consumer --bootstrap-server broker:9092 \
                       --topic saga \
                       --from-beginning


stop kafka
$ docker-compose down

```

### start Saga Execution Coordinator (SEC)
```
$ go run cmd/sec/main.go localhost:9092 saga
```
