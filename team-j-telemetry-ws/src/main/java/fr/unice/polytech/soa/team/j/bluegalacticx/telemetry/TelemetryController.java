package fr.unice.polytech.soa.team.j.bluegalacticx.telemetry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.telemetry.entities.Anomaly;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {

    @Autowired
    private TelemetryService telemetryService;

    @GetMapping("/anomalies")
    public List<Anomaly> checkForAnomalies() {
        return telemetryService.checkRecordedAnomalies();
    }

}
