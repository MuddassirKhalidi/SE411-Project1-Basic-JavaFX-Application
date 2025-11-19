package se411.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table("teams")
public class Team implements Persistable<String> {
    
    @Id
    @Column("team_id")
    private String teamId;  // Keep as String
    
    @Column("team_name")
    private String teamName;
    
    @Column("number_of_members")
    private int numberOfMembers;
    
    @Transient
    @JsonIgnore  // Don't serialize this field to JSON
    private boolean isNew = false;
    
    // Constructor for creating NEW teams (no ID yet)
    public Team(String teamName, int numberOfMembers) {
        this.teamName = teamName;
        this.numberOfMembers = numberOfMembers;
        this.isNew = true;
    }
    
    // Constructor for existing teams (with ID)
    public Team(String teamId, String teamName, int numberOfMembers) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.numberOfMembers = numberOfMembers;
        this.isNew = false;
    }
    
    // No-arg constructor
    public Team() {
        this.isNew = true;
    }
    
    // All getters and setters...
    public String getTeamId() {
        return teamId;
    }
    
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public int getNumberOfMembers() {
        return numberOfMembers;
    }
    
    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }
    
    // Persistable interface methods
    @Override
    @JsonIgnore  // Don't serialize getId() as "id" in JSON - we already have "teamId"
    public String getId() {
        return teamId;
    }
    
    @Override
    @JsonIgnore  // Don't serialize isNew() as "new" in JSON
    public boolean isNew() {
        return isNew;
    }
    
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    @Override
    public String toString() {
        return "Team{" +
                "teamId='" + teamId + '\'' +
                ", teamName='" + teamName + '\'' +
                ", numberOfMembers=" + numberOfMembers +
                '}';
    }
}