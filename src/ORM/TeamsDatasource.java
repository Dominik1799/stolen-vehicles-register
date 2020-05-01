package ORM;

import entities.Team;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
        this.createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.enableFilter("validMember");
        session.enableFilter("activeCase");
        Team team = session.get(Team.class,currentUserTeamId);
        tx.commit();
        session.close();
        return team;
    }

    public List<Team> getAllTeams(int pageNum){
        this.createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Query query = session.createQuery("from Team where ca.size<5");
        query.setFirstResult(pageNum);
        query.setMaxResults(16);
        List<Team> teams = (List<Team>) query.list();
        tx.commit();
        session.close();
        return teams;
    }


}
