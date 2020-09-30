package fr.unice.polytech.soa.team.j.bluegalacticx.springrocket;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.replies.RocketStatusReply;
import fr.unice.polytech.soa.team.j.bluegalacticx.springrocket.config.RocketServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig(classes = {RocketServiceTestConfig.class })
class RocketServiceTest {

    @Autowired
    private RocketService rocketService;

    @Test
    void getStatus() {
        RocketStatusReply rsr = rocketService.getStatus();
        assertEquals(RocketStatus.READY, rsr.getStatus());
    }
}