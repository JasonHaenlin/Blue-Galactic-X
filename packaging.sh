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
    printf "${blu}rocket, booster, mission, payload, weather${end}\n"
    printf "${blu}telemetry-writer, telemetry-reader, missionlogwriter, missionlogreader${end}\n"
    printf "${blu}anomaly, module-destroyer, orbital-payload${end}\n"
    printf "${mag}Building all${end}\n"
    for i in "team-j-blue-galactic-weather" "team-j-blue-galactic-rocket" "team-j-blue-galactic-mission" "team-j-blue-galactic-telemetry-writer" "team-j-blue-galactic-telemetry-reader" "team-j-blue-galactic-payload" "team-j-blue-galactic-booster" "team-j-blue-galactic-missionlogwriter" "team-j-blue-galactic-missionlogreader" "team-j-blue-galactic-anomaly" "team-j-blue-galactic-orbital-payload" "team-j-blue-galactic-module-destroyer"
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
    elif [[ $MODULE == "missionlogwriter" ]]; then
        DIR="team-j-missionlogwriter-ws"
        CONTAINER="missionlogwriter"
    elif [[ $MODULE == "missionlogreader" ]]; then
        DIR="team-j-missionlogreader-ws"
        CONTAINER="missionlogreader"
    elif [[ $MODULE == "anomaly" ]]; then
        DIR="team-j-anomaly-ws"
        CONTAINER="anomaly"
    elif [[ $MODULE == "orbital-payload" ]]; then
        DIR="team-j-orbital-payload-ws"
        CONTAINER="orbital-payload"
    elif [[ $MODULE == "module-destroyer" ]]; then
        DIR="team-j-module-destroyer-ws"
        CONTAINER="module-destroyer"
    fi

    cd $DIR
    printf "${mag}build${end} ${blu}${DIR}${end} ... \t"
    sh mvnw clean package -q -DskipTests
    cd ..
    printf "\n"
    printf "${grn}DONE${end} \n"
fi
