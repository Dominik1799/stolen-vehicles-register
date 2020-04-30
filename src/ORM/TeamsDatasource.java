package ORM;

public class TeamsDatasource  extends ManageDatasource {
    protected static TeamsDatasource instance = null;

    public static TeamsDatasource getInstance() {
        if (instance == null)
            instance = new TeamsDatasource();
        return instance;
    }

    private TeamsDatasource() {
    }

}
