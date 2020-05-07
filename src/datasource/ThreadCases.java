package datasource;
import ORM.CasesDatasource;
import entities.Case;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ThreadCases {
    ObservableList<Case> cases = FXCollections.observableArrayList();
    String[] args;
    int offset;

    public ThreadCases(int offset, String[] args) {
        this.args = args;
        this.offset = offset;
    }


    public void parseCases(){
        List<Case> cases = CasesDatasource.getInstance().getCases(this.offset, this.args);
        this.cases = FXCollections.observableArrayList(cases);
    }


    public List<Case> getCases(){
        return this.cases;
    }
}
