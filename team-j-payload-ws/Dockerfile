FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/payload-ws.jar /usr/spring/payload-ws.jar

EXPOSE 8050 8051

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/payload-ws.jar"]
