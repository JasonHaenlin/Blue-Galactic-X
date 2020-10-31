package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.stereotype.Service;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.MaxQ;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;

@Service
public class RocketApi {

    // mocked data
    private int iteration = 40;
    private Double mockDistStepFix = 2.0;
    private Double mockFuelStep = null;
    private Double mockDistStep = null;
    private SpaceCoordinate origin = new SpaceCoordinate(0, 0, 0);
    private double distance;

    public RocketApi withOriginCoordinate(SpaceCoordinate origin) {
        this.origin = origin;
        return this;
    }

    public RocketApi withNumberOfIteration(int number) {
        this.iteration = number;
        return this;
    }

    public SpaceTelemetry launchWhenReady(SpaceCoordinate objectiveCoordinates, String rocketId) {
        Rocket r = RocketsMocked.find(rocketId).get();
        this.distance = computeDistance(origin, objectiveCoordinates);
        this.mockDistStep = mockDistStepFix;
        return r.getTelemetryInAir().totalDistance(distance).distance(distance).rocketId(rocketId);
    }

    private boolean isRocketNotPassedMaxQ(Rocket rocket) {
        if (rocket.getTelemetryInAir().getDistance() >= rocket.getTelemetryInAir().getTotalDistance() - MaxQ.MAX) {
            return true;
        }
        return false;
    }

    public SpaceTelemetry retrieveLastTelemetry(String rocketId) {
        Rocket r = RocketsMocked.find(rocketId).get();
        if (this.mockDistStep == null) {
            return r.getTelemetryInGround();
        }
        if (isRocketNotPassedMaxQ(r)) {
            this.mockDistStep = this.mockDistStepFix;
        } else {
            this.mockDistStep = distance / this.iteration;
        }
        return r.nextTelemetry(this.mockDistStep, this.mockFuelStep);
    }

    private int computeDistance(SpaceCoordinate from, SpaceCoordinate to) {
        return (int) Math.round(
                Math.sqrt(pow2(from.getX(), to.getX()) + pow2(from.getY(), to.getY()) + pow2(from.getZ(), to.getZ())));
    }

    private double pow2(int a1, int a2) {
        return Math.pow(a1 - a2, 2);
    }

}
