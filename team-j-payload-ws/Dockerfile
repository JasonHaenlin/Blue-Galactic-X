FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/payload-ws.jar /usr/spring/payload-ws.jar

ENV KAFKA_BROKER_HOST="broker"
ENV KAFKA_BROKER_PORT="9092"
ENV SCHEMA_BROKER_HOST="schema-registry"
ENV SCHEMA_BROKER_PORT="9090"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8050/payload/ping/ || exit 1

EXPOSE 8050 8051

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/payload-ws.jar"]
