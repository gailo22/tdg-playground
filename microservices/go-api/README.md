# go api

```
$ go mod init go-api

$ go get -u github.com/gorilla/mux

$ go build

$ go run app.go

```

## build docker
```
$ docker image build -t go-api -f Dockerfile_dev .
```


## run docker
```
$ docker container run -it --rm go-api
```

## build for production
```
$ docker image build -t go-api-prod .
```

## run production
```
$ docker container run -it -p 8080:8080 --rm go-api-prod
```