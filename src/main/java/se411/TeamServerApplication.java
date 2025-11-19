package se411;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se411.util.Logger;

@SpringBootApplication
public class TeamServerApplication {
    
    public static void main(String[] args) {
        try {
            SpringApplication.run(TeamServerApplication.class, args);
            Logger.info("Spring Boot server started successfully on port 8080");
        } catch (Exception e) {
            Logger.error("Failed to start Spring Boot server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

