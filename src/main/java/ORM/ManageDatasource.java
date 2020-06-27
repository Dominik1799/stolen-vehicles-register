package ORM;

import datasource.Datasource;
import entities.DatabaseAccount;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;


public abstract class ManageDatasource {
    protected static SessionFactory factory;

    private DatabaseAccount getAccountData() {
        // loads serialized object of account information from file account_credentials.ser
        try {
            FileInputStream fileIn = new FileInputStream("account_credentials.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            DatabaseAccount databaseAccount = (DatabaseAccount) in.readObject();
            in.close();
            fileIn.close();
            return databaseAccount;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createConnection() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF); //disable logs
        try {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");

            // Configuring account data from serialized file
            DatabaseAccount account = this.getAccountData();
            assert account != null;
            config.setProperty("hibernate.connection.url", account.getURL());
            config.setProperty("hibernate.connection.username", account.getUsername());
            config.setProperty("hibernate.connection.password", account.getPassword());

            factory = config.configure().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
        }
    }
}
