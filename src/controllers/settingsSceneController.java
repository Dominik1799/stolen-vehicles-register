package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import datasource.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class settingsSceneController extends userSceneController {
    private ObservableList<String> ranks = FXCollections.observableArrayList();
    private ObservableList<String> sexes = FXCollections.observableArrayList();
    @FXML
    private JFXButton update;
    @FXML
    private JFXTextField firstname, lastname;
    @FXML
    private JFXComboBox sexCB, rankCB;
    @FXML
    private DatePicker birthdate;

    public void getOptions() {
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

    public void setPrompt() {
        firstname.setPromptText(user.getFirstName());
        lastname.setPromptText(user.getLastName());
        sexCB.setPromptText(user.getSex());
        rankCB.setPromptText(user.getRank());
        birthdate.setPromptText(user.getBirthdate().toString());
    }


    private void updateUser() {
        if(!this.firstname.getText().isEmpty()) {
            user.setFirstName(firstname.getText());
        }
        if(!this.lastname.getText().isEmpty()) {
            //System.out.println(lastname.getText());
            user.setLastName(lastname.getText());
        }
        if(this.sexCB.getValue() != null) {
            user.setSex(sexCB.getValue().toString());
        }
        if(this.rankCB.getValue() != null) {
            user.setRank(rankCB.getValue().toString());
        }
        if(this.birthdate.getValue() != null) {
            Instant instant = Instant.from(this.birthdate.getValue().atStartOfDay(ZoneId.systemDefault())); //to convert localdate to date
            user.setBirthdate(Date.from(instant));
        }

    }

    public void onUpdateClick(ActionEvent event) throws IOException {
        this.updateUser();
        Datasource.getInstance().updateUser(user);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/userScene.fxml"));
        Parent root = (Parent) loader.load();
        userSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        ctrl.showInfo();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
}
