package se411.client;

import java.util.List;

import se411.model.Team;

public class TeamDataAccess {
    private TeamRestClient restClient;
    
    public TeamDataAccess() {
        this.restClient = new TeamRestClient();
    }
    
    public boolean addTeam(Team team) {
        return restClient.addTeam(team);
    }
    
    public boolean removeTeam(String teamId) {
        return restClient.removeTeam(teamId);
    }
    
    public Team getTeam(String teamId) {
        return restClient.getTeam(teamId);
    }
    
    public List<Team> getAllTeams() {
        return restClient.getAllTeams();
    }
    
    public boolean updateTeam(String teamId, Team updatedTeam) {
        return restClient.updateTeam(teamId, updatedTeam);
    }
}

