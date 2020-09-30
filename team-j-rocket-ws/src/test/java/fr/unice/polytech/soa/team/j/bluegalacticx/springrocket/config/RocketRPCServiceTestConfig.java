package fr.unice.polytech.soa.team.j.bluegalacticx.springrocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketRPCService;

@Configuration
public class RocketRPCServiceTestConfig {

    @Bean
    RocketRPCService rocketRPCService() {
        return new RocketRPCService();
    }



}
