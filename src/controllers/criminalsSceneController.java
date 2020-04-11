package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXProgressBar;
import datasource.ThreadCriminals;
import entities.Criminal;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class criminalsSceneController extends userSceneController implements Initializable {
    @FXML
    JFXButton next,back;
    @FXML
    JFXButton listAll;
    @FXML private TableView<Criminal> tableView;
    @FXML private TableColumn<Criminal, String> fname;
    @FXML private TableColumn<Criminal, String> lname;
    @FXML private TableColumn<Criminal, String> sex;
    @FXML private TableColumn<Criminal, String> caseid;
    @FXML private TableColumn<Criminal, String> nationality;
    @FXML private TableColumn<Criminal, String> group;
    @FXML private TableColumn<Criminal, String> age;
    @FXML
    JFXProgressBar progressBar;
    int offset;
    int step = 16;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
        fname.setCellValueFactory(new PropertyValueFactory<Criminal, String>("name"));
        lname.setCellValueFactory(new PropertyValueFactory<Criminal, String>("surname"));
        sex.setCellValueFactory(new PropertyValueFactory<Criminal, String>("sex"));
        caseid.setCellValueFactory(new PropertyValueFactory<Criminal, String>("caseid"));
        nationality.setCellValueFactory(new PropertyValueFactory<Criminal, String>("nationality"));
        group.setCellValueFactory(new PropertyValueFactory<Criminal, String>("group"));
        age.setCellValueFactory(new PropertyValueFactory<Criminal, String>("age"));
        progressBar.setVisible(false);
        this.offset = 0;
    }

    public void setUpTable(ThreadCriminals threadCriminals){
        Thread t = new Thread(threadCriminals::parseCriminals);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBar.setVisible(true);
            }
            progressBar.setVisible(false);
            tableView.setItems(threadCriminals.getCriminals());
        });
        watcher.start();
    }

    public void onListAllClick(ActionEvent actionEvent) throws InterruptedException {
        this.offset = 0;
        next.setDisable(false);
        ThreadCriminals threadCriminals = new ThreadCriminals(this.offset);
        setUpTable(threadCriminals);


    }
    public void onNextClick(ActionEvent event){
        back.setDisable(false);
        this.offset = this.offset + step;
        ThreadCriminals threadCriminals = new ThreadCriminals(this.offset);
        setUpTable(threadCriminals);
    }
    public void onBackClick(ActionEvent event){
        this.offset = this.offset - step;
        if (this.offset == 0)
            back.setDisable(true);
        ThreadCriminals threadCriminals = new ThreadCriminals(this.offset);
        setUpTable(threadCriminals);
    }
}
