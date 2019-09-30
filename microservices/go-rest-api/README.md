## initialize go project

```
mkdir go-rest-api
cd go-rest-api
go mod init go-rest-api

go build
```


### run and test
```
$ ./go-rest-api

$ http localhost:8080/ping
HTTP/1.1 200 OK
Content-Length: 18
Content-Type: application/json; charset=utf-8
Date: Fri, 09 Aug 2019 04:24:13 GMT

{
    "message": "pong"
}

```