package fr.unice.polytech.soa.team.j.bluegalacticx.springmission.config;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.MissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MissionServiceTestConfig {

    @Bean
    MissionService missionService() {
        return new MissionService();
    }
}