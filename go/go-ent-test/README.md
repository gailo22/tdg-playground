# Go Entity framework
This is example from `https://entgo.io/docs/getting-started/`
### initialize and build
```
$ go mod init go-ent-test
$ go get github.com/facebookincubator/ent/cmd/entc
$ entc init User

$ entc generate ./ent/schema

$ entc init Car Group

$ entc generate ./ent/schema

```