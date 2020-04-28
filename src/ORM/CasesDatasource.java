package ORM;

import entities.Case;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class CasesDatasource extends ManageDatasource{
    protected static CasesDatasource instance = null;


    public static CasesDatasource getInstance() {
        if (instance == null)
            instance = new CasesDatasource();
        return instance;
    }


    public Case getCases() {
        this.createConnection();
        Case c;
        try (Session session = factory.openSession()) {
            Transaction tx = null;
            c = null;

            try {
                tx = session.beginTransaction();
                //do something
                c = (Case) session.get(Case.class, 5);
                System.out.println("Criminal group:" + c.getCriminalGroup());
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return c;
    }
}
