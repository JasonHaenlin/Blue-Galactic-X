package fr.unice.polytech.soa.team.j.bluegalacticx.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.replies.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping
    public WeatherStatusReply GetWeather() {
        return service.getWeather();
    }
}
