package controllers;

import com.jfoenix.controls.JFXProgressBar;
import entities.Case;
import entities.Criminal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class caseDetailController implements Initializable {
    private Case kejs;
    @FXML
    private Text caseId, status, severity, nameofgroup, leader, description;
    @FXML
    JFXProgressBar progressBar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDetails();
    }

    public void setDetails(){
        this.caseId.setText(String.valueOf(kejs.getId()));
        this.status.setText(String.valueOf(kejs.getStatus()));
        this.severity.setText(String.valueOf(kejs.getSeverity()));
        this.description.setText(kejs.getDescription());
        if (kejs.getCriminalGroup().getId()== 0){
            this.nameofgroup.setText("No crimminal group");
        } else {
            this.nameofgroup.setText(kejs.getCriminalGroup().getGroupName());
        }
        Criminal c = kejs.getCriminalGroup().getLeader();
        this.leader.setText(c.getName() + " " + c.getSurname());
    }


}
