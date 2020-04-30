package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import datasource.Datasource;
import datasource.ThreadCases;
import entities.Case;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class casesSceneController extends userSceneController implements Initializable {

    @FXML
    JFXButton next,back;
    @FXML
    JFXButton listAll;
    @FXML private TableView<Case> tableView;
    @FXML private TableColumn<Case, Integer> severity;
    @FXML private TableColumn<Case, Integer> status;
    @FXML private TableColumn<Case, Integer> CaseGroup;

    @FXML
    JFXProgressBar progressBar;
    @FXML
    TextField searchbar,severityFilter,criminalGroupFilter;
    @FXML
    ComboBox<String> statusFilter;
    ObservableList<String> sexes = FXCollections.observableArrayList();
    int offset;
    int step = 16;
    String filter[];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
        severity.setCellValueFactory(new PropertyValueFactory<Case, Integer>("severity"));
        status.setCellValueFactory(new PropertyValueFactory<Case, Integer>("status"));
        CaseGroup.setCellValueFactory(new PropertyValueFactory<Case, Integer>("CaseGroup"));
        progressBar.setVisible(false);
    }

    public void setUpTable(ThreadCases threadCases){
        Thread t = new Thread(threadCases::parseCases);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBar.setVisible(true);
            }
            progressBar.setVisible(false);
            tableView.setItems(threadCases.getCases());
        });
        watcher.start();
    }

    public void onListAllClick(ActionEvent actionEvent) throws InterruptedException {
        this.offset = 0;
        String tempSex = statusFilter.getValue();
        if (tempSex==null)
            tempSex="";
        else
            tempSex = Datasource.getInstance().translateSex(0,tempSex);
        this.filter = new String[]{searchbar.getText(),severityFilter.getText(),criminalGroupFilter.getText(),tempSex};
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

    public void updateTable(){
        ThreadCases threadCases = new ThreadCases(this.offset);
        setUpTable(threadCases);
    }
/*
    public void showDetail(ActionEvent event) throws IOException {
        Case Case =  tableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/caseDetailScene.fxml"));
        Parent root = (Parent) loader.load();
        caseDetailController ctrl = loader.getController();
        ctrl.setCase(Case);
        ctrl.setDetails();
        Stage stage = new Stage();
        stage.setTitle("Detail : " + Case.getId() + " " + Case.getSeverity());
        stage.setScene(new Scene(root, 695, 455));
        stage.show();
    }
*/

}
