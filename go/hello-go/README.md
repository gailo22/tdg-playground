Setup
---


```
$ mkdir hello-go
$ cd hello-go
```
Create files `hello.go` and `hello_test.go`

```
$ go test

$ go mod init example.com/hello
go: creating new go.mod: module example.com/hello

$ cat go.mod
module example.com/hello

go 1.15

$ go list -m all


$ go mod tidy

```