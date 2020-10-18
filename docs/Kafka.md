# Docker Images

[cloudkarafka]: https://www.cloudkarafka.com/blog/2018-07-04-cloudkarafka_what_is_zookeeper.html
[kafkalistener]: https://rmoff.net/2018/08/02/kafka-listeners-explained/

## cp-kafka

Start a Kafka Broker

### KAFKA_ADVERTISED_LISTENERS

- PLAINTEXT : Un-authenticated, non-encrypted channel
- SASL_PLAINTEXT : SASL authenticated, non-encrypted channel
- SASL_SSL : SASL authenticated, SSL channel
- SSL : SSL channel

[more info on kafka advertised listener][kafkalistener]

## cp-zookeeper

Zookeeper is mandatory for kafka. It's a top-level software used to maintain naming a configuration. It keeps track of all the data in a Kafka cluster (e.g.: Kafka nodes, topics, ...). It manages a potential failover to migrate to an other node in real-time. Thanks to that, a Single Point of Failure is avoided by being able to assign a new node. It is used to maintain a list of all the brokers. If the leader node fail, a new one is elected and the tasks are split into the sub nodes to continue working normaly.

[more info on cloudkarafka][cloudkarafka]
