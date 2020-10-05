FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/telemetry-ws.jar /usr/spring/telemetry-ws.jar

EXPOSE 8090 8091

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/telemetry-ws.jar"]
