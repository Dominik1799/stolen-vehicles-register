package controllers;

import com.jfoenix.controls.JFXProgressBar;
import datasource.Datasource;
import datasource.ThreadCases;
import entities.Case;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class caseDetailController implements Initializable {
    private Case Case;
    @FXML
    private Text name, sexText,dateofbirth,nationality,description,age,group;
    @FXML
    private Tab associates;
    @FXML private TableView<Case> tablePartners;
    @FXML private TableColumn<Case, String> fname;
    @FXML private TableColumn<Case, String> lname;
    @FXML private TableColumn<Case, String> sex;
    @FXML private TableColumn<Case, String> caseid;
    @FXML
    JFXProgressBar progressBar;
    @FXML
    private Button next,back;
    @FXML
    ContextMenu myMenuBar;

    int offset;
    int step = 16;
    String[] filter;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fname.setCellValueFactory(new PropertyValueFactory<Case, String>("name"));
        lname.setCellValueFactory(new PropertyValueFactory<Case, String>("surname"));
        sex.setCellValueFactory(new PropertyValueFactory<Case, String>("sex"));
        caseid.setCellValueFactory(new PropertyValueFactory<Case, String>("caseid"));
    }
/*
    public void setDetails(){
        this.name.setText(Case.getCriminalGroup().toString());
        this.sexText.setText( String(Case.getSeverity());
        this.dateofbirth.setText(Case.getBirthday());
        this.age.setText(String.valueOf(Case.getAge()));
        this.description.setText(Case.getDescription());
        this.nationality.setText(Case.getNationality());
        if (Case.getGroupID() == 0){
            this.group.setText("No crimminal group");
            this.associates.setDisable(true);
        } else {
            this.group.setText(Case.getGroup());
            setAssociates();
        }
    }



    public void setUpTable(ThreadCases threadCases){
        Thread t = new Thread(threadCases::parseCases);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBar.setVisible(true);
            }
            progressBar.setVisible(false);
            if (!(threadCases.getCases().size() <= 1))
                tablePartners.setItems(threadCases.getCases());
            if (threadCases.getCases().size() < this.step)
                this.next.setDisable(true);
        });
        watcher.start();
    }

    public void setCase(Case Case) {
        this.Case = Case;

    }

    public void setAssociates(){
        this.offset = 0;
        this.filter = new String[]{"","","","", String.valueOf(this.Case.getGroupID())};
        next.setDisable(false);
        ThreadCases threadCases = new ThreadCases(this.offset,this.filter);
        setUpTable(threadCases);


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
    public void showDetail(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/CaseDetailScene.fxml"));
        Case Case =  tablePartners.getSelectionModel().getSelectedItem();
        Parent root = (Parent) loader.load();
        CaseDetailController ctrl = loader.getController();
        ctrl.setCase(Case);
        ctrl.setDetails();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) next.getScene().getWindow();
        window.setTitle(Case.getId() + " " + Case.getSeverity());
        window.setScene(scene2);
        window.show();
    }

*/


}
