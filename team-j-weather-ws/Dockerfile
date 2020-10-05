FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/weather-ws.jar /usr/spring/weather-ws.jar

EXPOSE 8060 8061

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/weather-ws.jar"]
