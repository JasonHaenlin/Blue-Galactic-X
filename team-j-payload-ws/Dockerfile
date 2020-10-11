FROM alpine:edge

RUN apk add --no-cache openjdk11

COPY target/payload-ws.jar /usr/spring/payload-ws.jar

HEALTHCHECK --interval=3s --retries=20 CMD curl --fail http://localhost:8050/payload/ping || exit 1

EXPOSE 8050 8051

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/usr/spring/payload-ws.jar"]
