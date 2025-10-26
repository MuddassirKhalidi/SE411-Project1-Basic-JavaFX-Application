package se411;

public class Team {
    private String teamId;
    private String teamName;
    private int numberOfMembers;
    
    public Team() {
    }
    
    public Team(String teamId, String teamName, int numberOfMembers) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.numberOfMembers = numberOfMembers;
    }
    
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
    
    @Override
    public String toString() {
        return "Team{" +
                "teamId='" + teamId + '\'' +
                ", teamName='" + teamName + '\'' +
                ", numberOfMembers=" + numberOfMembers +
                '}';
    }
}

