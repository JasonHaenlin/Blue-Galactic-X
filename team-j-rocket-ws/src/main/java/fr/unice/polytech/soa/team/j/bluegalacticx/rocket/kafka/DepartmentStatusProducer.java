package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;

@Service
public class DepartmentStatusProducer {

    @Value(value = "${kafka.topics.departmentstatus}")
    private String departmentStatusTopic0;

    @Autowired
    private KafkaTemplate<String, GoNogoRequest> kafkaTemplate;

    public void notifyDepartmentStatus(String rocketId, boolean status) {
        GoNogoRequest req = GoNogoRequest.newBuilder().setDepartment(Department.ROCKET).setIdValue(rocketId)
                .setStatus(status).build();

        kafkaTemplate.send(departmentStatusTopic0, req);
    }
}
