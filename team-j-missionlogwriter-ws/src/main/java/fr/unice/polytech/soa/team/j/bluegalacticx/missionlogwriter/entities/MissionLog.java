package fr.unice.polytech.soa.team.j.bluegalacticx.missionlogwriter.entities;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "missionLog")
public class MissionLog {

    @Id
    private String id;
    private List<Log> logs;

    public MissionLog() {
    }

    public MissionLog(String id) {
        this.id = id;
    }

    public void addLog(String text){
        this.logs.add(new Log(text));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Log> getLogs() {
        return this.logs;
    }

    public void setLog(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MissionLog)) {
            return false;
        }
        MissionLog missionLog = (MissionLog) o;
        return Objects.equals(logs, missionLog.logs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logs);
    }

    @Override
    public String toString() {
        return "{ logs='" + getLogs() + "' }";
    }
}
