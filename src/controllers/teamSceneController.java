package controllers;

import ORM.TeamsDatasource;
import datasource.ThreadVehicles;
import entities.Team;
import entities.Vehicle;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class teamSceneController extends userSceneController {
    private Team team;

    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
    }

}
