package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.stereotype.Service;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.mocks.BoostersMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.BoosterStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterDoesNotExistException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.BoosterNotAvailableException;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.exceptions.CannotBeNullException;

@Service
public class BoosterService {

    public void addNewBooster(Booster booster) throws CannotBeNullException {
        if (booster == null) {
            throw new CannotBeNullException("Booster");
        }
        booster.initStatus();
        BoostersMocked.boosters.add(booster);
    }

    public Booster retrieveBooster(String boosterId) throws BoosterDoesNotExistException {
        for (Booster b : BoostersMocked.boosters) {
            if (b.getId().equals(boosterId)) {
                return b;
            }
        }
        throw new BoosterDoesNotExistException(boosterId);
    }

    public void updateAllBoostersState() {
        List<Booster> boosters = BoostersMocked.getAll();
        for (Booster b : boosters) {
            b.updateState();
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
