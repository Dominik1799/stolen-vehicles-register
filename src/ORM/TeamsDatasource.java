package ORM;

public class TeamsDatasource  extends ManageDatasource {
    protected static CasesDatasource instance = null;

    public static CasesDatasource getInstance() {
        if (instance == null)
            instance = new CasesDatasource();
        return instance;
    }


}
