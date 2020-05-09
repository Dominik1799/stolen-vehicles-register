package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import datasource.ThreadCriminalAge;
import entities.CriminalAgeGroup;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utilities.Dialog;

import java.net.URL;
import java.util.ResourceBundle;

public class casesCriminalAgeController extends casesSceneController{


    public TableColumn<CriminalAgeGroup, String > criminalGroupName;
    public TableColumn<CriminalAgeGroup, Integer> criminalAmount;
    public TableColumn<CriminalAgeGroup, Integer> averageAmount;
    public JFXRadioButton youngest;
    public JFXRadioButton youngerThan;
    public JFXTextField criminalGroupAge;
    public TableView tableView1;
    public JFXButton back1;
    public JFXButton next1;
    public JFXTextField criminalGroupSearch;
    public JFXProgressBar progressBar1;


    int offset;
    String name;
    Integer age;
    boolean desc = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criminalGroupName.setCellValueFactory(new PropertyValueFactory<CriminalAgeGroup, String>("groupName"));
        criminalAmount.setCellValueFactory(new PropertyValueFactory<CriminalAgeGroup, Integer>("criminalAmount"));
        averageAmount.setCellValueFactory(new PropertyValueFactory<CriminalAgeGroup, Integer>("averageAge"));
        prepareSlideMenuAnimation();
    }



    public void setUpTable(ThreadCriminalAge threadCriminalAge) {
        Thread t = new Thread(threadCriminalAge::parseCriminals);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()) {
                progressBar1.setVisible(true);
            }
            progressBar1.setVisible(false);
            tableView1.setItems((ObservableList) threadCriminalAge.getGroups());
        });
        watcher.start();
    }
    public void onNextClick(ActionEvent event) {
        back1.setDisable(false);
        this.offset = this.offset + step;
        ThreadCriminalAge threadCriminalAge = new ThreadCriminalAge(this.name, this.offset, this.age, this.getCompareSymbol(), this.desc);
        setUpTable(threadCriminalAge);
    }

    public void onBackClick(ActionEvent event) {
        this.offset = this.offset - step;
        if (this.offset == 0)
            back1.setDisable(true);
        ThreadCriminalAge threadCriminalAge = new ThreadCriminalAge(this.name, this.offset, this.age, this.getCompareSymbol(), this.desc);
        setUpTable(threadCriminalAge);
    }

    private String getCompareSymbol() {
        String compare = ">";
        if(youngerThan.isSelected()) compare = "<";
        return compare;
    }

    public void onFindDatesClick(ActionEvent event) {
        this.name = criminalGroupSearch.getText();
        this.offset = 0;
        try {
            if(!criminalGroupAge.getText().isEmpty())
                this.age = Integer.parseInt(criminalGroupAge.getText());
        }
        catch (NumberFormatException  e) {
            Dialog.getInstance().errorDialog("Only integer allowed!");
            return;
        }
        if(youngest.isSelected()) this.desc = false;

        ThreadCriminalAge threadCriminalAge = new ThreadCriminalAge(this.name, this.offset, this.age, this.getCompareSymbol(), this.desc);
        setUpTable(threadCriminalAge);
        next1.setDisable(false);
    }
}
