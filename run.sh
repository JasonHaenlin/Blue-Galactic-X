cd `dirname $0`
cd ./team-j-weather-ws
mvn package -DskipTests
cd ../team-j-rocket-ws
mvn package -DskipTests
cd ..
docker-compose build
docker-compose up