package fr.unice.polytech.soa.team.j.bluegalacticx.springmission;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import fr.unice.polytech.soa.team.j.bluegalacticx.springmission.config.MissionServiceTestConfig;

@SpringBootTest
@SpringJUnitConfig(classes = {MissionServiceTestConfig.class })
class MissionApplicationTests {

	@Test
	void contextLoads() {
	}

}
