#!/bin/bash

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

END=$1

sh healthcheck.sh

if [[ $# -eq 0 ]]; then
    END=1
fi

cd "team-j-client"
for i in {1..$END}; do (sh run.sh 2>&1) & done
cd ..
