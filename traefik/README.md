# Traefik configuration

```
$ docker run -d -p 8080:8080 -p 8000:80 \
    -v $PWD/traefik.toml:/etc/traefik/traefik.toml traefik:v2.0

```
