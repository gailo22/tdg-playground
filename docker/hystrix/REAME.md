# Hystrix Dashboard

```
$ docker build . -t hystrix-dash
$ docker run -d --name hystrix-dash -p 8080:8080 hystrix-dash
$ docker run -d --name hystrix-producer -p 7070:7070 diegopacheco/hystrixprod

```

open browser: 
- http://locahost:8080/hystrix-dashboard
  - add http://100.124.112.186:7070/hystrix.stream
- http://localhost:7070/calc/sum?a=1&b=2
