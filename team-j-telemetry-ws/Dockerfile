FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/telemetry-ws.jar /usr/spring/telemetry-ws.jar

EXPOSE 8070 8071

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/telemetry-ws.jar"]
