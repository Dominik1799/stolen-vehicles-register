package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXProgressBar;
import datasource.ThreadCriminals;
import entities.Criminal;
import entities.User;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class criminalsSceneController implements Initializable {
    @FXML
    private AnchorPane navList,background;
    @FXML
    JFXHamburger hamburgerOpen;
    @FXML
    JFXButton onLogOutClick,onHomeClick,onTeamsClick,onVehiclesClick,onCriminalsClick,next,back;
    User user;
    @FXML
    JFXButton listAll;
    @FXML
    private Text fullname;
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


    public void setUser(User user) {
        this.user = user;
    }

    public void setTextName() {
        fullname.setText(user.getFirstName() + " " +  user.getLastName());
    }
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


    private void prepareSlideMenuAnimation() {
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
        background.addEventFilter(MouseEvent.MOUSE_CLICKED,event -> {
            if(navList.getTranslateX() == 0){
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });
    }

    public void onLogOutClick(ActionEvent actionEvent) {

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
        this.offset = this.offset + 20;
        ThreadCriminals threadCriminals = new ThreadCriminals(this.offset);
        setUpTable(threadCriminals);
    }
    public void onBackClick(ActionEvent event){
        this.offset = this.offset - 20;
        if (this.offset == 0)
            back.setDisable(true);
        ThreadCriminals threadCriminals = new ThreadCriminals(this.offset);
        setUpTable(threadCriminals);
    }
}
