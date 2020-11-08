package fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.kafka.producer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.anomaly.proto.AnomalyRequest;

@Service
public class AnomalyProducer {

    @Value(value = "${kafka.topics.anomaly}")
    private String topic0;

    @Autowired
    private KafkaTemplate<String, AnomalyRequest> kafkaTemplate;

    public void alertAnomalies(List<AnomalyRequest> anomalies) {
        for (AnomalyRequest req : anomalies) {
            kafkaTemplate.send(topic0, req);
        }
    }
}
