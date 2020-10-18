package fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.Mission;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities.SpaceCoordinate;
import fr.unice.polytech.soa.team.j.bluegalacticx.mission.proto.MissionStatusRequest.MissionStatus;

public class MissionsMocked {
    static public List<Mission> missions;

    static {
        missions = new ArrayList<>();
        // @formatter:off
        missions.add(new Mission()
                .id("1")
                .destination(new SpaceCoordinate(1,2,3))
                .status(MissionStatus.PENDING)
                .rocketId("1")
                .payloadId("4f6911a8-437a-43fc-adad-a0ed6c6f69a7")
                .date(new Date())
        );

        missions.add(new Mission()
                .id("2")
                .destination(new SpaceCoordinate(3,2,1))
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
