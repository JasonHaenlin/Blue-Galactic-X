FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/booster-ws.jar /usr/spring/booster-ws.jar

ENV TELEMETRY_HOST="team-j-blue-galactic-telemetry"
ENV TELEMETRY_PORT="8090"
ENV KAFKA_BROKER_HOST="broker"
ENV KAFKA_BROKER_PORT="9092"
ENV SCHEMA_BROKER_HOST="schema-registry"
ENV SCHEMA_BROKER_PORT="9090"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8030/booster/ping/ || exit 1

EXPOSE 8030 8031

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/booster-ws.jar"]
