package se411;

import javafx.application.Application;
import javafx.stage.Stage;

public class TeamManagerApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Logger.info("Application starting");
        
        TeamDataAccess teamDataAccess = new TeamDataAccess();
        MainGUI mainGUI = new MainGUI(primaryStage, teamDataAccess);
        
        try {
            mainGUI.show();
        } catch (Exception e) {
            Logger.error("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void stop() {
        Logger.info("Application closing");
        Logger.close();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

