FROM adoptopenjdk/openjdk11-openj9:alpine-slim

COPY target/orbital-payload-ws.jar /usr/spring/orbital-payload-ws.jar

ENV KAFKA_BROKER_HOST="broker"
ENV KAFKA_BROKER_PORT="9092"
ENV SCHEMA_BROKER_HOST="schema-registry"
ENV SCHEMA_BROKER_PORT="9090"

HEALTHCHECK  --interval=5s --timeout=3s \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8055/payload/ping/ || exit 1

EXPOSE 8055

CMD ["java","-Xscmx50M","-Xmx50m","-Xshareclasses","-Xtune:virtualized","-Xquickstart","-jar", "/usr/spring/orbital-payload-ws.jar"]
