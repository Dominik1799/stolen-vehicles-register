package controllers;

import com.jfoenix.controls.JFXProgressBar;
import datasource.ThreadTeams;
import entities.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class teamSceneController extends userSceneController {
    private Team team;
    @FXML
    private Text teamID, teamLeader,description,descriptionPrompt;
    @FXML private TableView<User> membersTable;
    @FXML private TableView<Case> casesTable;
    @FXML private TableColumn<User, String> firstName;
    @FXML private TableColumn<User,String> lastName;
    @FXML private TableColumn<Case, Integer> caseid;
    @FXML private TableColumn<Case, Integer> status;
    @FXML private TableColumn<Case, Integer> severity;
    @FXML
    JFXProgressBar progressBarTeams;


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
            this.teamID.setText("Not a member of any team");
            return;
        }

        ThreadTeams threadTeams = new ThreadTeams();
        threadTeams.setTeamID(this.user.getTeam());
        fetchData(threadTeams);
        this.teamID.setText(teamID);

    }

    private void fetchData(ThreadTeams threadTeams){
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
}
