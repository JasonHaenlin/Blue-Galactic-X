package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.Rocket;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpaceTelemetry;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.SpeedChange;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.mocks.RocketsMocked;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.RocketStatusProducer;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.kafka.producers.TelemetryRocketProducer;

@Component
public class RocketScheduler {

    private SpaceTelemetry st = new SpaceTelemetry();

    @Autowired
    private RocketStatusProducer rocketProducer;

    @Autowired
    private TelemetryRocketProducer telemetryRocketProducer;

    @Scheduled(fixedDelay = 1000)
    public void scheduleRocketTelemetryTask() throws RocketDestroyedException {
        for (Rocket r : RocketsMocked.rockets) {
            st = r.getLastTelemetry();
            telemetryRocketProducer.sendTelemetryRocketEvent(st);

            if (r.isRocketInMaxQ() && r.getStatus() != RocketStatus.ENTER_MAXQ) {
                r.changeRocketStatus(RocketStatus.ENTER_MAXQ);
                r.updateSpeed(SpeedChange.DECREASE);
            } else if (!r.isRocketInMaxQ() && r.getStatus() == RocketStatus.ENTER_MAXQ) {
                r.changeRocketStatus(RocketStatus.QUIT_MAXQ);
                r.updateSpeed(SpeedChange.INCREASE);
            }

            if (st.getDistance() <= 0 && r.getStatus() != RocketStatus.ARRIVED) {
                r.arrivedAtDestination();
                rocketProducer.donedRocketEvent(st.getRocketId());
            }

        }

    }
}
