## Flight booking service

```
$ docker build -t gailo22/flightbooking .
$ docker run --rm -it -p 3401:3000 gailo22/flightbooking
$ curl localhost:3401/flights

```