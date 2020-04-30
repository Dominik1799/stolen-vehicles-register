package datasource;
import ORM.CasesDatasource;
import entities.Case;
import entities.Criminal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThreadCases {
    private int offset = 0;
    private String[] args;
    ObservableList<Case> cases = FXCollections.observableArrayList();
    public ThreadCases(int offset,String ... args){
        this.offset = offset;
        this.args = args;
    }
    public void parseCases(){
        this.cases = (ObservableList<Case>) CasesDatasource.getInstance().getCases();
    }

    public ObservableList<Case> getCases(){
        return this.cases;
    }
}
