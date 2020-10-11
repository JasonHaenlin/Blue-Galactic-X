FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/weather-ws.jar /usr/spring/weather-ws.jar

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8060/weather/ping/ || exit 1

EXPOSE 8060 8061

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/weather-ws.jar"]
