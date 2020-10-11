FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/mission-ws.jar /usr/spring/mission-ws.jar

ENV PAYLOAD_HOST="team-j-blue-galactic-payload"
ENV PAYLOAD_PORT="8050"
ENV ROCKET_HOST="team-j-blue-galactic-rocket"
ENV ROCKET_PORT="8080"

EXPOSE 8070 8071

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/mission-ws.jar"]
