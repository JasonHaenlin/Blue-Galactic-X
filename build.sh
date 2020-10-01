#!/bin/sh

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

for i in "team-j-client" "team-j-rocket-ws" "team-j-weather-ws"
do
    cd $i; ./mvnw clean package -DskipTests; cd ..
done
