package controllers;

import com.jfoenix.controls.JFXProgressBar;
import datasource.Datasource;
import datasource.ThreadCriminals;
import entities.Criminal;
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

public class criminalDetailController implements Initializable {
    private Criminal criminal;
    @FXML
    private Text name, sexText,dateofbirth,nationality,description,age,group;
    @FXML
    private Tab associates;
    @FXML private TableView<Criminal> tablePartners;
    @FXML private TableColumn<Criminal, String> fname;
    @FXML private TableColumn<Criminal, String> lname;
    @FXML private TableColumn<Criminal, String> sex;
    @FXML private TableColumn<Criminal, String> caseid;
    @FXML
    JFXProgressBar progressBar;
    @FXML
    private Button next,back;
    @FXML
    ContextMenu myMenuBar;

    criminalsSceneController criminalsSceneController;
    int offset;
    int step = 16;
    String[] filter;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fname.setCellValueFactory(new PropertyValueFactory<Criminal, String>("name"));
        lname.setCellValueFactory(new PropertyValueFactory<Criminal, String>("surname"));
        sex.setCellValueFactory(new PropertyValueFactory<Criminal, String>("sex"));
        caseid.setCellValueFactory(new PropertyValueFactory<Criminal, String>("caseid"));
    }

    public void setDetails(){
        this.name.setText(criminal.getName() + " " + criminal.getSurname());
        this.sexText.setText(criminal.getSex());
        this.dateofbirth.setText(criminal.getBirthday());
        this.age.setText(String.valueOf(criminal.getAge()));
        this.description.setText(criminal.getDescription());
        this.nationality.setText(criminal.getNationality());
        if (criminal.getGroupID() == 0){
            this.group.setText("No crimminal group");
            this.associates.setDisable(true);
        } else {
            this.group.setText(criminal.getGroup());
            setAssociates();
        }


    }

    public void setUpTable(ThreadCriminals threadCriminals){
        Thread t = new Thread(threadCriminals::parseCriminals);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBar.setVisible(true);
            }
            progressBar.setVisible(false);
            if (!(threadCriminals.getCriminals().size() <= 1))
                tablePartners.setItems(threadCriminals.getCriminals());
            if (threadCriminals.getCriminals().size() < this.step)
                this.next.setDisable(true);
        });
        watcher.start();
    }

    public void setCriminal(Criminal criminal) {
        this.criminal = criminal;

    }

    public void setASIdb(criminalsSceneController controller){
        this.criminalsSceneController = controller;
        this.criminalsSceneController.pokus = 15;
    }

    public void setAssociates(){
        this.offset = 0;
        this.filter = new String[]{"","","","", String.valueOf(this.criminal.getGroupID())};
        next.setDisable(false);
        ThreadCriminals threadCriminals = new ThreadCriminals(this.offset,this.filter);
        setUpTable(threadCriminals);


    }
    public void onNextClick(ActionEvent event){
        back.setDisable(false);
        this.offset = this.offset + step;
        ThreadCriminals threadCriminals = new ThreadCriminals(this.offset,this.filter);
        setUpTable(threadCriminals);
    }
    public void onBackClick(ActionEvent event){
        this.offset = this.offset - step;
        if (this.offset == 0)
            back.setDisable(true);
        ThreadCriminals threadCriminals = new ThreadCriminals(this.offset,this.filter);
        setUpTable(threadCriminals);
    }
    public void showDetail(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/criminalDetailScene.fxml"));
        Criminal criminal =  tablePartners.getSelectionModel().getSelectedItem();
        Parent root = (Parent) loader.load();
        criminalDetailController ctrl = loader.getController();
        ctrl.setCriminal(criminal);
        ctrl.setDetails();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) next.getScene().getWindow();
        window.setTitle(criminal.getName() + " " + criminal.getSurname());
        window.setScene(scene2);
        window.show();
    }




}
