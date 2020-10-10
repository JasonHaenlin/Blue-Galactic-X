package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.exceptions;

public class TelemetryDataRocketIdException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public TelemetryDataRocketIdException(){
        super("You need to indicate an ID to create a telemetry for the rocket");
    }
    
}
