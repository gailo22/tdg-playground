# InfluxDB


```
docker run \
    --name influxdb \
    -p 8086:8086 \
    --volume /Users/montreebungnasang/tmp/influxdb-data:/var/lib/influxdb2 \
    influxdb:2.5.1

```

```
$ mvn archetype:generate -DgroupId=com.example.influxdb -DartifactId=influxdb-client -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

```