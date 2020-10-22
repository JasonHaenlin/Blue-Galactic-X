package fr.unice.polytech.soa.team.j.bluegalacticx.client.telemetry.entities;

import java.util.Objects;

public class Anomaly {
    private AnomalyType type;
    private AnomalySeverity severity;
    private String reason;

    public Anomaly() {
    }

    public Anomaly(AnomalyType type, AnomalySeverity severity, String reason) {
        this.type = type;
        this.severity = severity;
        this.reason = reason;
    }

    public AnomalyType getType() {
        return this.type;
    }

    public void setType(AnomalyType type) {
        this.type = type;
    }

    public AnomalySeverity getSeverity() {
        return this.severity;
    }

    public void setSeverity(AnomalySeverity severity) {
        this.severity = severity;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Anomaly type(AnomalyType type) {
        this.type = type;
        return this;
    }

    public Anomaly severity(AnomalySeverity severity) {
        this.severity = severity;
        return this;
    }

    public Anomaly reason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Anomaly)) {
            return false;
        }
        Anomaly anomaly = (Anomaly) o;
        return Objects.equals(type, anomaly.type) && Objects.equals(severity, anomaly.severity)
                && Objects.equals(reason, anomaly.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, severity, reason);
    }

    @Override
    public String toString() {
        return "{" + " type='" + getType() + "'" + ", severity='" + getSeverity() + "'" + ", reason='" + getReason()
                + "'" + "}";
    }

}
