# Pushgatewat

### install using docker
```
$ docker pull prom/pushgateway

$ docker run -d -p 9091:9091 prom/pushgateway

```
open `http://localhost:9091/#`


### install prometheus

1. update config file `prometheus.yml` and add below section:

```yml
- job_name: 'pushgateway'
  honor_labels: true
  metrics_path: /metrics
  static_configs:
  - targets: ['docker.host.internal:9091']

```

2. run docker

```
$ docker run \
    -p 9090:9090 \
    -v ${PWD}/prometheus.yml:/etc/prometheus/prometheus.yml \
    prom/prometheus
```

3. push some metric to the push gateway

```
$ echo "some_metric 3.14" | curl --data-binary @- http://localhost:9091/metrics/job/pushgateway


$ for (( ; ; )); do
 echo "some_metric 4.02" | curl --data-binary @- http://localhost:9091/metrics/job/some_job
 sleep 1;
done

```

4. check in grafana

Explore -> Prometheus -> metrics browser> some_metric{job="some_job"}


