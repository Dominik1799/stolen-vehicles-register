package datasource;
import entities.Owner;
import entities.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThreadOwners {
    ObservableList<Owner> owners = FXCollections.observableArrayList();

    public void parseVehicles() {
        ResultSet rs = Datasource.getInstance().getTopOwners();
        try {
            while (rs.next()){
                while (rs.next()){
                    Owner owner = new Owner();
                    owner.setFirstname(rs.getString("firstname"));
                    owner.setLastname(rs.getString("lastname"));
                    owner.setCount(rs.getInt("vehiclecount"));
                    this.owners.add(owner);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public ObservableList<Owner> getVehicles() { return this.owners; }

}
