package controllers;

import ORM.CasesDatasource;
import com.jfoenix.controls.JFXProgressBar;
import datasource.Datasource;
import entities.Case;
import entities.Criminal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class casesDetailController implements Initializable {
    Case aCase;
    @FXML
    private Text caseId, status, severity, nameofgroup, leader, description;
    @FXML
    JFXProgressBar progressBar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Case getaCase() {
        return aCase;
    }

    public void setaCase(Case aCase) {
        this.aCase = aCase;
    }

    private List<String> getStatuses() {
        //creates list of statuses in the combobox
        List<String> statuses = new ArrayList<>();
        ResultSet casestatus = Datasource.getInstance().selectAllFrom("case_status");
        try {
            while (casestatus.next())
                statuses.add(casestatus.getString("status"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return statuses;
    }


    public void setDetails(){
        this.caseId.setText(String.valueOf(this.aCase.getId()));
        this.status.setText(getStatuses().get(this.aCase.getStatusId()-1));
        this.severity.setText(String.valueOf(this.aCase.getSeverity()));
        this.description.setText(this.aCase.getDescription());
        if (this.aCase.getCriminalGroup().getId()== 0){
            this.nameofgroup.setText("No crimminal group");
        } else {
            this.nameofgroup.setText(this.aCase.getCriminalGroup().getGroupName());
        }
        String leaderName = aCase.getLeaderName();
        if(leaderName != null)
            this.leader.setText(aCase.getLeaderName());
        else {
            Criminal c = CasesDatasource.getInstance().getLeader(this.aCase.getId());
            if(c != null)
                this.leader.setText(c.getName() + " " + c.getSurname());
            else
                this.leader.setText("Unknown");
        }
    }


}
