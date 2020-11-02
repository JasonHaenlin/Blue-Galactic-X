package fr.unice.polytech.soa.team.j.bluegalacticx.rocket.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.RocketService;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.exceptions.RocketDestroyedException;

@Component
public class RocketScheduler {

    @Autowired
    private RocketService rocketService;

    @Scheduled(fixedDelay = 1000)
    public void scheduleRocketTelemetryTask() throws RocketDestroyedException {
        rocketService.updateTelemetryToSend();
    }
}
