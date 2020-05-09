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
    String compareSymbol;

    public ThreadCases(int offset,String compareSymbol, String[] args) {
        this.args = args;
        this.offset = offset;
        this.compareSymbol = compareSymbol;
    }


    public void parseCases(){
        List<Case> cases = CasesDatasource.getInstance().getCases(this.offset,this.compareSymbol, this.args);
        this.cases = FXCollections.observableArrayList(cases);
    }


    public List<Case> getCases(){
        return this.cases;
    }
}
