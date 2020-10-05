FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/rocket-ws.jar /usr/spring/rocket-ws.jar

EXPOSE 8080 8081

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/rocket-ws.jar"]
