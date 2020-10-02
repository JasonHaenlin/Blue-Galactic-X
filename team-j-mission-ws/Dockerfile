FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/mission-ws.jar /usr/spring/mission-ws.jar

EXPOSE 8060 8062

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/mission-ws.jar"]
