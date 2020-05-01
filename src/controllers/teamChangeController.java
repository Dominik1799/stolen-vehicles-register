package controllers;

import com.jfoenix.controls.JFXProgressBar;
import datasource.ThreadTeams;
import entities.Team;
import entities.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utilities.Dialog;

import java.net.URL;
import java.util.ResourceBundle;

public class teamChangeController implements Initializable {
    private static final int PAGE_SIZE = 16;
    User user;
    int pageNum = 0;
    String[] filter;
    teamSceneController teamSceneController;

    @FXML
    private TableView<Team> tableview;
    @FXML private TableColumn<Team, String> leadername;
    @FXML private TableColumn<Team,String> leadersurname;
    @FXML private TableColumn<Team, Integer> teamid;
    @FXML private TableColumn<Team, Integer> members;
    @FXML private TableColumn<Team, Integer> activecases;

    @FXML
    JFXProgressBar progressbar;
    @FXML
    Button joinButton;

    @FXML
    TextField membersFrom,membersTo,casesFrom,casesTo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leadername.setCellValueFactory(new PropertyValueFactory<Team, String>("leaderName"));
        leadersurname.setCellValueFactory(new PropertyValueFactory<Team, String>("leaderSurname"));
        teamid.setCellValueFactory(new PropertyValueFactory<Team, Integer>("id"));
        members.setCellValueFactory(new PropertyValueFactory<Team, Integer>("memberamount"));
        activecases.setCellValueFactory(new PropertyValueFactory<Team, Integer>("activeCasesCount"));

    }

    public void setTeamSceneController(controllers.teamSceneController teamSceneController) {
        this.teamSceneController = teamSceneController;
    }

    public void setUser(User user){
        this.user = user;
    }
    public void setupTable(){
        ThreadTeams threadTeams = new ThreadTeams();
        threadTeams.pageNum = this.pageNum;
        threadTeams.args = this.filter;
        fetchData(threadTeams);
    }

    public void fetchData(ThreadTeams threadTeams){
        Thread t = new Thread(threadTeams::getOnePageOfTeams);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressbar.setVisible(true);
            }
            progressbar.setVisible(false);
            tableview.setItems(threadTeams.getTeams());
        });
        watcher.start();

    }

    public void onJoinClick(){
        Team team = tableview.getSelectionModel().getSelectedItem();
        ThreadTeams threadTeams = new ThreadTeams();
        threadTeams.teamToUser = team;
        threadTeams.userToTeam = user;
        addUserToTeam(threadTeams);

    }

    private void addUserToTeam(ThreadTeams threadTeams){
        Thread t = new Thread(threadTeams::addUserToTeam);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressbar.setVisible(true);
            }
            progressbar.setVisible(false);
            Stage stage = (Stage) joinButton.getScene().getWindow();
            this.teamSceneController.user.setTeam(String.valueOf(threadTeams.teamToUser.getId()));
            this.teamSceneController.prepareTables(String.valueOf(threadTeams.teamToUser.getId()));
            Platform.runLater(stage::close);
        });
        watcher.start();
    }

    public void onNextClick(){
        joinButton.setDisable(true);
        this.pageNum = this.pageNum + PAGE_SIZE;
        setupTable();
    }

    public void onBackClick(){
        if (this.pageNum - PAGE_SIZE < 1)
            return;
        joinButton.setDisable(true);
        this.pageNum = this.pageNum - PAGE_SIZE;
        setupTable();
    }

    private void parseFilter(){
        this.filter = new String[]{membersFrom.getText(),membersTo.getText(),casesFrom.getText(),casesTo.getText()};
    }

    public void onShowClick(){
        joinButton.setDisable(true);
        this.pageNum = 0;
        parseFilter();
        setupTable();
    }

    public void enableButton(){
        joinButton.setDisable(false);
    }
}
