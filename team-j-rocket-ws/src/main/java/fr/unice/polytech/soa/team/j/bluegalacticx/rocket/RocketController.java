package fr.unice.polytech.soa.team.j.bluegalacticx.rocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.entities.RocketReport;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.exception.ReportNotFoundException;
import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.replies.RocketStatusReply;

@RestController
@RequestMapping("/rocket")
@ResponseBody
public class RocketController {

    @Autowired
    private RocketService service;

    @GetMapping("/status")
    public RocketStatusReply getRocketStatus() {
        return service.getStatus();
    }

    @PostMapping("/report")
    public void postRocketReport(@RequestBody RocketReport report) {
        service.submitReport(report);
    }

    @GetMapping("/report")
    public RocketReport getRocketReport() {
        try {
            return service.getReport();
        } catch (ReportNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
