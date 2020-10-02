package fr.unice.polytech.soa.team.j.bluegalacticx.mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.replies.MissionReply;

@RestController
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService service;

    @GetMapping
    public MissionReply createNewMission(@RequestParam(value = "name", defaultValue = "testMission") String name) {
        return service.createMission(name);
    }
}
