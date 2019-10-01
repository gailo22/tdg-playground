FROM golang:1.12-alpine3.10

RUN apk update && apk add gcc git 
WORKDIR /
COPY . .
RUN go build ./...
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app .

FROM alpine:latest  
RUN apk --no-cache add ca-certificates
WORKDIR /root/
COPY --from=0 /app .

CMD ["./app"]  