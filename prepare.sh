#!/bin/bash

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

echo "Building JAR files"
./build.sh

echo "Building Docker images"
docker-compose build

echo "Running Docker Containers in DETACHED mode"
docker-compose up -d
