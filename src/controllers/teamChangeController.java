package controllers;

import com.jfoenix.controls.JFXProgressBar;
import datasource.ThreadTeams;
import entities.Team;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class teamChangeController implements Initializable {
    private static final int PAGE_SIZE = 16;
    User user;
    int pageNum = 0;
    String[] filter;

    @FXML
    private TableView<Team> tableview;
    @FXML private TableColumn<Team, String> leadername;
    @FXML private TableColumn<Team,String> leadersurname;
    @FXML private TableColumn<Team, Integer> teamid;
    @FXML private TableColumn<Team, Integer> members;
    @FXML private TableColumn<Team, Integer> activecases;

    @FXML
    JFXProgressBar progressbar;

    @FXML
    TextField membersFrom,membersTo,casesFrom,casesTo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leadername.setCellValueFactory(new PropertyValueFactory<Team, String>("leaderName"));
        leadersurname.setCellValueFactory(new PropertyValueFactory<Team, String>("leaderSurname"));
        teamid.setCellValueFactory(new PropertyValueFactory<Team, Integer>("id"));
        members.setCellValueFactory(new PropertyValueFactory<Team, Integer>("memberamount"));
        activecases.setCellValueFactory(new PropertyValueFactory<Team, Integer>("activeCasesCount"));

    }

    public void setUser(User user){
        this.user = user;
    }
    public void setupTable(){
        ThreadTeams threadTeams = new ThreadTeams();
        threadTeams.pageNum = this.pageNum;
        threadTeams.args = this.filter;
        fetchData(threadTeams);
    }

    public void fetchData(ThreadTeams threadTeams){
        Thread t = new Thread(threadTeams::getOnePageOfTeams);
        t.start();
        Thread watcher = new Thread(() -> {
            while (t.isAlive()){
                progressbar.setVisible(true);
            }
            progressbar.setVisible(false);
            tableview.setItems(threadTeams.getTeams());
        });
        watcher.start();

    }

    public void onNextClick(){
        this.pageNum = this.pageNum + PAGE_SIZE;
        setupTable();
    }

    public void onBackClick(){
        if (this.pageNum - PAGE_SIZE < 1)
            return;
        this.pageNum = this.pageNum - PAGE_SIZE;
        setupTable();
    }

    private void parseFilter(){
        this.filter = new String[]{membersFrom.getText(),membersTo.getText(),casesFrom.getText(),casesTo.getText()};
    }

    public void onShowClick(){
        this.pageNum = 0;
        parseFilter();
        setupTable();
    }
}
