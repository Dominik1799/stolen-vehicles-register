package controllers;

import com.jfoenix.controls.JFXProgressBar;
import datasource.Datasource;
import entities.Criminal;
import entities.Owner;
import entities.Owner;
import entities.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        this.setUpTable();
    }

    public void setUpTable(){
        ResultSet rs = Datasource.getInstance().getTopOwners();
        try {
            progressBar.setVisible(true);
            while (rs.next()){
                Owner owner = new Owner();
                owner.setFirstname(rs.getString("firstname"));
                owner.setLastname(rs.getString("lastname"));
                owner.setCount(rs.getInt("vehicleCount"));
                this.owners.add(owner);
            }
            tableView.setItems(this.owners);
            progressBar.setVisible(false);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }



}
