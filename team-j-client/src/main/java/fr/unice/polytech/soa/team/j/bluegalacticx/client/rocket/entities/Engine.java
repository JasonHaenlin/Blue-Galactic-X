package fr.unice.polytech.soa.team.j.bluegalacticx.client.rocket.entities;

import java.util.Objects;

public class Engine {
    private EngineState left;
    private EngineState right;

    public Engine() {
    }

    public Engine(EngineState left, EngineState right) {
        this.left = left;
        this.right = right;
    }

    public EngineState getLeft() {
        return this.left;
    }

    public void setLeft(EngineState left) {
        this.left = left;
    }

    public EngineState getRight() {
        return this.right;
    }

    public void setRight(EngineState right) {
        this.right = right;
    }

    public Engine left(EngineState left) {
        this.left = left;
        return this;
    }

    public Engine right(EngineState right) {
        this.right = right;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Engine)) {
            return false;
        }
        Engine engine = (Engine) o;
        return Objects.equals(left, engine.left) && Objects.equals(right, engine.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "{" + " left='" + getLeft() + "'" + ", right='" + getRight() + "'" + "}";
    }

}
