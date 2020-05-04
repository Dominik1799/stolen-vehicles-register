package controllers;

import com.jfoenix.controls.JFXProgressBar;
import datasource.ThreadTeams;
import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class teamSceneController extends userSceneController {
    private Team team;
    @FXML
    private Text teamID, teamLeader,description,descriptionPrompt,colleagues,workingon;
    @FXML private TableView<User> membersTable;
    @FXML private TableView<Case> casesTable;
    @FXML private TableColumn<User, String> firstName;
    @FXML private TableColumn<User,String> lastName;
    @FXML private TableColumn<Case, Integer> caseid;
    @FXML private TableColumn<Case, Integer> status;
    @FXML private TableColumn<Case, Integer> severity;
    @FXML
    JFXProgressBar progressBarTeams;
    @FXML
    Button leave,join;


    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
        firstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        caseid.setCellValueFactory(new PropertyValueFactory<Case, Integer>("id"));
        status.setCellValueFactory(new PropertyValueFactory<Case, Integer>("status"));
        severity.setCellValueFactory(new PropertyValueFactory<Case, Integer>("severity"));
    }

    public void prepareTables(String teamID){
        if (teamID.equals("Not a member of any team")){
            this.teamID.setText("Not a member of any team. ");
            this.membersTable.setVisible(false);
            this.casesTable.setVisible(false);
            this.leave.setVisible(false);
            this.join.setVisible(true);
            this.colleagues.setVisible(false);
            this.workingon.setVisible(false);
            this.teamLeader.setText("None");
            return;
        }
        this.join.setVisible(false);
        this.leave.setVisible(true);
        this.casesTable.setVisible(true);
        this.membersTable.setVisible(true);
        this.colleagues.setVisible(true);
        this.workingon.setVisible(true);
        this.teamLeader.setText("Loading...");
        ThreadTeams threadTeams = new ThreadTeams();
        threadTeams.setTeamID(this.user.getTeam());
        fetchDataAboutCurrentTeam(threadTeams);
        this.teamID.setText(teamID);

    }

    private void fetchDataAboutCurrentTeam(ThreadTeams threadTeams){
        Thread t = new Thread(threadTeams::getTeamData);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBarTeams.setVisible(true);
            }
            progressBarTeams.setVisible(false);
            membersTable.setItems(threadTeams.getMembers());
            casesTable.setItems(threadTeams.getActiveCases());
            this.team = threadTeams.getTeam();
            this.teamLeader.setText(this.team.getLeader().getFirstName() + " " + this.team.getLeader().getLastName());
        });
        watcher.start();
    }

    public void onMouseClick(){
        Case selectedCase = this.casesTable.getSelectionModel().getSelectedItem();
        this.descriptionPrompt.setVisible(true);
        this.description.setText(selectedCase.getDescription());
    }
    public void onJoinTeamClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/teamChangeScene.fxml"));
        Parent root = loader.load();
        teamChangeController ctrl = loader.getController();
        ctrl.setUser(this.user);
        ctrl.setTeamSceneController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 886, 526));
        stage.setTitle("Choose a new team");
        stage.show();
    }

    public void onLeaveTeamClick(){
        if (this.team.getLeader().getId() == this.user.getId()){
            System.out.println("pindur");
            return;
        }
        ThreadTeams threadTeams = new ThreadTeams();
        threadTeams.userToTeam = this.user;
        threadTeams.teamToUser = this.team;
        removeUserFromTeam(threadTeams);
    }

    private void removeUserFromTeam(ThreadTeams threadTeams){
        Thread t = new Thread(threadTeams::removeUserFromTeam);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBarTeams.setVisible(true);
            }
            progressBarTeams.setVisible(false);
            this.user.setTeam("Not a member of any team");
            this.prepareTables(this.user.getTeam());
        });
        watcher.start();
    }


}
