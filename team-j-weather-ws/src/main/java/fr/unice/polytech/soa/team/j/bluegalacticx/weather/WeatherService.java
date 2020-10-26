package fr.unice.polytech.soa.team.j.bluegalacticx.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.mocks.WeatherReportMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.kafka.DepartmentStatusProducer;

@Service
public class WeatherService {

    @Autowired
    private DepartmentStatusProducer departmentStatusProducer;

    public WeatherReport getCurrentWeather() {
        return WeatherReportMocked.reports;
    }

    public void setWeatherDepartmentStatus(boolean go) {
        departmentStatusProducer.notifyDepartmentStatus(go);
    }

}
