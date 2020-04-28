package ORM;

import entities.Case;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;


public abstract class ManageDatasource {
    protected static CasesDatasource instance = null;
    protected static SessionFactory factory;


    public static CasesDatasource getInstance() {
        if (instance == null)
            instance = new CasesDatasource();
        return instance;
    }

    public void createConnection() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF); //disable logs
        try {
            this.factory = (SessionFactory) new Configuration().configure().addAnnotatedClass(Case.class).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
        }
    }
}
