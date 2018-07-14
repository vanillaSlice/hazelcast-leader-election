#!/usr/bin/env bash

path=$(dirname "$0")

kubectl delete clusterrolebinding leader-election
kubectl delete service leader-election
kubectl delete deployment leader-election
