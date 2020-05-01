package controllers;

import ORM.TeamsDatasource;
import datasource.ThreadVehicles;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import javax.jws.soap.SOAPBinding;
import java.net.URL;
import java.util.ResourceBundle;

public class teamSceneController extends userSceneController {
    private Team team;
    @FXML
    private Text teamID, teamLeader;
    @FXML private TableView<User> membersTable;
    @FXML private TableView<Case> casesTable;
    @FXML private TableColumn<User, String> firstName;
    @FXML private TableColumn<User,String> lastName;
    @FXML private TableColumn<Case, Integer> caseid;
    @FXML private TableColumn<Case, Integer> status;
    @FXML private TableColumn<Case, Integer> severity;


    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
        firstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        caseid.setCellValueFactory(new PropertyValueFactory<Case, Integer>("id"));
        status.setCellValueFactory(new PropertyValueFactory<Case, Integer>("status"));
        severity.setCellValueFactory(new PropertyValueFactory<Case, Integer>("severity"));
    }

    public void prepareTables(String teamID){
        if (teamID.equals("Not a member of any team"))
            return;
        this.team = TeamsDatasource.getInstance().getCurrentUserTeam(Integer.parseInt(teamID));
        ObservableList<User> members = FXCollections.observableArrayList(this.team.getMembers());
        ObservableList<Case> activeCases = FXCollections.observableArrayList(this.team.getActiveCases());
        this.membersTable.setItems(members);
        this.casesTable.setItems(activeCases);
    }
}
