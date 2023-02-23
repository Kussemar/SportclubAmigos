package entities;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamId;
    private Sport sport;
    private List<Registration> registrations = new ArrayList<>();

    public Team(String teamId, Sport sport) {
        this.teamId = teamId;
        this.sport = sport;
    }

    public Sport getSport() {
        return sport;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public String getTeamId() {
        return teamId;
    }

    public void addRegistration(Registration registration){
        registrations.add(registration);
    }
}
