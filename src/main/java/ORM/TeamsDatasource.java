package ORM;

import entities.Team;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class TeamsDatasource  extends ManageDatasource {
    protected static TeamsDatasource instance = null;
    private final String SELECT_TEAMS_QUERRY = "from Team";

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

    public List<Team> getAllTeams(int pageNum,String ... args){
        this.createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Query query = session.createQuery(buildQuery(args));
        query.setFirstResult(pageNum);
        query.setMaxResults(16);
        List<Team> teams = (List<Team>) query.list();
        tx.commit();
        session.close();
        return teams;
    }

    private String buildQuery(String ... args){
        String[] conditions = {" where memberamount>=?"," where memberamount<=?"," where activeCases.size>=?"," where activeCases.size<=?"};
        boolean isAlreadyConditioned = false;
        StringBuilder finalQuery = new StringBuilder(SELECT_TEAMS_QUERRY);
        for (int i=0;i<args.length;i++){
            if (args[i].equals(""))
                continue;
            if (isAlreadyConditioned){
                finalQuery.append(" and");
                conditions[i] = conditions[i].replace(" where","");
            }
            isAlreadyConditioned = true;
            finalQuery.append(conditions[i].replace("?",args[i]));
        }
        return finalQuery.toString() + " order by id";
    }


}
