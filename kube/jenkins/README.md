# build


```
$ docker build -t gailo22/jenkins-k8s:lts .
$ docker push gailo22/jenkins-k8s:lts

$ kubectl apply -f jenkins-pvc.yml

$ kubectl apply -f jenkins-deployment.yml

$ kubectl apply -f jenkins-service.yml

$ minikube service jenkins-k8s-service
```

login:
admin/password

```
$ kubectl create -f fabric8-rbac.yml
$ minikube service my-app-service

```
http http://192.168.99.100:30360/greeting

