package ORM;

import entities.Case;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;


public abstract class ManageDatasource {
    protected static SessionFactory factory;

    public void createConnection() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF); //disable logs
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
        }
    }
}
