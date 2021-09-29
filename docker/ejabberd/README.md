### install ejabberd docker

```
mkdir database
docker run -d --name ejabberd -v $(pwd)/ejabberd.yml:/home/ejabberd/conf/ejabberd.yml -v $(pwd)/exAuth2.py:/home/ejabberd/conf/exAuth.py -v $(pwd)/database:/home/ejabberd/database -p 5222:5222 -p 5280:5280 ejabberd/ecs
```

```
docker exec -it ejabberd bin/ejabberdctl register admin localhost password
```

```
docker exec -it ejabberd tail -f logs/ejabberd.log
```

open `http://localhost:5280/admin' login as admin/password


### edit ejabberd config file
```
docker exec -it ejabberd vi conf/ejabberd.yml
```


### install python
```
docker exec -it --user=root ejabberd sh

echo -e "http://nl.alpinelinux.org/alpine/v3.5/main\nhttp://nl.alpinelinux.org/alpine/v3.5/community" > /etc/apk/repositories

apk add --no-cache python py-pip

pip install requests

```