# influxdb

### installation

```
$ docker run --name influxdb -p 8086:8086 influxdb:2.0.9
```

### generate config
```

$ docker run \
  --rm influxdb:2.0.9 \
  influxd print-config > config.yml
```
