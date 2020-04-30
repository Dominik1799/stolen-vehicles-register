package ORM;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class TeamsDatasource  extends ManageDatasource {
    private final String TEAMS_SELECT = "SELECT t.id, u.firstname, u.lastname " +
                                        "FROM team t INNER JOIN users u ON " +
                                        "t.leaderid = u.id";
    protected static TeamsDatasource instance = null;

    public static TeamsDatasource getInstance() {
        if (instance == null)
            instance = new TeamsDatasource();
        return instance;
    }
    private TeamsDatasource(){

    }

    public void getTeams(){
        List results;
        this.createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Query query = session.createQuery(TEAMS_SELECT);
        results = query.list();
        tx.commit();
        session.close();
    }


}
