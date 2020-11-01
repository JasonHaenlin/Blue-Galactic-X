FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/missionlogreader-ws.jar /usr/spring/missionlogreader-ws.jar

ENV KAFKA_BROKER_HOST="broker"
ENV KAFKA_BROKER_PORT="9092"
ENV SCHEMA_BROKER_HOST="schema-registry"
ENV SCHEMA_BROKER_PORT="9090"
ENV MONGODB_HOST="team-j-blue-galactic-database"
ENV MONGODB_PORT="27017"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8110/missionlogreader/ping/ || exit 1

EXPOSE 8110 8111

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/missionlogreader-ws.jar"]
