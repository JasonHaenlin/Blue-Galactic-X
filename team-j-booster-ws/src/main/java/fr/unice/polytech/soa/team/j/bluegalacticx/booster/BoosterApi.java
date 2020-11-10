package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.utils.RandomUtils;

@Service
public class BoosterApi {

    BoosterTelemetry boosterTelemetry;
    public BoosterApi initTelemetry() {
        this.boosterTelemetry = new BoosterTelemetry().fuel(100).speed(0).distanceFromEarth(0);
        return this;
    }

    public void nextTelemetry(Booster b) {
        double gainedSpeed = 0;
        if (b.getStatus() == BoosterStatus.LANDING) {
            gainedSpeed = RandomUtils.randomDouble(-40, -20);
        }
        if (b.getStatus() == BoosterStatus.RUNNING) {
            gainedSpeed = RandomUtils.randomDouble(40, 20);
        }
        if(b.getStatus()== BoosterStatus.LANDED){
            gainedSpeed = -b.getSpeed();
        }
        b.setSpeed(b.getSpeed() + gainedSpeed);
        b.setDistanceFromEarth(b.getDistanceFromEarth() + gainedSpeed);

    }

}
