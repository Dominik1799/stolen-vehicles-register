package datasource;
import ORM.CasesDatasource;
import entities.Case;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ThreadCases {
    ObservableList<Case> cases = FXCollections.observableArrayList();
    String[] args;

    public ThreadCases(String[] args) {
        this.args = args;
    }


    public void parseCases(){
        List<Case> cases = CasesDatasource.getInstance().getCases(this.args);
        this.cases = FXCollections.observableArrayList(cases);
    }


    public List<Case> getCases(){
        return this.cases;
    }
}
