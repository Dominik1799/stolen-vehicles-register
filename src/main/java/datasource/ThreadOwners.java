package datasource;
import entities.Owner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThreadOwners {
    ObservableList<Owner> owners = FXCollections.observableArrayList();
    String name;
    String amount;

    public ThreadOwners(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public void parseVehicles() {
        ResultSet rs = Datasource.getInstance().getTopOwners(this.name, this.amount);
        try {
            while (rs.next()){
                Owner owner = new Owner();
                owner.setId(rs.getInt("id"));
                owner.setFirstname(rs.getString("firstname"));
                owner.setLastname(rs.getString("lastname"));
                owner.setCount(rs.getInt("vehiclecount"));
                this.owners.add(owner);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public ObservableList<Owner> getVehicles() { return this.owners; }

}
