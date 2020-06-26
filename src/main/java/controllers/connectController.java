package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import datasource.Datasource;
import entities.DatabaseAccount;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Dialog;

import javax.xml.crypto.Data;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class connectController implements Initializable {

    public JFXTextField serverAddress, portNumber, username;
    public JFXButton connectButton;
    public JFXPasswordField password;
    public JFXProgressBar progressbar;
    public JFXTextField dbRestriction;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectButton.disableProperty().bind(
                    Bindings.isEmpty(username.textProperty())
                        .or(Bindings.isEmpty(portNumber.textProperty()))
                        .or(Bindings.isEmpty(serverAddress.textProperty())
                        .or(Bindings.isEmpty(password.textProperty())))
        );
    }

    private void writeToFile(DatabaseAccount databaseAccount) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream("account_credentials.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(databaseAccount);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/loginScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Welcome");
        stage.setScene(new Scene(root, 1067.0, 647.0));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onConnectClick(ActionEvent event) throws IOException {
        DatabaseAccount databaseAccount = new DatabaseAccount(username.getText(),
                                                            serverAddress.getText(),
                                                            password.getText(),
                                                            portNumber.getText(),
                                                            dbRestriction.getText());
        if(Datasource.getInstance().checkConnection(databaseAccount)) {
            this.writeToFile(databaseAccount);
            this.showLogin(event);
        }
        else {
            Dialog.getInstance().errorDialog("Could not connect!");
        }
    }

}
