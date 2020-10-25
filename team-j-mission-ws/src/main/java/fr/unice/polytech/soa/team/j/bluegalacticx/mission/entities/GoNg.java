package fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities;

import java.util.Objects;

public class GoNg {
    private boolean gong;

    public GoNg() {
    }

    public GoNg(boolean gong) {
        this.gong = gong;
    }

    public boolean isGong() {
        return this.gong;
    }

    public boolean getGong() {
        return this.gong;
    }

    public void setGong(boolean gong) {
        this.gong = gong;
    }

    public GoNg gong(boolean gong) {
        this.gong = gong;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof GoNg)) {
            return false;
        }
        GoNg goNg = (GoNg) o;
        return gong == goNg.gong;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gong);
    }

    @Override
    public String toString() {
        return "{" + " gong='" + isGong() + "'" + "}";
    }

}
