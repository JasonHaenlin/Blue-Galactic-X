package fr.unice.polytech.soa.team.j.bluegalacticx.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializerConfig;

public class KafkaProducerClient<T> {

    protected KafkaProducer<String, T> kafkaProducer;

    private final String topic;

    public KafkaProducerClient(String topic) {
        kafkaProducer = new KafkaProducer<String, T>(insertBaseConfig());
        this.topic = topic;
    }

    private Map<String, Object> insertBaseConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class);
        configProps.put(KafkaProtobufSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:9090");
        return configProps;
    }

    public void emit(T msg) {
        kafkaProducer.send(new ProducerRecord<String, T>(this.topic, msg));
    }
}
