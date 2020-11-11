#!/bin/bash

#  -Dmaven.surefire.debug

echo "Only run the Functionals Features"
# sh mvnw clean test -Dcucumber.filter.tags="@functional"
# sh mvnw clean test -Dcucumber.filter.tags="@telemetry"
sh mvnw clean test -Dcucumber.filter.tags="@status"
# sh mvnw clean test -Dcucumber.filter.tags="@gonogo"
# sh mvnw clean test -Dcucumber.filter.tags="@multipleRockets"
