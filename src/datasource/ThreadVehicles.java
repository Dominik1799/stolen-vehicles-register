package datasource;
import entities.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThreadVehicles {
    ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();

    public void parseVehicles() {
        ResultSet rs = Datasource.getInstance().getTopCars();
        try {
            while (rs.next()){
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setCount(rs.getInt("count"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setModelYear(rs.getInt("modelyear"));
                vehicle.setVin(rs.getString("vin"));
                this.vehicles.add(vehicle);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public ObservableList<Vehicle> getVehicles() { return this.vehicles; }

}
