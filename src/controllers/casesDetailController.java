package controllers;

import com.jfoenix.controls.JFXProgressBar;
import entities.Case;
import entities.Criminal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class casesDetailController extends userSceneController implements Initializable {
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

    public void setDetails(){
        this.caseId.setText(String.valueOf(this.aCase.getId()));
        this.status.setText(String.valueOf(this.aCase.getStatus()));
        this.severity.setText(String.valueOf(this.aCase.getSeverity()));
        this.description.setText(this.aCase.getDescription());
        if (this.aCase.getCriminalGroup().getId()== 0){
            this.nameofgroup.setText("No crimminal group");
        } else {
            this.nameofgroup.setText(this.aCase.getCriminalGroup().getGroupName());
        }
        Criminal c = this.aCase.getCriminalGroup().getLeader();
        this.leader.setText(c.getName() + " " + c.getSurname());
    }


}
