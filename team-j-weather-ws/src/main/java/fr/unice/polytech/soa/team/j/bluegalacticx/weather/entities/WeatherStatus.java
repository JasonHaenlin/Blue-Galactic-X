package fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities;

public enum WeatherStatus {
    SUNNY("sunny"),
    CLOUDY("cloudy");

    public final String status;

    private WeatherStatus(String status){
        this.status = status;
    }
}
