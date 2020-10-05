package fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.mocks;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.soa.team.j.bluegalacticx.weather.entities.WeatherBeacon;

public class WeatherBeaconsMocked {
    static public final List<WeatherBeacon> beacons;

    static {
        // @formatter:off
        beacons = new ArrayList<>();
        beacons.add(new WeatherBeacon().label("WEST01")
                        .pstatm(1005.0)
                        .pmerm(1013.4)
                        .tmmoy(20.2)
                        .tmsigma(4.30)
                        .txmoy(25.5)
                        .tnmoy(16.1)
                        .tsvmoy(16.6)
                        .rr(120.0));
        beacons.add(new WeatherBeacon().label("EAST01")
                        .pstatm(1007.5)
                        .pmerm(1013.4)
                        .tmmoy(21.2)
                        .tmsigma(4.60)
                        .txmoy(26.9)
                        .tnmoy(16.1)
                        .tsvmoy(15.9)
                        .rr(64.0));
        beacons.add(new WeatherBeacon().label("NORTH01")
                        .pstatm(1012.3)
                        .pmerm(1013.4)
                        .tmmoy(18.1)
                        .tmsigma(1.50)
                        .txmoy(20.9)
                        .tnmoy(16.1)
                        .tsvmoy(14.8)
                        .rr(63.0));
        beacons.add(new WeatherBeacon().label("SOUTH01")
                        .pstatm(996.0)
                        .pmerm(1013.7)
                        .tmmoy(19.7)
                        .tmsigma(4.20)
                        .txmoy(26.0)
                        .tnmoy(14.7)
                        .tsvmoy(16.2)
                        .rr(60.0));
        // @formatter:on
    }

    private WeatherBeaconsMocked() {
    }

}
