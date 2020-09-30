package fr.unice.polytech.soa.team.j.bluegalacticx.weather.replies;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.*;

public class WeatherStatusReply {
    
    private WeatherStatus status;

    public WeatherStatusReply(){}

    public WeatherStatusReply(WeatherStatus status){
        this.status = status;
    }

    public WeatherStatus getStatus(){
        return this.status;
    }

    public void setStatus(WeatherStatus status){
        this.status = status;
    }
}