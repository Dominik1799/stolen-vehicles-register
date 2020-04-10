package datasource;

import com.jfoenix.controls.JFXProgressBar;
import entities.Criminal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThreadCriminals {
    private int offset = 0;
    ObservableList<Criminal> criminals = FXCollections.observableArrayList();
    public ThreadCriminals(int offset){
        this.offset = offset;
    }
    public void parseCriminals(){
        ResultSet rs = Datasource.getInstance().getCriminalsWithOffset(this.offset);
        try {
            while (rs.next()){
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String description = rs.getString("description");
                String nationality = rs.getString("nationality");
                String sex = Datasource.getInstance().translateSex(rs.getInt("sex"));
                LocalDate birthday = rs.getDate("birthdate").toLocalDate();
                int caseid = rs.getInt("case");
                int criminalGroup = rs.getInt("criminalgroup");
                Criminal criminal = new Criminal(fname,lname,sex,nationality,description,caseid,criminalGroup,birthday);
                this.criminals.add(criminal);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }





    public ObservableList<Criminal> getCriminals(){
        return this.criminals;
    }
}
