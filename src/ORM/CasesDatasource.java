package ORM;
import entities.Case;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
        session.save(kejs);
        tx.commit();
    }


    public int getCriminalGroup(String criminalName) {
        // finds the criminal based on his name and returns id of his criminalgroup as integer
        this.createConnection();
        Session session = factory.openSession();


        String hql = String.format("SELECT C.groupID FROM Criminal AS C WHERE UPPER(CONCAT(C.name, ' ' , C.surname)) LIKE UPPER('%%%s%%')", criminalName);
        //should have more restrictions to avoid collisions of names but whatever
        System.out.println(hql);
        Query query = session.createQuery(hql);
        //query.setParameter("name", criminalName);
        List<Integer> results = query.list();
        if(results.isEmpty()) {
            Dialog.getInstance().errorDialog("No criminal with that name was found");
            return 0;
        }
        System.out.println(results.get(0).toString());
        return results.get(0);
    }

    public List<Case> getCases() {
        return null;
    }






}
