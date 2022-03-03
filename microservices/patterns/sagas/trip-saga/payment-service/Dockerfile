FROM golang:alpine AS build-env
RUN apk --no-cache add build-base git gcc
ADD . /src
RUN cd /src && CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o app ./cmd/paymentservice

FROM alpine
WORKDIR /app

EXPOSE 3000

COPY --from=build-env /src/app /app/
ENTRYPOINT ./app