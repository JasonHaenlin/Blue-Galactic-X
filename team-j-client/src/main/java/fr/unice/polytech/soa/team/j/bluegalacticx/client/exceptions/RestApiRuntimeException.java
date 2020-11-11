package fr.unice.polytech.soa.team.j.bluegalacticx.client.exceptions;

public class RestApiRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestApiRuntimeException(Throwable e) {
        super(e);
    }

}
