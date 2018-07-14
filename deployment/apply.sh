#!/usr/bin/env bash

path=$(dirname "$0")

kubectl apply -f $path/cluster-role-binding.yaml
kubectl apply -f $path/service.yaml
kubectl apply -f $path/deployment.yaml
