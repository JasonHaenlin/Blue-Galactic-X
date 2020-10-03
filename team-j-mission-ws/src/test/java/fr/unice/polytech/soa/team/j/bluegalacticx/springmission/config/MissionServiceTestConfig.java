package fr.unice.polytech.soa.team.j.bluegalacticx.springmission.config;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.MissionService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackages = "fr.unice.polytech.soa.team.j.bluegalacticx.mission")
@Configuration
@EnableWebMvc
public class MissionServiceTestConfig {
    
    @Bean
    MissionService missionService() {
        return new MissionService();
    }
}