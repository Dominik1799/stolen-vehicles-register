package controllers;

import ORM.CasesDatasource;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXHamburger;
import datasource.Datasource;
import entities.Case;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import utilities.Dialog;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class casesSceneController extends userSceneController implements Initializable {
    @FXML JFXHamburger hamburgerOpen1;
    @FXML TabPane tabPane;
    @FXML Tab searchTab;
    @FXML JFXComboBox<String> createStatus, statusBox;
    @FXML TextField createCriminal, createSeverity;
    @FXML TextArea createDescription;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //CasesDatasource.getInstance().getCases();


        prepareStatuses();
        prepareSlideMenuAnimation();
    }

    private void prepareStatuses() {
        //creates list of statuses in the combobox
        ObservableList<String> statuses = FXCollections.observableArrayList();
        ResultSet casestatus = Datasource.getInstance().selectAllFrom("case_status");
        try {
            while (casestatus.next())
                statuses.add(casestatus.getString("status"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.createStatus.setItems(statuses);
        this.statusBox.setItems(statuses);
    }


    protected void prepareSlideMenuAnimation() {
        TranslateTransition openNav=new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        TranslateTransition closeNav=new TranslateTransition(new Duration(350), navList);
        hamburgerOpen.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if(navList.getTranslateX()!=0){
                openNav.play();
            }else{
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
        //two burgers please
        hamburgerOpen1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if(navList.getTranslateX()!=0){
                openNav.play();
            }else{
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
        background.addEventFilter(MouseEvent.MOUSE_CLICKED,event -> {
            if(navList.getTranslateX() == 0){
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
    }

    private void createCase() {
        Case kejs = new Case();
        kejs.setStatus(this.createStatus.getValue());
        kejs.setDescription(this.createDescription.getText());
        kejs.setSeverity(this.createSeverity.getText());

        //Find criminal group id based on the name of criminal
        if(this.createCriminal.getText().equals("N/A"))
            kejs.setCriminalGroup("0"); //group unknown
        else {
            String something = CasesDatasource.getInstance().getCriminalGroup(this.createCriminal.getText());
            if(something != null)
                kejs.setCriminalGroup(something); //This works only if criminal exists so don't try funny things
            return;
        }

        CasesDatasource.getInstance().saveTable(kejs);
    }


    public void onCreateClick(ActionEvent event) {
        if(createDescription.getText().isEmpty() || createCriminal.getText().isEmpty()
                || createSeverity.getText().isEmpty() || createStatus.getValue().isEmpty()) {
            Dialog.getInstance().warningDialog("All fields must be entered!");
        }
        else {
            this.createCase();
            tabPane.getSelectionModel().select(this.searchTab);
            Dialog.getInstance().infoDialog("Case successfully created");
        }
    }


    public void showDetail(ActionEvent event) {
    }

    public void dropRecord(ActionEvent event) {
    }

    public void onSearchClick(ActionEvent event) {

    }
}
