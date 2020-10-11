FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/telemetry-ws.jar /usr/spring/telemetry-ws.jar

ENV MONGODB_HOST="team-j-blue-galactic-telemetry-database"
ENV MONGODB_PORT="27017"
<<<<<<< HEAD
=======

HEALTHCHECK --interval=3s --retries=20 CMD curl --fail http://localhost:8090/telemetry/ping || exit 1
>>>>>>> [#60] update docker env properties for spring-boot and link network

EXPOSE 8090 8091

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/telemetry-ws.jar"]
