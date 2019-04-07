# Hazelcast Leader Election

[![Build Status](https://img.shields.io/travis/com/vanillaSlice/hazelcast-leader-election/master.svg)](https://travis-ci.com/vanillaSlice/hazelcast-leader-election)
[![License](https://img.shields.io/github/license/vanillaSlice/hazelcast-leader-election.svg)](LICENSE)

Playing around with leadership election with [Hazelcast](https://hazelcast.com/) and [Kubernetes](https://kubernetes.io/) (minikube).

To create the initial deployment/perform updates:

```
kubectl apply -f ./deployment
```

To delete deployment:
```
kubectl delete -f ./deployment
```
