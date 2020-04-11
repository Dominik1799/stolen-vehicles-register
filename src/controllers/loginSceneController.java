package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import datasource.Datasource;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import utilities.Dialog;

import javax.swing.tree.ExpandVetoException;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginSceneController implements Initializable {
    ObservableList<String> ranks = FXCollections.observableArrayList();
    ObservableList<String> sexes = FXCollections.observableArrayList();
    @FXML
    private TextField FirstNameReg, LastNameReg, FirstNameLogIn;
    @FXML
    private ComboBox<String> sexCB, rankCB;
    @FXML
    private JFXDatePicker DateOfBirth;

    @Override
    // Initialize runs after the constructor so it has access to all the javaFX components.
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet ranks = Datasource.getInstance().selectAllFrom("rank");
        ResultSet sexes = Datasource.getInstance().selectAllFrom("sex");
        try {
            while (ranks.next())
                this.ranks.add(ranks.getString("rank"));
            while (sexes.next()) {
                this.sexes.add(sexes.getString("sex"));
            }
            ranks.close();
            sexes.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // setu-up combo boxes
        this.rankCB.setItems(this.ranks);
        this.sexCB.setItems(this.sexes);
    }

    public void onRegisterClick(ActionEvent event) {
        String birthDate = this.DateOfBirth.getValue().toString();
        String firstName = this.FirstNameReg.getText();
        String lastName = this.LastNameReg.getText();
        String sex = this.sexCB.getValue();
        String rank = this.rankCB.getValue();
        if (birthDate.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || sex.isEmpty() || rank.isEmpty()) {
            Dialog.getInstance().warningDialog("All fields are required!");
            return;
        }
        int userID = Datasource.getInstance().createUser(firstName, lastName, birthDate, sex, rank) + 1;
        if ( userID > 0)
            Dialog.getInstance().infoDialog("User created successfully! New user ID: " + userID);
        else
            Dialog.getInstance().errorDialog("Something went wrong. Please try again.");
    }

    public void onLoginClick(ActionEvent event) throws IOException {
        String id = this.FirstNameLogIn.getText();
        //String last = this.LastNameLogIn.getText();
        if (id.isEmpty()) {
            Dialog.getInstance().warningDialog("All fields are required!");
            return;
        }

        User user = Datasource.getInstance().checkLoggingData(id);
        if (user != null) {
            //There is someone with credentials in database
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
        } else {
            Dialog.getInstance().warningDialog("ID not found");
        }
    }
}

