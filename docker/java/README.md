## Build
```
docker build -f jar.Dockerfile -t my-api .

```

## Run
```
docker run -p 9000:8080 -it my-api

```

## Logging
```
docker run -it --rm \
-p 24224:24224 \
-v ${PWD}/fluentd/etc/ \
-e FLUENTD_CONF=fluent.conf \
fluent/fluentd:v1.12-1

docker run -it --rm -p 8080:8080
--log-driver=fluentd \
--log-opt tag="{{.Name}}.{{.ImageName}}" \
--name api my-app-log

```