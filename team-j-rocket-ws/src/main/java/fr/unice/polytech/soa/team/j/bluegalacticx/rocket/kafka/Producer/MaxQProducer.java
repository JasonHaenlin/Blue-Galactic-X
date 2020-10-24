package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.Producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class MaxQProducer {

    @Autowired
    private KafkaTemplate<String, Integer> kafkaTemplateMaxQ;

    public void sendInMaxQ() {

        kafkaTemplateMaxQ.send("maxQ", 1);
    }

    public void sendNotInMaxQ() {
        kafkaTemplateMaxQ.send("maxQ", 0);
    }

   
}
