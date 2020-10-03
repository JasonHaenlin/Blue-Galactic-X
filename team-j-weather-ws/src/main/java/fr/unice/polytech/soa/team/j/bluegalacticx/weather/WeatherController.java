package fr.unice.polytech.soa.team.j.bluegalacticx.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.exceptions.NoWeatherReportExceptions;

@RestController
@RequestMapping("/weather")
@ResponseBody
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping
    public WeatherStatus getCurrentWeather() {
        return service.getCurrentWeather();
    }

    @GetMapping("/report")
    public WeatherReport getWeatherReport() {
        try {
            return service.getLastReport();
        } catch (NoWeatherReportExceptions e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/report")
    public void postWeatherReport(@RequestBody WeatherReport report) {
        try {
            service.setReport(report);
        } catch (NoWeatherReportExceptions e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
