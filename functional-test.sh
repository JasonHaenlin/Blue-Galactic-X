#!/bin/bash

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

DEF=1
END=${1:-$DEF}

sh healthcheck.sh

cd "team-j-client"
for i in {1..$END}; do sh run.sh; done
cd ..
