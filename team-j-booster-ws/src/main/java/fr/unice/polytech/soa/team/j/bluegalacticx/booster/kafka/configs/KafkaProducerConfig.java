package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.configs;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterLandingStepRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializerConfig;

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

    private Map<String, Object> insertBaseConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerHost + ":" + brokerPort);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class);
        configProps.put(KafkaProtobufSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                "http://" + schemaHost + ":" + schemaPort);
        return configProps;
    }

    @Bean
    public KafkaTemplate<String, BoosterStatusRequest> kafkaTemplateRocket() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(insertBaseConfig()));
    }

    @Bean
    public KafkaTemplate<String, TelemetryBoosterRequest> kafkaTemplateTelemetryBooster() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(insertBaseConfig()));
    }

    @Bean
    public KafkaTemplate<String, BoosterLandingStepRequest> kafkaTemplateLandingStepBooster() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(insertBaseConfig()));
    }

}
