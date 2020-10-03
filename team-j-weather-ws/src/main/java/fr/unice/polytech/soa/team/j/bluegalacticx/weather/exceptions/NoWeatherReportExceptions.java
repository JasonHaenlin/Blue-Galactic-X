package fr.unice.polytech.soa.team.j.bluegalacticx.weather.exceptions;

public class NoWeatherReportExceptions extends Exception {

    private static final long serialVersionUID = 1L;

    public NoWeatherReportExceptions() {
        super("No report are currently available at the moment");
    }

}
