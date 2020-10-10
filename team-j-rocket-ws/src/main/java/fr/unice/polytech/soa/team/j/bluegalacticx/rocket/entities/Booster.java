package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.BoosterDestroyedException;

public class Booster {
    private BoosterStatus status;
    private int fuelLevel = 100;

    public Booster(){}

    public Booster(BoosterStatus status) {
        this.status = status;
    }

    public int getFuelLevel(){
        return this.fuelLevel;
    }

    public Booster fuelLevel(int fuelLevel){
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
}