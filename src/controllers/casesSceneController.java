package controllers;

import ORM.CasesDatasource;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXProgressBar;
import datasource.Datasource;
import datasource.ThreadCases;
import entities.Case;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.Dialog;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class casesSceneController extends userSceneController implements Initializable {
    @FXML JFXButton next,back;
    @FXML JFXHamburger hamburgerOpen1;
    @FXML TabPane tabPane;
    @FXML Tab searchTab;
    @FXML JFXComboBox<String> createStatus, searchStatus;
    @FXML TextField createCriminal, createSeverity, searchCriminalGroup, searchKeywords, searchSeverity;
    @FXML TextArea createDescription;
    @FXML private JFXProgressBar progressBar;
    @FXML private TableView<Case> tableView;
    @FXML private TableColumn<Case, String > criminalGroup;
    @FXML private TableColumn<Case, Integer> caseID;
    @FXML private TableColumn<Case, Integer> status;
    @FXML private TableColumn<Case, Integer> severity;
    ObservableList<String> statuses = FXCollections.observableArrayList();
    int offset = 0;
    int step = 16;
    String[] filter;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        caseID.setCellValueFactory(new PropertyValueFactory<Case, Integer>("id"));
        criminalGroup.setCellValueFactory(new PropertyValueFactory<Case, String>("criminalGroupName"));
        status.setCellValueFactory(new PropertyValueFactory<Case, Integer>("status"));
        severity.setCellValueFactory(new PropertyValueFactory<Case, Integer>("severity"));
        prepareStatuses();
        prepareSlideMenuAnimation();
    }

    private void prepareStatuses() {
        //creates list of statuses in the combobox
        ResultSet casestatus = Datasource.getInstance().selectAllFrom("case_status");
        try {
            while (casestatus.next())
                statuses.add(casestatus.getString("status"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.createStatus.setItems(statuses);
        this.searchStatus.setItems(statuses);
    }

    public void setUpTable(ThreadCases threadCases){
        Thread t = new Thread(threadCases::parseCases);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBar.setVisible(true);
            }
            progressBar.setVisible(false);
            tableView.setItems((ObservableList) threadCases.getCases());
        });
        watcher.start();
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


    private Case createCase() {
        Case kejs = new Case();
        kejs.setStatus(this.statuses.indexOf(this.createStatus.getValue()));
        kejs.setDescription(this.createDescription.getText());
        kejs.setSeverity(Integer.parseInt(this.createSeverity.getText()));

        //Find criminal group id based on the name of criminal
        if(this.createCriminal.getText().equals("N/A")) {
            kejs.getCriminalGroup().setId(0); //group unknown
        }
        else {
            Integer index = CasesDatasource.getInstance().getCriminalGroupId(this.createCriminal.getText()); //find the criminal
            if(index == 0) //no criminal found
                return null;
            if(kejs.getCriminalGroup() != null) {
                kejs.getCriminalGroup().setId(index);
            }
        }
        return kejs;
    }


    public void onCreateClick(ActionEvent event) {
        if(createDescription.getText().isEmpty() || createCriminal.getText().isEmpty()
                || createSeverity.getText().isEmpty() || createStatus.getValue().isEmpty()) {
            Dialog.getInstance().warningDialog("All fields must be entered!");
        }
        else {
            Case kejs = this.createCase();
            if(kejs != null) {
                CasesDatasource.getInstance().saveTable(kejs);
                Dialog.getInstance().infoDialog("Case successfully created");
                tabPane.getSelectionModel().select(this.searchTab);
            }
        }
    }



    public void showDetail() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "../scenes/casesDetailScene.fxml"));
            Parent root = (Parent) loader.load();
            casesDetailController ctrl = loader.getController();
            ctrl.setaCase(tableView.getSelectionModel().getSelectedItem());
            ctrl.setDetails();
            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onNextClick(ActionEvent event){
        back.setDisable(false);
        this.offset = this.offset + step;
        ThreadCases threadCases = new ThreadCases(this.offset,this.filter);
        setUpTable(threadCases);
    }
    public void onBackClick(ActionEvent event){
        this.offset = this.offset - step;
        if (this.offset == 0)
            back.setDisable(true);
        ThreadCases threadCases = new ThreadCases(this.offset,this.filter);
        setUpTable(threadCases);
    }


    public void onSearchClick(ActionEvent event) {
        this.offset = 0;
        next.setDisable(false);
        this.filter = new String[]{searchCriminalGroup.getText(), searchKeywords.getText(),
                String.valueOf(searchStatus.getSelectionModel().getSelectedIndex()+1) , searchSeverity.getText()};
        ThreadCases threadCases = new ThreadCases(this.offset, this.filter);
        setUpTable(threadCases);
    }
}
