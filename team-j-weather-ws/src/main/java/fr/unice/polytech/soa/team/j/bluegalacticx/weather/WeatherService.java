package fr.unice.polytech.soa.team.j.bluegalacticx.weather;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.mocks.WeatherBeaconsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.exceptions.NoWeatherReportExceptions;

@Service
public class WeatherService {

    private WeatherReport report;

    public WeatherStatus getCurrentWeather() {
        return new WeatherStatus().beacons(WeatherBeaconsMocked.beacons).date(Calendar.getInstance());
    }

    public WeatherReport getLastReport() throws NoWeatherReportExceptions {
        checkReport(this.report);
        return report;
    }

    public void setReport(WeatherReport report) throws NoWeatherReportExceptions {
        checkReport(report);
        report.setDate(Calendar.getInstance());
        this.report = report;
    }

    private void checkReport(WeatherReport report) throws NoWeatherReportExceptions {
        if (report == null) {
            throw new NoWeatherReportExceptions();
        }
    }

}
