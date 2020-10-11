FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/telemetry-ws.jar /usr/spring/telemetry-ws.jar

ENV MONGODB_HOST="team-j-blue-galactic-telemetry-database"
ENV MONGODB_PORT="27017"

EXPOSE 8090 8091

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/telemetry-ws.jar"]
