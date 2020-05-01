package ORM;

import entities.Team;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;


public class TeamsDatasource  extends ManageDatasource {
    protected static TeamsDatasource instance = null;

    public static TeamsDatasource getInstance() {
        if (instance == null)
            instance = new TeamsDatasource();
        return instance;
    }
    private TeamsDatasource(){

    }

    public Team getCurrentUserTeam(int currentUserTeamId){
        List results;
        this.createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.enableFilter("validMember");
        Team team = session.get(Team.class,currentUserTeamId);
        tx.commit();
        session.close();
        return team;
    }

    private TeamsDatasource() {
    }

}
