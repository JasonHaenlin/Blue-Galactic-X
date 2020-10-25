#!/bin/bash

echo "Only run the Functionals Features"
sh mvnw clean test -Dcucumber.filter.tags="@functional"
