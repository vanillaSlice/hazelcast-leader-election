# Leader Election

Playing around with leadership election with Hazelcast and Kubernetes (minikube).

To create the initial deployment:
```
eval $(minikube docker-env)
gradle buildDockerImage
./deployment/create.sh
```

To perform updates:
```
./deployment/apply.sh
```

To delete deployment:
```
./deployment/delete.sh
```
