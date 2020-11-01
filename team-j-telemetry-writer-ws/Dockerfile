FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/telemetry-writer-ws.jar /usr/spring/telemetry-writer-ws.jar

ENV MONGODB_HOST="team-j-blue-galactic-telemetry-database"
ENV MONGODB_PORT="27017"
ENV KAFKA_BROKER_HOST="broker"
ENV KAFKA_BROKER_PORT="9092"
ENV SCHEMA_BROKER_HOST="schema-registry"
ENV SCHEMA_BROKER_PORT="9090"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8092/telemetry/ping/ || exit 1

EXPOSE 8092

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/telemetry-writer-ws.jar"]
