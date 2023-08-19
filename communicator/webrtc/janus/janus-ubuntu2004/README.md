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

```

Access the demos page url: **https://[DOMAIN NAME]/demos/index.html**

janus info url: **https://[DOMAIN NAME]/janus/info**

### References:
 - https://towardsaws.com/setting-up-janus-webrtc-on-aws-a8aa8914b0c6
 - https://towardsaws.com/enabling-https-on-janus-using-nginx-on-aws-2de1d6c8817b