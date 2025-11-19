package se411.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import se411.model.Team;
import se411.util.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import se411.util.*;
public class TeamRestClient {
    private static final String BASE_URL = "http://localhost:8080/api/teams";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public TeamRestClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    
    public boolean addTeam(Team team) {
        try {
            // Log what we're about to send
            Logger.info("Attempting to add team: " + team.toString());
            
            String json = objectMapper.writeValueAsString(team);
            Logger.info("JSON being sent: " + json);  // Debug: see the actual JSON
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            Logger.info("Response status: " + response.statusCode());
            Logger.info("Response body: " + response.body());  // Debug: see server response
            
            if (response.statusCode() == 201) {
                Logger.info("Team added via REST API: " + team.getTeamId());
                return true;
            } else {
                Logger.warn("Failed to add team via REST API. Status: " + response.statusCode());
                Logger.warn("Server response: " + response.body());
                return false;
            }
        } catch (Exception e) {
            Logger.error("Error adding team via REST API: " + e.getMessage());
            e.printStackTrace();  // Print full stack trace
            return false;
        }
    }
    
    public Team getTeam(String teamId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + teamId))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Team.class);
            } else {
                Logger.warn("Team not found via REST API: " + teamId);
                return null;
            }
        } catch (Exception e) {
            Logger.error("Error retrieving team via REST API: " + e.getMessage());
            return null;
        }
    }
    
    public List<Team> getAllTeams() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                TypeReference<List<Team>> typeRef = new TypeReference<List<Team>>() {};
                return objectMapper.readValue(response.body(), typeRef);
            } else {
                Logger.warn("Failed to retrieve teams via REST API. Status: " + response.statusCode());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            Logger.error("Error retrieving all teams via REST API: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean removeTeam(String teamId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + teamId))
                .DELETE()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200 || response.statusCode() == 204) {
                Logger.info("Team removed via REST API: " + teamId);
                return true;
            } else {
                Logger.warn("Failed to remove team via REST API. Status: " + response.statusCode());
                return false;
            }
        } catch (Exception e) {
            Logger.error("Error removing team via REST API: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateTeam(String teamId, Team updatedTeam) {
        try {
            String json = objectMapper.writeValueAsString(updatedTeam);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + teamId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                Logger.info("Team updated via REST API: " + teamId);
                return true;
            } else {
                Logger.warn("Failed to update team via REST API. Status: " + response.statusCode());
                return false;
            }
        } catch (Exception e) {
            Logger.error("Error updating team via REST API: " + e.getMessage());
            return false;
        }
    }
}

