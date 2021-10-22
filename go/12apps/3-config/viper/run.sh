#!/bin/bash

docker run -h viper --rm -e HOSTNAME=example.com -e PORT=1337 -v $(pwd):/go/src/app -w /go/src/app golang go run main.go
