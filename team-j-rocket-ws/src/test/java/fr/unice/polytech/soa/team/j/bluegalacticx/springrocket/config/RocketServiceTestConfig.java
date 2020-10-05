package fr.unice.polytech.soa.team.j.bluegalacticx.springrocket.config;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketServiceTestConfig {

    @Bean
    RocketService rocketService() {
        return new RocketService();
    }
}
