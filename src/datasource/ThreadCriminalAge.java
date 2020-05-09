package datasource;
import entities.CriminalAgeGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ThreadCriminalAge {
    ObservableList<CriminalAgeGroup> groups = FXCollections.observableArrayList();
    int offset;
    Integer age;
    boolean desc;
    String compareSymbol;
    String criminalName;

    public ThreadCriminalAge(String name, int offset, Integer age, String compareSymbol, boolean desc) {
        this.criminalName = name;
        this.offset = offset;
        this.age = age;
        this.desc = desc;
        this.compareSymbol = compareSymbol;
    }

    public void parseCriminals(){
        List<CriminalAgeGroup> groups = Datasource.getInstance().findAgeCriminals(this.criminalName, this.offset, this.age, this.compareSymbol, this.desc);
        this.groups = FXCollections.observableArrayList(groups);
    }


    public List<CriminalAgeGroup> getGroups(){
        return this.groups;
    }
}
