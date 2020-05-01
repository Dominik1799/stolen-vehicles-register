package controllers;

import datasource.ThreadOwners;
import entities.Owner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ownerSceneController extends vehicleSceneController{
    ObservableList<Owner> owners = FXCollections.observableArrayList();
    @FXML private TableView<Owner> tableView;
    @FXML private TableColumn<Owner, Integer> count;
    @FXML private TableColumn<Owner, String> firstname, lastname;

    public void initialize(URL url, ResourceBundle rb) {
        prepareSlideMenuAnimation();
        firstname.setCellValueFactory(new PropertyValueFactory<Owner, String>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<Owner, String>("lastname"));
        count.setCellValueFactory(new PropertyValueFactory<Owner, Integer>("count"));
        progressBar.setVisible(false);
    }

    public void listTables() {
        String tempName = "", tempAmount = "16";
        if(!this.nameFilter.getText().isEmpty()) tempName = this.nameFilter.getText();
        if (!this.amountFilter.getText().isEmpty()) tempAmount = this.amountFilter.getText();
        System.out.println(tempAmount + tempName);
        this.setUpTable(new ThreadOwners(tempName, tempAmount));
    }

    public void setUpTable(ThreadOwners threadOwners){
        Thread t = new Thread(threadOwners::parseVehicles);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressBar.setVisible(true);
            }
            progressBar.setVisible(false);
            tableView.setItems(threadOwners.getVehicles());
        });
        watcher.start();
    }

}
