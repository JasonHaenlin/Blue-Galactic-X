package fr.unice.polytech.soa.team.j.bluegalacticx.client.api.rocket.entities;

import java.util.Objects;

public class Booster {
    private BoosterStatus status;
    private int fuelLevel;

    public Booster() {
    }

    public Booster(BoosterStatus status, int fuelLevel) {
        this.status = status;
        this.fuelLevel = fuelLevel;
    }

    public BoosterStatus getStatus() {
        return this.status;
    }

    public void setStatus(BoosterStatus status) {
        this.status = status;
    }

    public int getFuelLevel() {
        return this.fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public Booster status(BoosterStatus status) {
        this.status = status;
        return this;
    }

    public Booster fuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Booster)) {
            return false;
        }
        Booster booster = (Booster) o;
        return Objects.equals(status, booster.status) && fuelLevel == booster.fuelLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, fuelLevel);
    }

    @Override
    public String toString() {
        return "{" + " status='" + getStatus() + "'" + ", fuelLevel='" + getFuelLevel() + "'" + "}";
    }

}
