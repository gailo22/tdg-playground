#!/bin/bash

docker run --rm -e "AGE=35" -v $(pwd):/go/src/app -w /go/src/app golang go run main.go