FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/missionlogwriter-ws.jar /usr/spring/missionlogwriter-ws.jar

ENV KAFKA_BROKER_HOST="broker"
ENV KAFKA_BROKER_PORT="9092"
ENV SCHEMA_BROKER_HOST="schema-registry"
ENV SCHEMA_BROKER_PORT="9090"
ENV MONGODB_HOST="database"
ENV MONGODB_PORT="27017"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8010/missionlogwriter/ping/ || exit 1

EXPOSE 8010

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/missionlogwriter-ws.jar"]
