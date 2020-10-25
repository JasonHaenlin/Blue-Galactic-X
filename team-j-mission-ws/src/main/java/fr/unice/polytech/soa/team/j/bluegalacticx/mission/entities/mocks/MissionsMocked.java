package fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.MissionStatus;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;

public class MissionsMocked {

    public static final List<Mission> missions;

    static {
        missions = new ArrayList<>();
        // @formatter:off
        missions.add(new Mission()
                .id("10")
                .destination(new SpaceCoordinate(1000,2000,3000))
                .status(MissionStatus.PENDING)
                .rocketId("10")
                .date(new Date())
        );

        missions.add(new Mission()
                .id("20")
                .destination(new SpaceCoordinate(3000,2000,1000))
                .status(MissionStatus.STARTED)
                .rocketId("20")
                .date(new Date())
        );
        // @formatter:on
    }

    public static final Optional<Mission> find(String id) {
        return missions.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public static final Optional<Mission> findByRocketId(String id) {
        return missions.stream().filter(r -> r.getRocketId().equals(id)).findFirst();
    }

}
