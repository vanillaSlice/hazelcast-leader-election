# Hazelcast Leader Election

[![Build Status](https://img.shields.io/travis/com/vanillaSlice/hazelcast-leader-election/master.svg)](https://travis-ci.com/vanillaSlice/hazelcast-leader-election)
[![License](https://img.shields.io/github/license/vanillaSlice/hazelcast-leader-election.svg)](LICENSE)

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
