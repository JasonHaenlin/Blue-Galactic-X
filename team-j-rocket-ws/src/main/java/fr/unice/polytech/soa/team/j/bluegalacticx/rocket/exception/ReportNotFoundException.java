package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception;

public class ReportNotFoundException extends Exception {
    
    public ReportNotFoundException() {
        super("no report has been submitted");
    }

}
