# Install redis
```
$ brew install redis
```

# start redis server
```
$ redis-server
```

# start redis-cli
```
$ redis-cli
```

# build nodejs app
```
$ npm install
$ nodemon server.js
```

# test
```
url pattern: <url>/api/concurrent/<ssoid>/<deviceId>
open browser url: 
* http://localhost:8080/api/concurrent/123/device1
* http://localhost:8080/api/concurrent/123/device2
```

# check redis db in redis-cli
```
$ redis-cli

# list all keys
127.0.0.1:6379> keys *

# get key value
127.0.0.1:6379> get 123:123:device1

# count devices by ssoid
127.0.0.1:6379> get 123:count

```