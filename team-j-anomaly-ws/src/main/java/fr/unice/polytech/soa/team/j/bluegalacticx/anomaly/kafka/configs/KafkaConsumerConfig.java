package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.kafka.configs;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.booster.proto.TelemetryBoosterRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.payload.proto.TelemetryPayloadRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.rocket.proto.TelemetryRocketRequest;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka.broker.host}")
    private String brokerHost;
    @Value("${kafka.broker.port}")
    private String brokerPort;
    @Value("${kafka.schema.host}")
    private String schemaHost;
    @Value("${kafka.schema.port}")
    private String schemaPort;
    @Value("${kafka.group.default}")
    private String defaultGroupId;

    private Map<String, Object> baseConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerHost + ":" + brokerPort);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, defaultGroupId);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaProtobufDeserializer.class);

        props.put(KafkaProtobufDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                "http://" + schemaHost + ":" + schemaPort);
        return props;
    }

    public <T> ConcurrentKafkaListenerContainerFactory<String, T> consumerCustomFactory(String className) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> props = baseConfig();
        props.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, className);
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TelemetryBoosterRequest> telemetryBoosterKafkaListenerContainerFactory() {
        return consumerCustomFactory(TelemetryBoosterRequest.class.getName());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TelemetryPayloadRequest> telemetryPayloadKafkaListenerContainerFactory() {
        return consumerCustomFactory(TelemetryPayloadRequest.class.getName());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TelemetryRocketRequest> telemetryRocketKafkaListenerContainerFactory() {
        return consumerCustomFactory(TelemetryRocketRequest.class.getName());
    }
}
