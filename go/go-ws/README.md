# go websocket

## go http2 server

### generate ssl certificate
```
$ openssl genrsa -out ca.key 2048
$ openssl req -new -x509 -days 365 -key ca.key -subj "/C=CN/ST=GD/L=SZ/O=Acme, Inc./CN=Acme Root CA" -out ca.crt
$ openssl req -newkey rsa:2048 -nodes -keyout server.key -subj "/C=CN/ST=GD/L=SZ/O=Acme, Inc./CN=localhost" -out server.csr
$ openssl x509 -req -extfile <(printf "subjectAltName=DNS:localhost") -days 365 -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt
```


### run client
```
$ go run "./cmd/http2-client/main.go"

$ go run "./cmd/http2-client/main.go" -version 1

```


### http3
```
$ openssl x509 -in server.crt -pubkey -noout | openssl pkey -pubin -outform der | openssl dgst -sha256 -binary | openssl enc -base64
MorvsN2s/qrWpW0E4HFC/xkkIdebbN8vPpdpD0VDI4c=

$ /Applications/Google\ Chrome\ Canary.app/Contents/MacOS/Google\ Chrome\ Canary \
    --origin-to-force-quic-on=localhost:4433 \
    --ignore-certificate-errors-spki-list=MorvsN2s/qrWpW0E4HFC/xkkIdebbN8vPpdpD0VDI4c=

```

open `https://googlechrome.github.io/samples/webtransport/client.html` using chrome canary