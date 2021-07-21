# Node Exporter

### install using homebrew
```
$ brew install node_exporter

$ node_exporter --web.listen-address=":9200"
```


### install prometheus

1. update config file `prometheus.yml` and add below section:

```yml
- job_name: 'node_exporter'

    scrape_interval: 5s

    # using 'host.docker.internal' as the docker hostname
    static_configs:
      - targets: ['host.docker.internal:9200']

```

2. run docker

```
$ docker run \
    -p 9090:9090 \
    -v ${PWD}/prometheus.yml:/etc/prometheus/prometheus.yml \
    prom/prometheus
```

3. checking the metrics by open `http://localhost:9090/graph` and search for "node_***"

### install grafana
1. run grafana docker
```
$ docker run -d -p 3000:3000 grafana/grafana
```

2. open browser `http://localhost:3000/login' and click skip login.

3. go to configuration -> data sources -> add data source

4. select Prometheus and put URL as 'http://172.17.0.2:9090'

5. go to create -> import dashboard -> input dashboard id '3662' Prometheus 2.0 Overview

6. back to home and view the dashboard



