package controllers;

import entities.Criminal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class criminalDetailController implements Initializable {
    private Criminal criminal;
    @FXML
    private Text name,sex,dateofbirth,nationality,description,age,group;
    @FXML
    private Tab associates;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setDetails(){
        this.name.setText(criminal.getName() + " " + criminal.getSurname());
        this.sex.setText(criminal.getSex());
        this.dateofbirth.setText(criminal.getBirthday());
        this.age.setText(String.valueOf(criminal.getAge()));
        this.description.setText(criminal.getDescription());
        this.nationality.setText(criminal.getNationality());
        if (criminal.getGroupID() == 0){
            this.group.setText("No crimminal group");
            this.associates.setDisable(true);
        } else {
            this.group.setText(criminal.getGroup());
            setAssociates();
        }


    }

    public void setCriminal(Criminal criminal) {
        this.criminal = criminal;

    }

    private void setAssociates(){

    }


}
