package fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.mocks;

import java.util.Calendar;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherType;

public class WeatherReportMocked {

    static public WeatherReport reports;

    static {
        reports = new WeatherReport().avgHumidity(80).avgRainfall(30).avgWind(10).overallResult("RAS")
                .date(Calendar.getInstance()).weatherType(WeatherType.SUNNY).avgTemperature(30);
    }

}
