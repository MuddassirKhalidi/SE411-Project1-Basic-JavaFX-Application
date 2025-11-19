package se411.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se411.model.Team;
import se411.repository.TeamRepository;
import se411.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TeamService {
    
    @Autowired
    private TeamRepository teamRepository;
    
    /**
     * Get all teams from the database
     */
    public List<Team> getAllTeams() {
        try {
            List<Team> teams = new ArrayList<>();
            teamRepository.findAll().forEach(team -> {
                team.setNew(false); // Mark as existing entity
                teams.add(team);
            });
            Logger.info("Retrieved " + teams.size() + " teams from service layer");
            return teams;
        } catch (Exception e) {
            Logger.error("Error in service layer getting all teams: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Get a team by ID
     */
    public Optional<Team> getTeamById(String teamId) {
        try {
            Optional<Team> team = teamRepository.findById(teamId);
            if (team.isPresent()) {
                team.get().setNew(false); // Mark as existing entity
                Logger.info("Found team in service layer: " + teamId);
            } else {
                Logger.warn("Team not found in service layer: " + teamId);
            }
            return team;
        } catch (Exception e) {
            Logger.error("Error in service layer getting team: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Add a new team
     */
    public Team addTeam(Team team) {
        try {
            // Check if team already exists
            if (team.getTeamId() != null && teamRepository.existsById(team.getTeamId())) {
                Logger.warn("Service layer: Team already exists: " + team.getTeamId());
                throw new IllegalArgumentException("Team with ID " + team.getTeamId() + " already exists");
            }
            
            // Mark as new entity so Spring Data JDBC performs INSERT instead of UPDATE
            team.setNew(true);
            
            // Save the team (will INSERT because isNew() returns true)
            Team savedTeam = teamRepository.save(team);
            
            // Mark as not new after save
            savedTeam.setNew(false);
            
            Logger.info("Service layer: Added team: " + savedTeam.getTeamId());
            return savedTeam;
        } catch (Exception e) {
            Logger.error("Error in service layer adding team: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Update an existing team
     */
    public Team updateTeam(String teamId, Team updatedTeam) {
        try {
            if (!teamRepository.existsById(teamId)) {
                Logger.warn("Service layer: Team not found for update: " + teamId);
                throw new IllegalArgumentException("Team with ID " + teamId + " not found");
            }
            
            updatedTeam.setTeamId(teamId);
            // Mark as existing entity so Spring Data JDBC performs UPDATE instead of INSERT
            updatedTeam.setNew(false);
            
            // Save the team (will UPDATE because isNew() returns false)
            Team savedTeam = teamRepository.save(updatedTeam);
            
            // Ensure it's still marked as not new after save
            savedTeam.setNew(false);
            
            Logger.info("Service layer: Updated team: " + teamId);
            return savedTeam;
        } catch (Exception e) {
            Logger.error("Error in service layer updating team: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Delete a team
     */
    public boolean deleteTeam(String teamId) {
        try {
            if (!teamRepository.existsById(teamId)) {
                Logger.warn("Service layer: Team not found for deletion: " + teamId);
                return false;
            }
            
            teamRepository.deleteById(teamId);
            Logger.info("Service layer: Deleted team: " + teamId);
            return true;
        } catch (Exception e) {
            Logger.error("Error in service layer deleting team: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Check if a team exists
     */
    public boolean teamExists(String teamId) {
        return teamRepository.existsById(teamId);
    }
}