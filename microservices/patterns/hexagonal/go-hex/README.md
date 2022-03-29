# Hexagonal Architecture using go

```
$ go mod init go-hex
```


## build a docker image
```
$ docker build -t gailo22/go-hex-expression .

$ docker run -p 3000:3000 gailo22/go-hex-expression
```

## run
```
$ docker compose up --build
```

## testing
```
$ curl -XPOST -d'{"A":1, "B": 2}' localhost:3000/addition
```

## verify in db

mysql> select * from expr_history;
+------------+--------+-----------+
| date       | answer | operation |
+------------+--------+-----------+
| 2022-03-29 |      3 | addition  |
+------------+--------+-----------+


