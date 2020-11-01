FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/telemetry-reader-ws.jar /usr/spring/telemetry-reader-ws.jar

ENV MONGODB_HOST="team-j-blue-galactic-telemetry-database"
ENV MONGODB_PORT="27017"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8094/telemetry/ping/ || exit 1

EXPOSE 8094

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/telemetry-reader-ws.jar"]
