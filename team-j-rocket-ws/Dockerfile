FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/rocket-ws.jar /usr/spring/rocket-ws.jar

ENV TELEMETRY_HOST="team-j-blue-galactic-telemetry"
ENV TELEMETRY_PORT="8090"
ENV MISSION_HOST="team-j-blue-galactic-mission"
ENV MISSION_PORT="8070"
ENV MISSION_GRPC="8071"

EXPOSE 8080 8081

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/rocket-ws.jar"]
