# echo framework

```
$ mkdir echo-app

$ go mod init echo-app

$ go get github.com/labstack/echo/v4

$ go run server.go

```

### testing
```
$ http :1323

$ http :1323/users/2

$ http :1323/show?team=x-men&member=wolverine

$ http --form POST :1323/save name="Joe Smith" email="joe@gmail.com"

$ echo '{ "name": "john", "email": "john@yahoo.com" }' | http :1323/users

```