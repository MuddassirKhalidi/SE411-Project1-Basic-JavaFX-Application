package se411;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamDataAccess {
    private List<Team> teams;
    
    public TeamDataAccess() {
        this.teams = new ArrayList<>();
    }
    
    public boolean addTeam(Team team) {
        if (team == null || team.getTeamId() == null || team.getTeamId().isEmpty()) {
            Logger.warn("Attempted to add invalid team");
            return false;
        }
        
        boolean exists = teams.stream().anyMatch(t -> t.getTeamId().equals(team.getTeamId()));
        if (exists) {
            Logger.warn("Team with ID " + team.getTeamId() + " already exists");
            return false;
        }
        
        teams.add(team);
        Logger.info("Added team: " + team.getTeamId());
        return true;
    }
    
    public boolean removeTeam(String teamId) {
        if (teamId == null || teamId.isEmpty()) {
            Logger.warn("Attempted to remove team with invalid ID");
            return false;
        }
        
        boolean removed = teams.removeIf(t -> t.getTeamId().equals(teamId));
        if (removed) {
            Logger.info("Removed team: " + teamId);
        } else {
            Logger.warn("Team not found: " + teamId);
        }
        return removed;
    }
    
    public Team getTeam(String teamId) {
        if (teamId == null || teamId.isEmpty()) {
            return null;
        }
        
        Optional<Team> team = teams.stream()
                .filter(t -> t.getTeamId().equals(teamId))
                .findFirst();
        return team.orElse(null);
    }
    
    public List<Team> getAllTeams() {
        return new ArrayList<>(teams);
    }
    
    public boolean updateTeam(String teamId, Team updatedTeam) {
        if (teamId == null || teamId.isEmpty() || updatedTeam == null) {
            Logger.warn("Attempted to update team with invalid data");
            return false;
        }
        
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamId().equals(teamId)) {
                teams.set(i, updatedTeam);
                Logger.info("Updated team: " + teamId);
                return true;
            }
        }
        
        Logger.warn("Team not found for update: " + teamId);
        return false;
    }
}

