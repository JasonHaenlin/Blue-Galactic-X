package fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterMetrics;

public class BoostersMocked {
    static public List<Booster> boosters;

    static {
        reset();
    }

    public static final void reset() {
        boosters = new ArrayList<>();
        // @formatter:off
        boosters.add(new Booster()
                .id("1")
                .status(BoosterStatus.READY)
                .fuelLevel(100)
                );

        boosters.add(new Booster()
                .id("2")
                .status(BoosterStatus.RUNNING)
                .fuelLevel(40)
                .metrics(new BoosterMetrics(500, 200))
                );
        // @formatter:on

    }

    public static final void nextMetrics() {
        for(Booster b : boosters){
            double gainedSpeed = 0;
            if(b.getStatus() == BoosterStatus.LANDING){
                gainedSpeed = randomDouble(-40, -20);
            }
            b.setSpeed(b.getSpeed() + gainedSpeed);
            b.setDistanceFromEarth(b.getDistanceFromEarth() + gainedSpeed);
        }
    }

    public static final Optional<Booster> find(String id) {
        return boosters.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    public static final List<Booster> getAll(){
        return boosters;
    }

    private static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }
}
