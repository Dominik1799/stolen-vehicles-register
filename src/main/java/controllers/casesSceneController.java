package controllers;

import ORM.CasesDatasource;
import com.jfoenix.controls.*;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.Dialog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class casesSceneController extends userSceneController implements Initializable {
    @FXML
    JFXButton next, back;
    @FXML
    JFXHamburger hamburgerOpen1, hamburgerOpen2;
    @FXML
    TabPane tabPane;
    @FXML
    Tab searchTab;
    @FXML
    JFXComboBox<String> createStatus, searchStatus;
    @FXML
    TextField createCriminal, createSeverity, searchCriminalGroup, searchKeywords, searchSeverity;
    @FXML
    TextArea createDescription;
    @FXML
    protected JFXProgressBar progressBar;
    @FXML
     protected TableView<Case> tableView;
    @FXML
     protected TableColumn<Case, String> criminalGroup;
    @FXML
     protected TableColumn<Case, Integer> caseID;
    @FXML
     protected TableColumn<Case, Integer> status;
    @FXML
     protected TableColumn<Case, Integer> severity;
    @FXML
     protected JFXRadioButton greaterEqualRB, lessEqualRB;
    ObservableList<String> statuses = FXCollections.observableArrayList();
    int offset = 0;
    int step = 16;
    String[] filter;


     protected void prepareStatuses() {
        //creates list of statuses in the combobox
        this.statuses = getStatuses();
        this.createStatus.setItems(statuses);
        this.searchStatus.setItems(statuses);
    }

     protected ObservableList<String> getStatuses() {
        //creates list of statuses in the combobox
        ObservableList<String> statusList = FXCollections.observableArrayList();
        ResultSet casestatus = Datasource.getInstance().selectAllFrom("case_status");
        try {
            while (casestatus.next())
                statusList.add(casestatus.getString("status"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return statusList;
    }


    public void setUpTable(ThreadCases threadCases) {
        Thread t = new Thread(threadCases::parseCases);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()) {
                progressBar.setVisible(true);
            }
            progressBar.setVisible(false);
            tableView.setItems((ObservableList) threadCases.getCases());
        });
        watcher.start();
    }

    protected void prepareSlideMenuAnimation() {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);
        //three burgers please
        hamburgerOpen.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (navList.getTranslateX() != 0) {
                openNav.play();
            } else {
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
        hamburgerOpen1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (navList.getTranslateX() != 0) {
                openNav.play();
            } else {
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
        hamburgerOpen2.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (navList.getTranslateX() != 0) {
                openNav.play();
            } else {
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
        background.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (navList.getTranslateX() == 0) {
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
    }


     protected Case createCase() {
        Case kejs = new Case();
        kejs.setStatusId(this.statuses.indexOf(this.createStatus.getValue()));
        kejs.setDescription(this.createDescription.getText());
        kejs.setSeverity(Integer.parseInt(this.createSeverity.getText()));

        //Find criminal group id based on the name of criminal
        if (this.createCriminal.getText().equals("N/A")) {
            kejs.getCriminalGroup().setId(0); //group unknown
        } else {
            Integer index = CasesDatasource.getInstance().getCriminalGroupId(this.createCriminal.getText()); //find the criminal
            if (index == 0) //no criminal found
                return null;
            if (kejs.getCriminalGroup() != null) {
                kejs.getCriminalGroup().setId(index);
            }
        }
        return kejs;
    }


    public void onCreateClick(ActionEvent event) {
        if (createDescription.getText().isEmpty() || createCriminal.getText().isEmpty()
                || createSeverity.getText().isEmpty() || createStatus.getValue().isEmpty()) {
            Dialog.getInstance().warningDialog("All fields must be entered!");
        } else {
            Case kejs = this.createCase();
            if (kejs != null) {
                CasesDatasource.getInstance().saveTable(kejs);
                Dialog.getInstance().infoDialog("Case successfully created");
                tabPane.getSelectionModel().select(this.searchTab);
            }
        }
    }


    public void showDetail() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/scenes/casesDetailScene.fxml"));
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

    public void onNextClick(ActionEvent event) {
        back.setDisable(false);
        this.offset = this.offset + step;
        ThreadCases threadCases = new ThreadCases(this.offset, this.getCompareSymbol(), this.filter);
        setUpTable(threadCases);
    }

    public void onBackClick(ActionEvent event) {
        this.offset = this.offset - step;
        if (this.offset == 0)
            back.setDisable(true);
        ThreadCases threadCases = new ThreadCases(this.offset, this.getCompareSymbol(), this.filter);
        setUpTable(threadCases);
    }

     protected String getCompareSymbol() {
        //returns symbol for comparison (=,<,>) of Severity based on RBs
        if (greaterEqualRB.isSelected()) return ">=";
        if (lessEqualRB.isSelected()) return "<=";
        return "=";
    }


    public void onSearchClick(ActionEvent event) {
        this.offset = 0;
        next.setDisable(false);

        this.filter = new String[]{searchCriminalGroup.getText(), searchKeywords.getText(),
                String.valueOf(searchStatus.getSelectionModel().getSelectedIndex() + 1), searchSeverity.getText()};
        ThreadCases threadCases = new ThreadCases(this.offset, this.getCompareSymbol(), this.filter);
        setUpTable(threadCases);
    }
}