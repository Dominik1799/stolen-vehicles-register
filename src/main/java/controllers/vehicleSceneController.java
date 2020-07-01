package controllers;

import com.jfoenix.controls.JFXProgressBar;
import datasource.ThreadVehicles;
import entities.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class vehicleSceneController extends userSceneController {
    ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
    @FXML JFXProgressBar progressBar;
    @FXML private TableView<Vehicle> tableView;
    @FXML private TableColumn<Vehicle, Integer> id, count, modelYear;
    @FXML private TableColumn<Vehicle, String> brand, model, vin, ownerName;
    @FXML TextField nameFilter, amountFilter;

    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
        id.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("id"));
        ownerName.setCellValueFactory(new PropertyValueFactory<Vehicle, String >("ownerName"));
        count.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("count"));
        modelYear.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("modelYear"));

        brand.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("brand"));
        model.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
        vin.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vin"));
        progressBar.setVisible(false);
    }

    public void listTables() {
        String tempName = "", tempAmount = "16";
        if(!this.nameFilter.getText().isEmpty()) tempName = this.nameFilter.getText();
        if (!this.amountFilter.getText().isEmpty()) tempAmount = this.amountFilter.getText();

        this.setUpTable(new ThreadVehicles(tempName, tempAmount));
    }


    public void setupFields(String name, String amount) {
        this.nameFilter.setText(name);
        this.amountFilter.setText(amount);
    }

    public void setUpTable(ThreadVehicles threadVehicles){
        Thread t = new Thread(threadVehicles::parseVehicles);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBar.setVisible(true);
            }
            progressBar.setVisible(false);
            tableView.setItems(threadVehicles.getVehicles());
        });
        watcher.start();
    }

    public void onVehiclesClick(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/scenes/vehiclesScene.fxml"));
        Parent root = loader.load();
        vehicleSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.setupFields(nameFilter.getText(), amountFilter.getText());
        ctrl.showName();
        ctrl.listTables();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onOwnersClick(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/scenes/ownersScene.fxml"));
        Parent root = loader.load();
        ownerSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.setupFields(nameFilter.getText(), amountFilter.getText()); //moves the textfield context to next scene
        ctrl.showName();
        ctrl.listTables();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
}
