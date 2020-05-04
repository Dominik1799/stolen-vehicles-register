package datasource;
import ORM.CasesDatasource;
import entities.Case;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ThreadCases {
    ObservableList<Case> cases = FXCollections.observableArrayList();
    private int groupId = 0;
    private int limit = 16;
    private String name;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ThreadCases() {
    }

    public ThreadCases(String name) {
        this.name = name;
    }

    public void parseCases(){
        List<Case> cases = CasesDatasource.getInstance().getCases(this.limit);
        this.cases = FXCollections.observableArrayList(cases);
    }

    public void creatingCases() {
        this.groupId = CasesDatasource.getInstance().getCriminalGroupId(this.name);
    }


    public List<Case> getCases(){
        return this.cases;
    }
}
