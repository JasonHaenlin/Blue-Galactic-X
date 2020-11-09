#!/bin/bash

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

if [[ $# -eq 0 ]]; then
    printf "${mag}Select a module...${end}\n"
    printf "${blu}rocket, booster, mission, payload, weather, telemetry-writer, telemetry-reader, missionlogwriter, missionlogreader,mission-preparation${end}\n"
fi
printf "packaging and recreating the container ...\n"
sh packaging.sh $1
docker-compose up -d --force-recreate --no-deps --build $1
printf "Done\n"
