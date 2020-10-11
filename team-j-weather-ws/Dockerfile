FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/weather-ws.jar /usr/spring/weather-ws.jar

HEALTHCHECK --interval=3s --retries=20 CMD curl --fail http://localhost:8060/weather/ping || exit 1

EXPOSE 8060 8061

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/weather-ws.jar"]
