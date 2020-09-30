package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import org.springframework.data.mongodb.core.aggregation.ConvertOperators.ToString;

public enum RocketStatus {
    READY("ready"),
    PROBLEM("problem");

    private final String status;

    private RocketStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return status;
    }
    
}
