# team-j-missionlogreader-ws
missionlogreader web service

## Start the project

You can use Maven wrapper if you do not have it installed

```sh
# shell
./mvnw clean compile
# or executable
mvnw.exe clean compile
```

Start spring boot

```sh
mvn spring-boot:run
```

### gRPC

the proto file need to be compile to import and use the objects
You can do it during the compilation phase.

```sh
mvn compile
# or
mvn spring-boot:run
```

### dependencies

    - Java 11
    - Maven 3.6.3

### Vscode Plugins

    - Language Support for Java(TM) by Red Hat
    - Debugger for Java
    - Maven for Java
    - Spring Boot tools
    - vscode-proto3 (need clang-format installed (LLVM) and clang-format plugin)
    - YAML
