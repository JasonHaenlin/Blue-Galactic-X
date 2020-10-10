package fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks;

import java.util.*;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;

public class MissionsMocked {
    static public List<Mission> missions;

    static {
        missions = new ArrayList<>();
        // @formatter:off
        missions.add(new Mission()
                .id("1")
                .status(MissionStatus.PENDING)
                .rocketId("1")
                .date(new Date())
        );

        missions.add(new Mission()
                .id("2")
                .status(MissionStatus.STARTED)
                .rocketId("2")
                .date(new Date())
        );
        // @formatter:on
    }

    public static final Optional<Mission> find(String id) {
        return missions.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

}
