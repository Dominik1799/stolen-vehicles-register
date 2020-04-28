package ORM;

import entities.Case;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;


public class CasesDatasource {
    private static CasesDatasource instance = null;
    private static SessionFactory factory;


    public static CasesDatasource getInstance() {
        if (instance == null)
            instance = new CasesDatasource();
        return instance;
    }

    public CasesDatasource() {
    }

    public void createConnection() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF); //disable logs
        try {
            this.factory = (SessionFactory) new Configuration().configure().addAnnotatedClass(Case.class).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
        }
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
