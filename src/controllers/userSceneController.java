package controllers;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import entities.User;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userSceneController implements Initializable  {
    User user = new User();
    @FXML
    private VBox vbox1;

    @FXML
    private JFXTextField sex;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField rank;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    public void setUser(User user) {
        this.user = user;
    }

    public void setText(String text) {
        System.out.println(text);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        name.setText(user.getFirstName() + user.getLastName());
        sex.setText(user.getSex());
        rank.setText(user.getRank());
        */
        drawer.setSidePane(vbox1);
    }

    public void onClickLogOut(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent view2 = FXMLLoader.load(getClass().getResource("../scenes/loginScene.fxml"));
        Scene scene2 = new Scene(view2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void toggleBurger() {
        if(drawer.isClosed()) {
            drawer.open();
        }
        else {
            drawer.close();
        }
    }


}
