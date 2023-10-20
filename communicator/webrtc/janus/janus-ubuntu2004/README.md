# Setup janus gateway on Ubuntu 20.04.6 LTS (GNU/Linux 5.15.0-1036-aws x86_64)

### download dependencies
```
$ yes | bash download.sh
```

### install dependencies
```
$ yes | bash install.sh
```

### install janus gateway
```
$ yes | bash install-janus.sh
```

### install nginx and setup ssl using letsecrypt
follow the reference below.

Example of nginx config file:

```
location /janus/ {
    proxy_pass http://127.0.0.1:8088/janus/;
}

location /demos/ {
    alias /opt/janus/share/janus/demos/;
}

location / {
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "Upgrade";
    proxy_set_header Host $host;
    proxy_pass http://127.0.0.1:8188;
}

location /admin {
        proxy_set_header Host $host;
        proxy_set_header Connection "";
        proxy_http_version 1.1;

        proxy_pass http://127.0.0.1:7088;
}

```

Access the demos page url: **https://[DOMAIN NAME]/demos/index.html**

janus info url: **https://[DOMAIN NAME]/janus/info**

### References:
 - https://towardsaws.com/setting-up-janus-webrtc-on-aws-a8aa8914b0c6
 - https://towardsaws.com/enabling-https-on-janus-using-nginx-on-aws-2de1d6c8817b
 - https://facsiaginsa.com/janus/enable-janus-admin-monitoring-page
 - https://webrtc.ventures/2023/01/connecting-to-a-janus-media-server-using-janode/
 - https://webrtc.ventures/2021/08/hardened-janus-gateway/
 - https://medium.com/@BeingOttoman/coturn-rest-api-development-deployment-7bac065c64aa
 


## github repos
- https://github.com/flutterjanus/JanusDocker
- https://github.com/flutterjanus/flutter_janus_client
- https://github.com/flutterjanus/JanusJs
- https://github.com/meetecho/simple-whip-client
- https://github.com/meetecho/simple-whip-server
- https://github.com/pion/webrtc