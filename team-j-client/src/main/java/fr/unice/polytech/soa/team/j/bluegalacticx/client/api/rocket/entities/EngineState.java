package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities;

public enum EngineState {
    READY("READY"), INITIALIZATION("INITIALIZATION"), FAILING("FAILING"), STARTING("STARTING");

    private final String state;

    EngineState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }

}
