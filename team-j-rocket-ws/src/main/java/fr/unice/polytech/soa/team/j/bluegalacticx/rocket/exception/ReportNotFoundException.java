package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception;

public class ReportNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ReportNotFoundException() {
        super("no report has been submitted");
    }

}
