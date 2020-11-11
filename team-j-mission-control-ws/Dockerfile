FROM adoptopenjdk/openjdk11-openj9:alpine-slim

COPY target/mission-control-ws.jar /usr/spring/mission-control-ws.jar

ENV PAYLOAD_HOST="team-j-blue-galactic-payload"
ENV PAYLOAD_PORT="8050"
ENV ROCKET_HOST="team-j-blue-galactic-rocket"
ENV ROCKET_PORT="8080"
ENV BOOSTER_HOST="team-j-blue-galactic-booster"
ENV BOOSTER_PORT="8030"
ENV MISSION_PREPARATION_HOST="team-j-blue-galactic-mission-preparation"
ENV MISSION_PREPARATION_PORT="8300"

ENV KAFKA_BROKER_HOST="broker"
ENV KAFKA_BROKER_PORT="9092"
ENV SCHEMA_BROKER_HOST="schema-registry"
ENV SCHEMA_BROKER_PORT="9090"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8200/mission-control/ping/ || exit 1

EXPOSE 8200

CMD ["java","-Xscmx50M","-Xmx50m","-Xshareclasses","-Xtune:virtualized","-Xquickstart","-jar", "/usr/spring/mission-control-ws.jar"]
