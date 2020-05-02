package controllers;

import com.jfoenix.controls.JFXHamburger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class casesSceneController extends userSceneController implements Initializable {
    @FXML
    protected JFXHamburger hamburgerOpen1;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //CasesDatasource.getInstance().getCases();
        prepareSlideMenuAnimation();
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



    public void showDetail(ActionEvent event) {
    }

    public void dropRecord(ActionEvent event) {
    }

    public void onSearchClick(ActionEvent event) {

    }

}
