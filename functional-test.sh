#!/bin/bash

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

sh healthcheck.sh

cd "team-j-client"
sh run.sh
cd ..
