package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
    JFXButton onLogOutClick,onHomeClick,onTeamsClick,onVehiclesClick,onCriminalsClick;
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

    public void onTeamClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/teamScene.fxml"));
        Parent root = (Parent) loader.load();
        teamSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.setTextName();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void setOnCriminalsClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/criminalsScene.fxml"));
        Parent root = (Parent) loader.load();
        teamSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.setTextName();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onLogOutClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent view2 = FXMLLoader.load(getClass().getResource("../scenes/loginScene.fxml"));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

}
