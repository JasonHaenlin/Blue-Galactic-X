FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/rocket-ws.jar /usr/spring/rocket-ws.jar

ENV TELEMETRY_HOST="team-j-blue-galactic-telemetry"
ENV TELEMETRY_PORT="8090"
ENV MISSION_HOST="team-j-blue-galactic-mission"
ENV MISSION_PORT="8070"
<<<<<<< HEAD
ENV MISSION_GRPC="8071"
=======

HEALTHCHECK --interval=3s --retries=20 CMD curl --fail http://localhost:8080/rocket/ping || exit 1
>>>>>>> [#60] update docker env properties for spring-boot and link network

EXPOSE 8080 8081

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/rocket-ws.jar"]
