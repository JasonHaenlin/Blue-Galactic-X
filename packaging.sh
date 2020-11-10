#!/bin/bash
mag=$'\e[1;35m'
grn=$'\e[1;32m'
blu=$'\e[1;34m'
end=$'\e[0m'

MODULE=$1
DIR=""

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

if [[ $# -eq 0 ]]; then
    printf "${mag}No module selected...${end}\n"
    printf "${blu}rocket, booster, mission, payload, weather${end}\n"
    printf "${blu}telemetry-writer, telemetry-reader, missionlogwriter, missionlogreader${end}\n"
    printf "${blu}anomaly, module-destroyer, orbital-payload${end}\n"
    printf "${mag}Building all${end}\n"
    ls -d * | grep "team-j-.*-ws" | while read line ; do
        printf "${mag}build${end} ${blu}${line}${end} ... \t"
        cd $line; sh mvnw clean package -q -DskipTests; cd ..
        printf "${grn}DONE${end} \n"
    done
else
    DIR="team-j-$MODULE-ws"

    cd $DIR
    printf "${mag}build${end} ${blu}${DIR}${end} ... \t"
    sh mvnw clean package -q -DskipTests
    cd ..
    printf "\n"
    printf "${grn}DONE${end} \n"
fi
