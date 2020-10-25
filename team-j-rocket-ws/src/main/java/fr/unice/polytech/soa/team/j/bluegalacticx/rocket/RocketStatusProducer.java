package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketStatusRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.RocketStatusRequest.EventType;

@Service
public class RocketStatusProducer {

    @Value(value = "${kafka.topics.rocketstatus}")
    private String RocketStatusTopic0;

    @Autowired
    private KafkaTemplate<String, RocketStatusRequest> kafkaTemplate;

    public void launchRocketEvent(String rocketId) {
        RocketStatusRequest req = RocketStatusRequest.newBuilder().setRocketId(rocketId)
                .setEventType(EventType.IN_SERVICE).build();

        kafkaTemplate.send(RocketStatusTopic0, req);
    }

    public void destroyedRocketEvent(String rocketId) {
        RocketStatusRequest req = RocketStatusRequest.newBuilder().setRocketId(rocketId)
                .setEventType(EventType.DESTROYED).build();

        kafkaTemplate.send(RocketStatusTopic0, req);
    }

    public void atBaseRocketEvent(String rocketId) {
        RocketStatusRequest req = RocketStatusRequest.newBuilder().setRocketId(rocketId).setEventType(EventType.AT_BASE)
                .build();

        kafkaTemplate.send(RocketStatusTopic0, req);
    }

    public void inRepairRocketEvent(String rocketId) {
        RocketStatusRequest req = RocketStatusRequest.newBuilder().setRocketId(rocketId)
                .setEventType(EventType.IN_REPAIR).build();

        kafkaTemplate.send(RocketStatusTopic0, req);
    }

    public void damagedRocketEvent(String rocketId) {
        RocketStatusRequest req = RocketStatusRequest.newBuilder().setRocketId(rocketId).setEventType(EventType.DAMAGED)
                .build();

        kafkaTemplate.send(RocketStatusTopic0, req);
    }
}
