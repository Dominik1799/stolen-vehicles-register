package ORM;
import entities.Case;
import entities.Criminal;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilities.Dialog;

import java.util.List;

public class CasesDatasource extends ManageDatasource{
    protected static CasesDatasource instance = null;
    protected int defaultLimit = 17;

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
        Transaction tx;
        tx = session.beginTransaction();
        session.save(kejs);
        tx.commit();
    }

    public Integer getCriminalGroupId(String criminalName) {
        // finds the criminal based on his name and returns id of his criminalgroup as integer
        this.createConnection();
        Session session = factory.openSession();

        String hql = String.format("SELECT C.id FROM Criminal C WHERE UPPER(CONCAT(C.name, ' ' , C.surname)) LIKE UPPER('%%%s%%')", criminalName);
        //should have more restrictions to avoid collisions of names but whatever
        Query<String> query = session.createQuery(hql);
        List<String> results = query.list();
        if(results.isEmpty()) {
            Dialog.getInstance().errorDialog("No criminal with that name was found");
            return 0;
        }
        return Integer.valueOf(results.get(0));
    }

    public List<Case> getCases(int offset, String compareSymbol, String ... args) {
        String hql = this.buildQuery(compareSymbol, args);
        this.createConnection();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Query<Case> query;
        query = session.createQuery(hql);
        query.setFirstResult(offset);
        query.setMaxResults(this.defaultLimit);
        List<Case> cases = query.list();
        tx.commit();
        session.close();
        return cases;
    }

    public Criminal getLeader(int caseId) {
        this.createConnection();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        String hql = "SELECT C FROM Criminal C WHERE C.caseid = :id";
        Query<Criminal> query = session.createQuery(hql);
        query.setParameter("id", caseId);
        List<Criminal> leader = query.list();
        tx.commit();
        session.close();
        if(leader.isEmpty()) return null;
        return leader.get(0);

    }

    private String buildQuery(String compareSymbol, String ... args){
        String severity = String.format(" WHERE C.severity%s?", compareSymbol);
        String[] conditions = {" WHERE UPPER(C.criminalGroup.groupName) LIKE UPPER('%?%')"," WHERE UPPER(C.description) LIKE UPPER('%?%')" , " WHERE C.status=?", severity};
        boolean isAlreadyConditioned = false;
        StringBuilder finalQuery = new StringBuilder("FROM Case C");
        for (int i=0;i<args.length;i++){
            if (args[i] == null || args[i].isEmpty() || args[i].equals("0")) {
                continue;
            }
            if (isAlreadyConditioned){
                finalQuery.append(" AND");
                conditions[i] = conditions[i].replace(" WHERE","");
            }
            isAlreadyConditioned = true;
            finalQuery.append(conditions[i].replace("?",args[i]));
        }
        return finalQuery.toString() + " ORDER BY id";
    }
}
