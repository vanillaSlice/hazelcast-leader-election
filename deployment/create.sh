#!/usr/bin/env bash

WORKING_DIR=$(dirname "$0")

kubectl create -f $WORKING_DIR/cluster-role-binding.yaml
kubectl create -f $WORKING_DIR/service.yaml
kubectl create -f $WORKING_DIR/deployment.yaml
