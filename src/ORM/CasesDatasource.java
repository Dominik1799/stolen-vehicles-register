package ORM;

import entities.Case;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class CasesDatasource extends ManageDatasource{
    protected static CasesDatasource instance = null;

    private CasesDatasource() {
    }

    public static CasesDatasource getInstance() {
        if (instance == null)
            instance = new CasesDatasource();
        return instance;
    }

    //TODO: function should return list of cases, not only one...
    public List<Case> getCases() {
        this.createConnection();
        List<Case> cases;
        try (Session session = factory.openSession()) {
            Transaction tx = null;
            cases = null;

            try {
                tx = session.beginTransaction();
                //do something

                cases = (List<Case>) session.get(Case.class, 1);

                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return cases;
    }
}
