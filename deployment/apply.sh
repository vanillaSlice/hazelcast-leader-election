#!/usr/bin/env bash

WORKING_DIR=$(dirname "$0")

kubectl apply -f $WORKING_DIR/cluster-role-binding.yaml
kubectl apply -f $WORKING_DIR/service.yaml
kubectl apply -f $WORKING_DIR/deployment.yaml
