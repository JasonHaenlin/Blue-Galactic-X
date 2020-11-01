FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/rocket-ws.jar /usr/spring/rocket-ws.jar

ENV MISSION_HOST="team-j-blue-galactic-mission"
ENV MISSION_PORT="8070"
ENV MISSION_GRPC="8071"
ENV BOOSTER_HOST="team-j-blue-galactic-booster"
ENV BOOSTER_PORT="8030"
ENV BOOSTER_GRPC="8031"
ENV KAFKA_BROKER_HOST="broker"
ENV KAFKA_BROKER_PORT="9092"
ENV SCHEMA_BROKER_HOST="schema-registry"
ENV SCHEMA_BROKER_PORT="9090"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/rocket/ping/ || exit 1

EXPOSE 8080 8081

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/rocket-ws.jar"]
