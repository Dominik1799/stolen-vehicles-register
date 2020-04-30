package controllers;

import ORM.CasesDatasource;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class casesSceneController extends userSceneController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CasesDatasource.getInstance().getCases();
        prepareSlideMenuAnimation();
    }





}
