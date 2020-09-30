package fr.unice.polytech.soa.team.j.bluegalacticx.weather;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.replies.*;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.*;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    public WeatherStatusReply getWeather() {
        return new WeatherStatusReply(WeatherStatus.SUNNY);
    }

}
