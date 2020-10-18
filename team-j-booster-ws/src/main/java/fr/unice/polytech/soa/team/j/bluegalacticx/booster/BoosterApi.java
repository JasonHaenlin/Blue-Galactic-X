package fr.unice.polytech.soa.team.j.bluegalacticx.booster;

import org.springframework.stereotype.Service;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.mocks.BoostersMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.booster.entities.Booster;
import java.util.List;

@Service
public class BoosterApi {
    public List<Booster> updateBoosterMetricsAndRetrieve() {
        BoostersMocked.nextMetrics();
        return BoostersMocked.getAll();
    }
}
