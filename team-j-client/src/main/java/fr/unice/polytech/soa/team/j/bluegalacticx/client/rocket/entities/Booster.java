package fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities;

import fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities.exceptions.BoosterDestroyedException;

public class Booster {
    private BoosterStatus status;
    private int fuelLevel = 100;

    public Booster() {
    }

    public Booster(BoosterStatus status) {
        this.status = status;
    }

    public int getFuelLevel() {
        return this.fuelLevel;
    }

    public void reduceFuel(int fuel) {
        this.fuelLevel -= fuel;
        if (this.fuelLevel < 0) {
            this.fuelLevel = 0;
        }
    }

    public BoosterStatus getStatus() {
        return status;
    }

    public Booster fuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
        return this;
    }

    public Booster status(BoosterStatus status) throws BoosterDestroyedException {
        throwIfBoosterIsDestroyed("Cannot change the status of a destroyed rocket");
        this.status = status;
        return this;
    }

    private void throwIfBoosterIsDestroyed(String msg) throws BoosterDestroyedException {
        if (status == BoosterStatus.DESTROYED) {
            throw new BoosterDestroyedException(msg);
        }
    }

    @Override
    public String toString() {
        return "{" + " status='" + status + "'" + ", fuelLevel='" + fuelLevel + "'" + "}";
    }

}
