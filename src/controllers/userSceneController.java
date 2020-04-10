package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
    private JFXHamburger hamburgerOpen;
    @FXML
    private JFXTextArea userInfo;
    User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void showName() {
        fullname.setText(user.getFirstName() + " " + user.getLastName());
    }
    public void showInfo() {
        userInfo.setText("Name: " + user.getFirstName() + " " + user.getLastName() + "\n" +  "Sex: " + user.getSex() + "\n" + "Rank: " + user.getRank() + "\n" + "Team: " + user.getTeam());
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
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/loginScene.fxml"));
        //navList.getChildren().setAll(pane);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/loginScene.fxml"));
        Parent root = loader.load();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onActionTeams(ActionEvent event) throws  IOException {
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/teamsScene.fxml"));
        //navList.getChildren().setAll(pane);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/teamsScene.fxml"));
        Parent root = loader.load();
        userSceneController ctrl = loader.getController();

        ctrl.setUser(user);
        ctrl.showName();

        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

    }

    public void onActionVehicles(ActionEvent event) throws  IOException {
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/vehiclesScene.fxml"));
        //navList.getChildren().setAll(pane);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/vehiclesScene.fxml"));
        Parent root = loader.load();
        userSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();

        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onActionCriminals(ActionEvent event) throws  IOException {
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/criminalsScene.fxml"));
        //navList.getChildren().setAll(pane);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/criminalsScene.fxml"));
        Parent root = loader.load();
        userSceneController ctrl = loader.getController();

        ctrl.setUser(user);
        ctrl.showName();

        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onActionHome(ActionEvent event) throws  IOException {
        //AnchorPane pane = FXMLLoader.load(getClass().getResource("../scenes/userScene.fxml"));
        //navList.getChildren().setAll(pane);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/userScene.fxml"));
        Parent root = loader.load();
        userSceneController ctrl = loader.getController();

        ctrl.setUser(user);
        ctrl.showName();
        ctrl.showInfo();

        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }


    public void onActionSettings() {

    }
}
