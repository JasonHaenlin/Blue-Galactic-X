#!/bin/bash
mag=$'\e[1;35m'
grn=$'\e[1;32m'
blu=$'\e[1;34m'
end=$'\e[0m'

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

printf "${mag}Healthcheck${end} ${blu}Kafka Env.${end} "
res=`docker inspect -f {{.State.Status}} setup`
while [ "$res" != "exited" ]; do
    sleep 0.5;
    printf "${grn}.${end}"
    res=`docker inspect -f {{.State.Status}} setup`
done;
printf "${grn}SETUP READY${end}  \n"

ls -d * | grep "team-j-.*-ws" | cut -c8- | sed 's/.\{3\}$//' | while read line ; do
    printf "${mag}Healthcheck${end} ${blu}${line}${end} "
    res=`docker inspect -f {{.State.Health.Status}} team-j-blue-galactic-$line`
    while [ "$res" != "healthy" ]; do
        sleep 0.5;
        printf "${grn}.${end}"
        res=`docker inspect -f {{.State.Health.Status}} team-j-blue-galactic-$line`
    done;
    printf "${grn}DONE${end}  \n"
done
