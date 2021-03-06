package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class userSceneController implements Initializable {
    @FXML
    private AnchorPane navList,background;
    @FXML
    JFXHamburger hamburgerOpen;
    @FXML
    JFXButton onLogOutClick,onHomeClick,onTeamsClick,onVehiclesClick,onCriminalsClick;
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

    public void onLogOutClick(ActionEvent actionEvent) {

    }
}
