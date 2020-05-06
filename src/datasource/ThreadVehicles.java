package datasource;
import entities.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThreadVehicles {
    ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
    String name, amount;

    public ThreadVehicles(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public void parseVehicles() {
        ResultSet rs = Datasource.getInstance().getTopCars(this.name, this.amount);
        try {
            while (rs.next()){
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setOwnerName(rs.getString("ownername"));
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
