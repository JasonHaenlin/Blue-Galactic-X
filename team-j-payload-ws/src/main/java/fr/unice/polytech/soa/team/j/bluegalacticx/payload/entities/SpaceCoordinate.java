package fr.unice.polytech.soa.team.j.bluegalacticx.payload.entities;

import java.util.Objects;

public class SpaceCoordinate {
    int latitude;
    int longitude;
    int altitude;


    public SpaceCoordinate() {
    }

    public SpaceCoordinate(int latitude, int longitude, int altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public int getLatitude() {
        return this.latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return this.longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public SpaceCoordinate latitude(int latitude) {
        this.latitude = latitude;
        return this;
    }

    public SpaceCoordinate longitude(int longitude) {
        this.longitude = longitude;
        return this;
    }

    public SpaceCoordinate altitude(int altitude) {
        this.altitude = altitude;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SpaceCoordinate)) {
            return false;
        }
        SpaceCoordinate spaceCoordinate = (SpaceCoordinate) o;
        return latitude == spaceCoordinate.latitude && longitude == spaceCoordinate.longitude && altitude == spaceCoordinate.altitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, altitude);
    }

    @Override
    public String toString() {
        return "{" +
            " latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            "}";
    }

}
