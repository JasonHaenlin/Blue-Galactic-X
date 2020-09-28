#!/bin/bash

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

cd ./team-j-weather-ws
mvn clean package -DskipTests
cd ../team-j-rocket-ws
mvn clean package -DskipTests
cd ..
docker-compose build
docker-compose up
