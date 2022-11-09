# Stream Processing using Flink

### prerequisites
- download and install apache flink

### start flink
```
$ ./bin/start-cluster.sh
```
### generate java maven project
```
$ mvn archetype:generate                \
  -DarchetypeGroupId=org.apache.flink   \
  -DarchetypeArtifactId=flink-quickstart-java \
  -DarchetypeVersion=1.16.0 \
  -DartifactId=flink-stream-example \
  -DgroupId=stream-example \
  -Dversion=0.1 \
  -Dpackage=com.gailo22.stream.flink \
  -DinteractiveMode=false
```

### build
```
$ mvn clean package
```

### run
```
flink-1.16.0 » bin/flink run flink-stream-example-0.1.jar
```

### check the log
```
$ tail -f log/flink-*-taskexecutor-*.out
```