package fr.unice.polytech.soa.team.j.bluegalacticx.client.controllers;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.RestAPI;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.GoNg;
import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherReport;

public class WeatherREST extends RestAPI {

    public WeatherREST(String uri) {
        super(uri);
    }

    public WeatherReport getCurrentWeather() {
        return get("/", WeatherReport.class);
    }

    public void setGoNoGo(GoNg gonogo) {
        put("/", GoNg.class, gonogo);
    }
}
