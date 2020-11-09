#!/bin/bash
mag=$'\e[1;35m'
grn=$'\e[1;32m'
blu=$'\e[1;34m'
end=$'\e[0m'

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

printf "${mag}Healtcheck${end} ${blu}Kafka Env.${end} "
res=`docker inspect -f {{.State.Status}} setup`
while [ "$res" != "exited" ]; do
    sleep 0.5;
    printf "${grn}.${end}"
    res=`docker inspect -f {{.State.Status}} setup`
done;
printf "${grn}SETUP READY${end}  \n"

for i in "team-j-blue-galactic-weather" "team-j-blue-galactic-rocket" "team-j-blue-galactic-mission" "team-j-blue-galactic-telemetry-writer" "team-j-blue-galactic-telemetry-reader" "team-j-blue-galactic-payload" "team-j-blue-galactic-booster" "team-j-blue-galactic-missionlogwriter" "team-j-blue-galactic-missionlogreader" "team-j-blue-galactic-anomaly" "team-j-blue-galactic-orbital-payload" "team-j-blue-galactic-module-destroyer"
do
    printf "${mag}Healtcheck${end} ${blu}${i}${end} "
    res=`docker inspect -f {{.State.Health.Status}} $i`
    while [ "$res" != "healthy" ]; do
        sleep 0.5;
        printf "${grn}.${end}"
        res=`docker inspect -f {{.State.Health.Status}} $i`
    done;
    printf "${grn}DONE${end}  \n"
done
