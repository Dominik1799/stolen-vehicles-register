package datasource;
import entities.Criminal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThreadCriminals {
    private int offset = 0;
    private String[] args;
    ObservableList<Criminal> criminals = FXCollections.observableArrayList();
    public ThreadCriminals(int offset,String ... args){
        this.offset = offset;
        this.args = args;
    }
    public void parseCriminals(){
        ResultSet rs = Datasource.getInstance().getCriminalsWithOffset(this.offset,this.args);
        try {
            while (rs.next()){
                String id = rs.getString("id");
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String description = rs.getString("description");
                String nationality = rs.getString("nationality");
                String sex = Datasource.getInstance().translateSex(rs.getInt("sex"));
                LocalDate birthday = rs.getDate("birthdate").toLocalDate();
                int caseid = rs.getInt("case");
                int groupId = rs.getInt("criminalgroup");
                String criminalGroup = Datasource.getInstance().getGroupName(groupId);
                String groupAmount = Datasource.getInstance().getGroupAmount(groupId);
                Criminal criminal = new Criminal(id, fname,lname,sex,nationality,description,caseid,criminalGroup,birthday, groupAmount,groupId);
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
