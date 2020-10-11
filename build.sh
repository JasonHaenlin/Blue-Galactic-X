#!/bin/sh

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

for i in "team-j-rocket-ws" "team-j-weather-ws" "team-j-mission-ws" "team-j-telemetry-ws" "team-j-payload-ws" "team-j-client"
do
    cd $i; ./mvnw clean package -DskipTests; cd ..
done
