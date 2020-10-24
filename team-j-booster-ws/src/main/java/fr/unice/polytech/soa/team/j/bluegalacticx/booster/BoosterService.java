package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.stereotype.Service;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.mocks.BoostersMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterNotAvailableException;

@Service
public class BoosterService {

    public void updateAllBoostersState() {
        List<Booster> boosters = BoostersMocked.getAll();
        for (Booster b : boosters) {
            b.updateState();
        }
    }

    public void updateBoostersSpeed(double power) {
        for (Booster b : BoostersMocked.getAll()) {
            if (b.getStatus() == BoosterStatus.RUNNING) {
                b.updatePower(power);
            }
        }
    }

    public String getAvailableBoosterID() throws BoosterNotAvailableException {
        List<Booster> boosters = BoostersMocked.getAll();
        for (Booster b : boosters) {
            if (b.getStatus() == BoosterStatus.READY) {
                return b.getId();
            }
        }
        throw new BoosterNotAvailableException();
    }

}
