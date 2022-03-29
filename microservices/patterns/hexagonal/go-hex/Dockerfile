FROM golang:alpine AS build

RUN apk update \
  && apk add --no-cache \ 
    mysql-client \
    build-base

ADD . /src
RUN cd /src && CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app ./cmd/expression


FROM alpine
WORKDIR /app
EXPOSE 3000
COPY --from=build /src/config.yml /app/
COPY --from=build /src/app /app/
ENTRYPOINT ./app