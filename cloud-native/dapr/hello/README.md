# hello app

```
$ dapr run --app-id hello-dapr --app-port 8088 --dapr-http-port 8089 go run main.go

$ curl -X POST -H "Content-Type: application/json" -d '[{ "key": "name", "value": "Bruce Wayne"}]' http://localhost:8089/v1.0/state/statestore

$ curl http://localhost:8089/v1.0/state/statestore/name


$ http :8088/greeting

$ dpr list
```

