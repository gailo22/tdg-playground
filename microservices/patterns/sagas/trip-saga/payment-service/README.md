## Payment service

```
$ docker build -t gailo22/payments .
$ docker run --rm -it -p 3403:3000 gailo22/payments
$ curl localhost:3403/payments

```