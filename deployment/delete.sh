#!/usr/bin/env bash

kubectl delete clusterrolebinding leader-election
kubectl delete service leader-election
kubectl delete deployment leader-election
