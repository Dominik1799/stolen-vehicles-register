package ORM;
import entities.Case;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import utilities.Dialog;

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

    public void saveTable(Case kejs) {
        this.createConnection();
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();

        System.out.println(kejs.getDescription());
        System.out.println(kejs.getCriminalGroup());

        session.save(kejs);
        tx.commit();
    }

    public String getCriminalGroup(String criminalName) {
        // finds the criminal based on his name and returns id of his criminalgroup as string
        String hql = "SELECT Criminal .groupID" +
                "FROM Criminal AS C " +
                "WHERE UPPER(CONCAT(C.name, ' ' , C.surname)) LIKE UPPER('%%:name%%')"; //should have more restrictions to avoid collisions of names but whatever
        Session session = factory.openSession();
        Query query = session.createQuery(hql);
        query.setParameter("name", criminalName);
        List<Integer> results = query.list();
        if(results.isEmpty()) {
            Dialog.getInstance().errorDialog("No criminal with that name was found");
            return null;
        }
        return results.get(0).toString();
    }

    public List<Case> getCases() {
        return null;
    }






}
