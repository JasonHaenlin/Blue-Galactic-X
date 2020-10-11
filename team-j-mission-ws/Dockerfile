FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/mission-ws.jar /usr/spring/mission-ws.jar

ENV PAYLOAD_HOST="team-j-blue-galactic-payload"
ENV PAYLOAD_PORT="8050"
ENV ROCKET_HOST="team-j-blue-galactic-rocket"
ENV ROCKET_PORT="8080"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8070/mission/ping/ || exit 1

EXPOSE 8070 8071

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/mission-ws.jar"]
