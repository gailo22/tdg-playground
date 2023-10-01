# go module example

```
$ go mod init example.com/my-module
$ go build .
$ ./my-module

$ go get -u github.com/gorilla/mux

$ go list all
$ go list -m all
$ go list -m -versions github.com/gorilla/mux

$ go mod verify

$ ls $GOPATH/pkg/mod
```