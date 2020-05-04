package datasource;
import ORM.CasesDatasource;
import entities.Case;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ThreadCases {
    ObservableList<Case> cases = FXCollections.observableArrayList();
    Case kejs;

    public ThreadCases() {
    }

    public ThreadCases(Case kejs) {
        this.kejs = kejs;
    }


    public void parseCases(){
        List<Case> cases = CasesDatasource.getInstance().getCases(this.kejs);
        this.cases = FXCollections.observableArrayList(cases);
    }


    public List<Case> getCases(){
        return this.cases;
    }
}
