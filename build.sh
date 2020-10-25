#!/bin/bash
mag=$'\e[1;35m'
grn=$'\e[1;32m'
blu=$'\e[1;34m'
end=$'\e[0m'

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

for i in "team-j-rocket-ws" "team-j-weather-ws" "team-j-mission-ws" "team-j-telemetry-ws" "team-j-payload-ws" "team-j-booster-ws"
do
    printf "${mag}build${end} ${blu}${i}${end} ... \t"
    cd $i; sh mvnw clean package -q -DskipTests; cd ..
    printf "${grn}DONE${end} \n"
done
