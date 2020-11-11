package fr.unice.polytech.soa.team.j.bluegalacticx.missioncontrol.entities;

import java.util.Objects;

public class SpaceCoordinate {
    int x;
    int y;
    int z;

    public SpaceCoordinate() {
    }

    public SpaceCoordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public SpaceCoordinate x(int x) {
        this.x = x;
        return this;
    }

    public SpaceCoordinate y(int y) {
        this.y = y;
        return this;
    }

    public SpaceCoordinate z(int z) {
        this.z = z;
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
        return x == spaceCoordinate.x && y == spaceCoordinate.y && z == spaceCoordinate.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "{" + " x='" + getX() + "'" + ", y='" + getY() + "'" + ", z='" + getZ() + "'" + "}";
    }

}
