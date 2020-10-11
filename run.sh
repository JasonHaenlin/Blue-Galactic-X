#!/bin/bash
mag=$'\e[1;35m'
grn=$'\e[1;32m'
blu=$'\e[1;34m'
cyn=$'\e[1;36m'
end=$'\e[0m'

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

for i in "team-j-blue-galactic-weather" "team-j-blue-galactic-rocket" "team-j-blue-galactic-mission" "team-j-blue-galactic-telemetry" "team-j-blue-galactic-payload"
do
    printf "${mag}Healtcheck${end} ${blu}${i}${end}\t\t"
    res=`docker inspect -f {{.State.Health.Status}} $i`
    while [ "$res" != "healthy" ]; do
        sleep 0.5;
        printf "${grn}.${end}"
        res=`docker inspect -f {{.State.Health.Status}} $i`
    done;
    printf "${grn}DONE${end}  \n"
done

cd "team-j-client"
printf "${cyn}Start demo scenario${end}\n"
mvn test
