package controllers;

import datasource.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import utilities.Dialog;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginSceneController implements Initializable {
    ObservableList<String> ranks = FXCollections.observableArrayList();
    ObservableList<String> sexes = FXCollections.observableArrayList();
    @FXML
    private TextField FirstNameReg,LastNameReg,FirstNameLogIn,LastNameLogIn,DateOfBirth;
    @FXML
    private ComboBox<String> sexCB,rankCB;

    @Override
    // Initialize runs after the constructor so it has access to all the javaFX components.
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet ranks = Datasource.getInstance().selectAllFrom("rank");
        ResultSet sexes = Datasource.getInstance().selectAllFrom("sex");
        try {
            while (ranks.next())
                this.ranks.add(ranks.getString("rank"));
            while (sexes.next()){
                this.sexes.add(sexes.getString("sex"));
            }
            ranks.close();
            sexes.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        // setu-up combo boxes
        this.rankCB.setItems(this.ranks);
        this.sexCB.setItems(this.sexes);
    }

    public void onRegisterClick(ActionEvent event){
        String birthDate = this.DateOfBirth.getText().replace(" ","").replace(".","-");
        String firstName = this.FirstNameReg.getText();
        String lastName = this.LastNameReg.getText();
        String sex = this.sexCB.getValue();
        String rank = this.rankCB.getValue();
        if (birthDate.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || sex.isEmpty() || rank.isEmpty()){
            Dialog.getInstance().warningDialog("All fields are required!");
            return;
        }
        if (Datasource.getInstance().createUser(firstName,lastName,birthDate,sex,rank) == 1)
            Dialog.getInstance().infoDialog("User created successfully!");
        else
            Dialog.getInstance().errorDialog("Something went wrong. Please try again.");
    }
}
