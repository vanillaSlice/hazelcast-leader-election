#!/usr/bin/env bash

path=$(dirname "$0")

kubectl create -f $path/cluster-role-binding.yaml
kubectl create -f $path/service.yaml
kubectl create -f $path/deployment.yaml
