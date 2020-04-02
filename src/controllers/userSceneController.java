package controllers;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class userSceneController implements Initializable {
    private User user;
    @FXML
    private Label name;
    @FXML
    private Label sex;
    @FXML
    private Label rank;

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(user.getFirstName() + user.getLastName());
        sex.setText(user.getSex());
        rank.setText(user.getRank());
    }
}
