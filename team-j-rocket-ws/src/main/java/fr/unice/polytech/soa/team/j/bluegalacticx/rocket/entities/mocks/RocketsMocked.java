package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Engine;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.EngineState;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketMetrics;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;

public class RocketsMocked {
    static public List<Rocket> rockets;

    static {
        rockets = new ArrayList<>();
        try {
            // @formatter:off
            rockets.add(new Rocket()
                    .id("1")
                    .status(RocketStatus.AT_BASE)
                    .report(new RocketReport()
                        .fuelLevel(50)
                        .remainingEstimatedTime(160)
                        .engine(new Engine(EngineState.INITIALIZATION, EngineState.READY))
                        .overallResult("No problem, the preparation of the rocket goes well."))
                    .metrics(new RocketMetrics()
                        .boosterRGA(5)
                        .irradiance(350)
                        .temperature(300)
                        .vibration(400)
                        .midRocketRGA(9))
                    );

            rockets.add(new Rocket()
                    .id("2")
                    .status(RocketStatus.UNDER_CONSTRUCTION)
                    .report(new RocketReport()
                        .fuelLevel(0)
                        .engine(new Engine(EngineState.INITIALIZATION, EngineState.INITIALIZATION))
                        .overallResult("Rocket creation in progress"))
                    .metrics(new RocketMetrics()
                        .boosterRGA(5)
                        .irradiance(10)
                        .temperature(25)
                        .vibration(0)
                        .midRocketRGA(9))
                    );
            // @formatter:on
        } catch (RocketDestroyedException e) {
            e.printStackTrace();
        }
    }

    public static final Optional<Rocket> find(String id) {
        return rockets.stream().filter(r -> r.getId().equals(id)).findFirst();
    }
}
