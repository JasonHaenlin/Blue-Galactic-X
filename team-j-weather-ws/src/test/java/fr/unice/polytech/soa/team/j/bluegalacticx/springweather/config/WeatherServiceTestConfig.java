package fr.unice.polytech.soa.team.j.bluegalacticx.springweather.config;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.WeatherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherServiceTestConfig {

    @Bean
    WeatherService weatherService() {
        return new WeatherService();
    }
}