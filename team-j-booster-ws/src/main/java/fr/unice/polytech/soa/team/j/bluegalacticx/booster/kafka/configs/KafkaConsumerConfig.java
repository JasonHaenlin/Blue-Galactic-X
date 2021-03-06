package fr.unice.polytech.soa.team.j.bluegalacticx.booster.kafka.configs;

import java.util.HashMap;
import java.util.Map;

import fr.unice.polytech.soa.team.j.bluegalacticx.moduledestroyer.proto.DestroyModuleRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.MaxQRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketStatusRequest;
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

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MaxQRequest> maxQKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MaxQRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> props = baseConfig();
        props.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, MaxQRequest.class.getName());
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        return factory;
    }

    public ConsumerFactory<String, RocketStatusRequest> consumerRocketStatusFactory() {
        Map<String, Object> props = baseConfig();
        props.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, RocketStatusRequest.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RocketStatusRequest> rocketStatusKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RocketStatusRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerRocketStatusFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DestroyModuleRequest> ModuleDestructionKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DestroyModuleRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> props = baseConfig();
        props.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, DestroyModuleRequest.class.getName());
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        return factory;
    }

}