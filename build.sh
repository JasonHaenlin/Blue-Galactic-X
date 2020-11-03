#!/bin/bash
mag=$'\e[1;35m'
grn=$'\e[1;32m'
blu=$'\e[1;34m'
end=$'\e[0m'

MODULE=$1
DIR=""
CONTAINER=""

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

if [[ $# -eq 0 ]]; then
    printf "${mag}No module selected...${end}\n"
    printf "${blu}rocket, booster, mission, payload, weather, telemetry-writer, telemetry-reader${end}\n"
    printf "${mag}Building all${end}\n"
    for i in "team-j-rocket-ws" "team-j-weather-ws" "team-j-mission-ws" "team-j-telemetry-writer-ws" "team-j-telemetry-reader-ws" "team-j-payload-ws" "team-j-booster-ws"
    do
        printf "${mag}build${end} ${blu}${i}${end} ... \t"
        cd $i; sh mvnw clean package -q -DskipTests; cd ..
        printf "${grn}DONE${end} \n"
    done
else
    if [[ $MODULE == "rocket" ]]; then
        DIR="team-j-rocket-ws"
        CONTAINER="rocket"
    elif [[ $MODULE == "booster" ]]; then
        DIR="team-j-booster-ws"
        CONTAINER="booster"
    elif [[ $MODULE == "mission" ]]; then
        DIR="team-j-mission-ws"
        CONTAINER="mission"
    elif [[ $MODULE == "payload" ]]; then
        DIR="team-j-payload-ws"
        CONTAINER="payload"
    elif [[ $MODULE == "weather" ]]; then
        DIR="team-j-weather-ws"
        CONTAINER="weather"
    elif [[ $MODULE == "telemetry-writer" ]]; then
        DIR="team-j-telemetry-writer-ws"
        CONTAINER="telemetry-writer"
    elif [[ $MODULE == "telemetry-reader" ]]; then
        DIR="team-j-telemetry-reader-ws"
        CONTAINER="telemetry-reader"
    fi

    cd $DIR
    printf "${mag}build${end} ${blu}${DIR}${end} ... \t"
    sh mvnw clean package -q -DskipTests
    cd ..
    printf "\n"
    docker-compose build $CONTAINER
    printf "${grn}DONE${end} \n"
fi
