package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.proto.BoosterStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.missionpreparation.proto.MissionPreparationRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.payload.proto.PayloadStatusRequest;
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

    public ConsumerFactory<String, PayloadStatusRequest> consumerPayloadStatusFactory() {
        Map<String, Object> props = baseConfig();
        props.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, RocketStatusRequest.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PayloadStatusRequest> payloadStatusKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PayloadStatusRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerPayloadStatusFactory());
        return factory;
    }

    public ConsumerFactory<String, BoosterStatusRequest> consumerBoosterStatusFactory() {
        Map<String, Object> props = baseConfig();
        props.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE, RocketStatusRequest.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BoosterStatusRequest> boosterStatusKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BoosterStatusRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerBoosterStatusFactory());
        return factory;
    }

    public ConsumerFactory<String, MissionPreparationRequest> consumerMissionPreparationStatusFactory() {
        Map<String, Object> props = baseConfig();
        props.put(KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE,
                MissionPreparationRequest.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MissionPreparationRequest> missionPreparationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MissionPreparationRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerMissionPreparationStatusFactory());
        return factory;
    }

}
