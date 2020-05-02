package datasource;

import ORM.TeamsDatasource;
import entities.Case;
import entities.Criminal;
import entities.Team;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.List;

public class ThreadTeams {
    int teamID;
    public int pageNum;
    Team team;
    public Team teamToUser;
    public User userToTeam;
    public String[] args;
    ObservableList<User> members = FXCollections.observableArrayList();
    ObservableList<Case> activeCases = FXCollections.observableArrayList();

    ObservableList<Team> teams = FXCollections.observableArrayList();


    public void getTeamData(){
        this.team = TeamsDatasource.getInstance().getCurrentUserTeam(this.teamID);
        this.members = FXCollections.observableArrayList(team.getMembers());
        this.activeCases = FXCollections.observableArrayList(team.getActiveCases());
    }

    public Team getTeam() {
        return team;
    }

    public void addUserToTeam(){
        Datasource.getInstance().addUserToTeam(this.userToTeam,this.teamToUser);
    }

    public void removeUserFromTeam(){
        Datasource.getInstance().removeUserFromTeam(this.userToTeam,this.teamToUser);
    }


    public void getOnePageOfTeams(){
        List<Team> teams = TeamsDatasource.getInstance().getAllTeams(pageNum,args);
        this.teams = FXCollections.observableArrayList(teams);
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public void setTeamID(String teamID){
        this.teamID = Integer.parseInt(teamID);
    }

    public ObservableList<User> getMembers() {
        return members;
    }

    public ObservableList<Case> getActiveCases() {
        return activeCases;
    }
}
