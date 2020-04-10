package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import entities.User;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userSceneController implements Initializable {
    @FXML
    private AnchorPane navList,background;
    @FXML
    private Text fullname;
    @FXML
    JFXHamburger hamburgerOpen;
    @FXML
    JFXButton onClickHome, onClickTeams, onClickCriminals, onClickVehicles;
    User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setTextName() {
        fullname.setText(user.getFirstName() + " " +  user.getLastName());
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
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

    public void onClickLogOut(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/loginScene.fxml"));
        navList.getChildren().setAll(pane);
    }

    public void onActionTeams(ActionEvent event) throws  IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/teamsScene.fxml"));
        navList.getChildren().setAll(pane);
    }

    public void onActionVehicles(ActionEvent event) throws  IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/vehicleScene.fxml"));
        navList.getChildren().setAll(pane);
    }

    public void onActionCriminals(ActionEvent event) throws  IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/criminalScene.fxml"));
        navList.getChildren().setAll(pane);
    }

    public void onActionHome(ActionEvent event) throws  IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/userScene.fxml"));
        navList.getChildren().setAll(pane);
    }


    }
