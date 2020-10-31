package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Engine;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.EngineState;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;

public class RocketsMocked {
    static public List<Rocket> rockets;
    private static List<SpaceTelemetry> inAir;
    private static List<SpaceTelemetry> inGround;

    private static void presetSpaceTelemetry() {
        // @formatter:off
        inAir =  new ArrayList<SpaceTelemetry>();
                    inAir.add(new SpaceTelemetry()
                    .rocketId("1")
                    .heatShield(95)
                    .speed(950)
                    .distance(500)
                    .irradiance(10)
                    .velocityVariation(15)
                    .temperature(200)
                    .vibration(30)
                    .boosterRGA(30)
                    .midRocketRGA(35));
                    inAir.add(new SpaceTelemetry()
                    .rocketId("2")
                    .heatShield(95)
                    .speed(1000)
                    .distance(550)
                    .irradiance(10)
                    .velocityVariation(15)
                    .temperature(250)
                    .vibration(30)
                    .boosterRGA(20)
                    .midRocketRGA(35));

        inGround = new ArrayList<SpaceTelemetry>();
                    inGround.add(new SpaceTelemetry()
                    .rocketId("1")
                    .heatShield(100)
                    .speed(0)
                    .distance(0)
                    .irradiance(10)
                    .velocityVariation(15)
                    .temperature(50)
                    .vibration(40)
                    .boosterRGA(30)
                    .midRocketRGA(35));
                    inGround.add(new SpaceTelemetry()
                    .rocketId("2")
                    .heatShield(95)
                    .speed(0)
                    .distance(0)
                    .irradiance(30)
                    .velocityVariation(15)
                    .temperature(40)
                    .vibration(40)
                    .boosterRGA(20)
                    .midRocketRGA(35));
        // @formatter:on
    }

    static {
        
        presetSpaceTelemetry();
        reset();
    }

    public static final void reset() {
        rockets = new ArrayList<>();
        try {
            // @formatter:off
            rockets.add(new Rocket()
                    .id("1")
                    .status(RocketStatus.AT_BASE)
                    .spaceCoordinate(new SpaceCoordinate(10,10,10))
                    .report(new RocketReport()
                        .fuelLevel(50)
                        .remainingEstimatedTime(160)
                        .engine(new Engine(EngineState.INITIALIZATION, EngineState.READY))
                        .overallResult("No problem, the preparation of the rocket goes well."))
                        .inAir(findSpaceTelemetryInAir("1").get()).inGround(findSpaceTelemetryInGround("1").get()));

            rockets.add(new Rocket()
                    .id("2")
                    .status(RocketStatus.UNDER_CONSTRUCTION)
                    .spaceCoordinate(new SpaceCoordinate(10,10,10))
                    .report(new RocketReport()
                        .fuelLevel(0)
                        .engine(new Engine(EngineState.INITIALIZATION, EngineState.INITIALIZATION))
                        .overallResult("Rocket creation in progress"))
                    .inAir(findSpaceTelemetryInAir("2").get()).inGround(findSpaceTelemetryInAir("2").get()));
            // @formatter:on
        } catch (RocketDestroyedException e) {
            e.printStackTrace();
        }
    }

    public static final Optional<Rocket> find(String id) {
        return rockets.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public static final Optional<SpaceTelemetry> findSpaceTelemetryInAir(String id) {
        return inAir.stream().filter(r -> r.getRocketId().equals(id)).findFirst();
    }

    public static final Optional<SpaceTelemetry> findSpaceTelemetryInGround(String id) {
        return inGround.stream().filter(r -> r.getRocketId().equals(id)).findFirst();
    }

}
