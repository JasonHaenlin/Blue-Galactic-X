#!/bin/bash
cyn=$'\e[1;36m'
end=$'\e[0m'

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

sh healthcheck.sh

cd "team-j-client"
printf "${cyn}Start demo scenario${end}\n"
sh mvnw clean test -Dcucumber.filter.tags="@integration"
cd ..
printf "${cyn}To remove the container : 'docker-compose down'${end}\n"
