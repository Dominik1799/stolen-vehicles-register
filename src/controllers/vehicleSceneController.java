package controllers;
import com.jfoenix.controls.JFXProgressBar;
import datasource.ThreadVehicles;
import entities.Criminal;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class vehicleSceneController extends userSceneController {
    ObservableList<Criminal> vehicles = FXCollections.observableArrayList();
    @FXML JFXProgressBar progressBar;
    @FXML private TableView<Vehicle> tableView;
    @FXML private TableColumn<Vehicle, Integer> id, count, modelYear;
    @FXML private TableColumn<Vehicle, String> brand, model, vin;

    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();

        id.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("id"));
        count.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("count"));
        modelYear.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("modelYear"));

        brand.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("brand"));
        model.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
        vin.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vin"));
        this.setUpTable(new ThreadVehicles());
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
                "../scenes/vehiclesScene.fxml"));
        Parent root = (Parent) loader.load();
        vehicleSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    public void onOwnersClick(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "../scenes/ownersScene.fxml"));
        Parent root = (Parent) loader.load();
        ownerSceneController ctrl = loader.getController();
        ctrl.setUser(user);
        ctrl.showName();
        Scene scene2 = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
}
