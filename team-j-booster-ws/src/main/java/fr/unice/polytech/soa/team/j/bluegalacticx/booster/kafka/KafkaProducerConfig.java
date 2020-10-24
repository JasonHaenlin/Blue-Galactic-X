package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.broker.host}")
    private String brokerHost;
    @Value("${kafka.broker.port}")
    private String brokerPort;
    @Value("${kafka.schema.host}")
    private String schemaHost;
    @Value("${kafka.schema.port}")
    private String schemaPort;

    @Bean
    public ProducerFactory<String, Double> producerSpeedChangeFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerHost + ":" + brokerPort);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Double> kafkaTemplate() {
        return new KafkaTemplate<>(producerSpeedChangeFactory());
    }
}
