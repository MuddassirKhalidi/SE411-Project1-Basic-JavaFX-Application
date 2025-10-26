package se411;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI {
    private Stage stage;
    private TeamDataAccess teamDataAccess;
    private TableView<Team> table;
    private ObservableList<Team> teamList;
    
    public MainGUI(Stage stage, TeamDataAccess teamDataAccess) {
        this.stage = stage;
        this.teamDataAccess = teamDataAccess;
        this.teamList = FXCollections.observableArrayList();
    }
    
    public void show() {
        stage.setTitle("Team Manager Application");
        
        // Menu Bar
        MenuBar menuBar = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> {
            Logger.info("Exit menu selected");
            stage.close();
        });
        fileMenu.getItems().add(exitItem);
        
        Menu teamMenu = new Menu("Team");
        MenuItem addTeamItem = new MenuItem("Add Team");
        addTeamItem.setOnAction(e -> showAddTeamDialog());
        
        MenuItem editTeamItem = new MenuItem("Edit Team");
        editTeamItem.setOnAction(e -> showEditTeamDialog());
        
        MenuItem deleteTeamItem = new MenuItem("Delete Team");
        deleteTeamItem.setOnAction(e -> showDeleteTeamDialog());
        
        teamMenu.getItems().addAll(addTeamItem, editTeamItem, deleteTeamItem);
        
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAboutWindow());
        helpMenu.getItems().add(aboutItem);
        
        menuBar.getMenus().addAll(fileMenu, teamMenu, helpMenu);
        
        // Table
        table = new TableView<>();
        
        TableColumn<Team, String> idColumn = new TableColumn<>("Team ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("teamId"));
        idColumn.setPrefWidth(100);
        
        TableColumn<Team, String> nameColumn = new TableColumn<>("Team Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        nameColumn.setPrefWidth(200);
        
        TableColumn<Team, Integer> membersColumn = new TableColumn<>("Number of Members");
        membersColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfMembers"));
        membersColumn.setPrefWidth(150);
        
        table.getColumns().addAll(idColumn, nameColumn, membersColumn);
        table.setItems(teamList);
        
        // Refresh table
        refreshTable();
        
        // Buttons
        Button addButton = new Button("Add Team");
        addButton.setOnAction(e -> showAddTeamDialog());
        
        Button editButton = new Button("Edit Team");
        editButton.setOnAction(e -> showEditTeamDialog());
        
        Button deleteButton = new Button("Delete Team");
        deleteButton.setOnAction(e -> showDeleteTeamDialog());
        
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> refreshTable());
        
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addButton, editButton, deleteButton, refreshButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        
        // Layout
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(table);
        root.setBottom(buttonBox);
        
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    
    private void refreshTable() {
        teamList.clear();
        teamList.addAll(teamDataAccess.getAllTeams());
        Logger.info("Table refreshed");
    }
    
    private void showAddTeamDialog() {
        Dialog<Team> dialog = new Dialog<>();
        dialog.setTitle("Add Team");
        dialog.setHeaderText("Enter team information");
        
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        
        TextField idField = new TextField();
        idField.setPromptText("Team ID");
        
        TextField nameField = new TextField();
        nameField.setPromptText("Team Name");
        
        TextField membersField = new TextField();
        membersField.setPromptText("Number of Members");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(
            new Label("Team ID:"), idField,
            new Label("Team Name:"), nameField,
            new Label("Number of Members:"), membersField
        );
        
        dialog.getDialogPane().setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    String id = idField.getText().trim();
                    String name = nameField.getText().trim();
                    int members = Integer.parseInt(membersField.getText().trim());
                    
                    if (id.isEmpty() || name.isEmpty()) {
                        Logger.warn("Invalid team data entered");
                        showAlert("Error", "All fields must be filled");
                        return null;
                    }
                    
                    return new Team(id, name, members);
                } catch (NumberFormatException e) {
                    Logger.error("Invalid number format: " + e.getMessage());
                    showAlert("Error", "Invalid number of members");
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(team -> {
            if (teamDataAccess.addTeam(team)) {
                refreshTable();
                showAlert("Success", "Team added successfully");
            } else {
                showAlert("Error", "Failed to add team. Team ID may already exist.");
            }
        });
    }
    
    private void showEditTeamDialog() {
        Team selectedTeam = table.getSelectionModel().getSelectedItem();
        
        if (selectedTeam == null) {
            showAlert("Error", "Please select a team to edit");
            return;
        }
        
        Dialog<Team> dialog = new Dialog<>();
        dialog.setTitle("Edit Team");
        dialog.setHeaderText("Edit team information");
        
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);
        
        TextField idField = new TextField(selectedTeam.getTeamId());
        idField.setEditable(false);
        
        TextField nameField = new TextField(selectedTeam.getTeamName());
        
        TextField membersField = new TextField(String.valueOf(selectedTeam.getNumberOfMembers()));
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(
            new Label("Team ID:"), idField,
            new Label("Team Name:"), nameField,
            new Label("Number of Members:"), membersField
        );
        
        dialog.getDialogPane().setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                try {
                    String id = idField.getText().trim();
                    String name = nameField.getText().trim();
                    int members = Integer.parseInt(membersField.getText().trim());
                    
                    if (name.isEmpty()) {
                        Logger.warn("Invalid team data entered");
                        showAlert("Error", "Team name must be filled");
                        return null;
                    }
                    
                    return new Team(id, name, members);
                } catch (NumberFormatException e) {
                    Logger.error("Invalid number format: " + e.getMessage());
                    showAlert("Error", "Invalid number of members");
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(team -> {
            if (teamDataAccess.updateTeam(team.getTeamId(), team)) {
                refreshTable();
                showAlert("Success", "Team updated successfully");
            } else {
                showAlert("Error", "Failed to update team");
            }
        });
    }
    
    private void showDeleteTeamDialog() {
        Team selectedTeam = table.getSelectionModel().getSelectedItem();
        
        if (selectedTeam == null) {
            showAlert("Error", "Please select a team to delete");
            return;
        }
        
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText("Delete Team");
        confirmation.setContentText("Are you sure you want to delete team: " + selectedTeam.getTeamName() + "?");
        
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (teamDataAccess.removeTeam(selectedTeam.getTeamId())) {
                    refreshTable();
                    showAlert("Success", "Team deleted successfully");
                } else {
                    showAlert("Error", "Failed to delete team");
                }
            }
        });
    }
    
    private void showAboutWindow() {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");
        
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);
        
        Label title = new Label("Team Manager Application");
        title.setStyle("-fx-font-size: 18pt; -fx-font-weight: bold;");
        
        Label devTeam = new Label("Development Team:");
        devTeam.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");
        
        Label member1 = new Label("Muddassir Khalidi, 222110775");
        Label member2 = new Label("Abdulrahman Hamza, <student ID>");
        Label member3 = new Label("Fahad Turki, <student ID>");
        Label member4 = new Label("Assem Alhanash, <student ID>");
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> aboutStage.close());
        
        vbox.getChildren().addAll(title, devTeam, member1, member2, member3, member4, closeButton);
        
        Scene scene = new Scene(vbox, 400, 300);
        aboutStage.setScene(scene);
        aboutStage.show();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

