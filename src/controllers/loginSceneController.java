package controllers;

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
    private TextField FirstNameReg, LastNameReg, FirstNameLogIn, DateOfBirth;
    @FXML
    private ComboBox<String> sexCB, rankCB;

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
        String birthDate = this.DateOfBirth.getText().replace(" ", "").replace(".", "-");
        String firstName = this.FirstNameReg.getText();
        String lastName = this.LastNameReg.getText();
        String sex = this.sexCB.getValue();
        String rank = this.rankCB.getValue();
        if (birthDate.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || sex.isEmpty() || rank.isEmpty()) {
            Dialog.getInstance().warningDialog("All fields are required!");
            return;
        }
        if (Datasource.getInstance().createUser(firstName, lastName, birthDate, sex, rank) == 1)
            Dialog.getInstance().infoDialog("User created successfully!");
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
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent view2 = (Parent) FXMLLoader.load(getClass().getResource("../scenes/userScene.fxml"));

                //creates controller
                userSceneController cnt = loader.getController();
                //adds user to the controller

                //cnt.setUser(user);

                //cnt.setText(user.getLastName());
                Scene scene2 = new Scene(view2);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene2);
                window.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Dialog.getInstance().warningDialog("ID not found");
        }
    }
}

