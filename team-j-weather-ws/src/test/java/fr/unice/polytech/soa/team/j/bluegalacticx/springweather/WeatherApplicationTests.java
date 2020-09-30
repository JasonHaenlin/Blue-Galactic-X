package fr.unice.polytech.soa.team.j.bluegalacticx.springweather;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import fr.unice.polytech.soa.team.j.bluegalacticx.springweather.config.WeatherServiceTestConfig;

@SpringBootTest
@SpringJUnitConfig(classes = {WeatherServiceTestConfig.class })
class WeatherApplicationTests {

	@Test
	void contextLoads() {
	}

}
