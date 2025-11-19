package se411.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import se411.model.Team;
import se411.service.TeamService;
import se411.util.Logger;

import java.util.List;
import java.util.Optional;
import se411.util.*;
@RestController
@RequestMapping("/api/teams")
public class TeamController {
    
    @Autowired
    private TeamService teamService;  // Use service instead of repository!
    
    @PostMapping
    public ResponseEntity<?> addTeam(@RequestBody Team team) {
        try {
            if (team == null || team.getTeamId() == null || team.getTeamId().isEmpty()) {
                Logger.warn("Attempted to add invalid team");
                return ResponseEntity.badRequest()
                    .body("Team ID is required and cannot be empty");
            }
            
            Team savedTeam = teamService.addTeam(team);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTeam);
        } catch (IllegalArgumentException e) {
            Logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            Logger.error("Error adding team: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error adding team: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable String id) {
        try {
            Optional<Team> team = teamService.getTeamById(id);
            if (team.isPresent()) {
                return ResponseEntity.ok(team.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Team with ID " + id + " not found");
            }
        } catch (Exception e) {
            Logger.error("Error retrieving team: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving team: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllTeams() {
        try {
            List<Team> teams = teamService.getAllTeams();
            return ResponseEntity.ok(teams);
        } catch (Exception e) {
            Logger.error("Error retrieving all teams: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving teams: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable String id, @RequestBody Team team) {
        try {
            Team updatedTeam = teamService.updateTeam(id, team);
            return ResponseEntity.ok(updatedTeam);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            Logger.error("Error updating team: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating team: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable String id) {
        try {
            if (teamService.deleteTeam(id)) {
                return ResponseEntity.ok("Team deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Team with ID " + id + " not found");
            }
        } catch (Exception e) {
            Logger.error("Error deleting team: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error deleting team: " + e.getMessage());
        }
    }
}