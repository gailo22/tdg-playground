# Homer SipCapture

### install hom7-docker

```
$ git checkout https://github.com/sipcapture/homer7-docker
$ cd homer7-docker/heplify-server/hom7-prom-all

$ docker-compose up -d

```

### generate sip transactions using hepgen.js

```
$ npm install -g hepgen.js
$ hepgen.js -s 127.0.0.1 -p 9060 -c "./config/b2bcall_rtcp.js"

```