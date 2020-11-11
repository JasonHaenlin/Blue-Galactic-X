package fr.unice.polytech.soa.team.j.bluegalacticx.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;

@RestController
@RequestMapping("/weather")
@ResponseBody
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping
    public WeatherReport getCurrentWeather() {
        return service.getCurrentWeather();
    }

    @PutMapping
    public GoNg setGoNoGo(@RequestBody GoNg go) {
        return service.setWeatherDepartmentStatus(go.getGong());
    }

    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
