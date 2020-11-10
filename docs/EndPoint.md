# End Point

## Port in use

| port  | function          | module              |
| ----- | ----------------- | ------------------- |
| 8010  | REST              | Missin Log Writer   |
| 8020  | REST              | Anomaly             |
| 8030  | REST              | Booster             |
| 8031  | gRPC              | Booster             |
| 8050  | REST              | payload             |
| 8055  | REST              | Orbital Payload     |
| 8060  | REST              | Weather             |
| 8070  | REST              | mission Preparation |
| 8080  | REST              | Rocket              |
| 8081  | gRPC              | Rocket              |
| 8082  | REST              | Module Destroyer    |
| 8092  | REST              | Telemetry Writer    |
| 8094  | REST              | Telemetry Reader    |
| 8110  | REST              | Mission Log Reader  |
| 8200  | REST              | mission Control     |
| 9090  | -                 | Schema Registry     |
| 9091  | -                 | Zookeeper           |
| 9092  | Inside Container  | Borker              |
| 19092 | Outside Container | Broker              |
| 27017 | -                 | MongoDB             |

## swagger

http://localhost:{port}/swagger-ui/index.html
