# Hazelcast Leader Election

Playing around with leadership election with [Hazelcast](https://hazelcast.com/) and [Kubernetes](https://kubernetes.io/) (minikube).

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
