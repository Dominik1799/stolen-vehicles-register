package datasource;

import entities.User;

import javax.jws.soap.SOAPBinding;
import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Datasource {
    private static Datasource instance = null;
    private Datasource(){ }

    public static Datasource getInstance() {
        if (instance == null)
            instance = new Datasource();

        return instance;
    }

    private Connection openConnection(){
        try {
            String PASSWORD = "Welcome1";
            String USER_NAME = "DB_FIIT";
            String URL = "jdbc:postgresql://postgresql.websupport.sk:5432/DB_FIIT";
            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Always use this function to close opened connections
    private void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createUser(String FirstName,String LastName,String dateOfBirth,String sex, String rank){
        String sql = "INSERT INTO users(firstname,lastname,birthdate,rank,sex) VALUES (?,?,?,?,?)";
        Connection conn = openConnection();
        if (conn == null){
            System.out.println("Something went wrong");
            return 0;
        }
        try {
            ResultSet sexrs = conn.createStatement().executeQuery("SELECT id FROM sex WHERE sex='" + sex + "'");
            ResultSet ranksrs = conn.createStatement().executeQuery("SELECT id FROM rank WHERE rank='" + rank + "'");
            sexrs.next();
            ranksrs.next();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,FirstName);
            statement.setString(2,LastName);
            statement.setDate(3, java.sql.Date.valueOf(dateOfBirth));
            statement.setInt(4,ranksrs.getInt("id"));
            statement.setInt(5,sexrs.getInt("id"));
            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return 0;
    }
    // function that can be used to get values from enums. Simply pass table name and it will return all rows.
    public ResultSet selectAllFrom(String tableName){
        Connection conn = openConnection();
        if (conn == null){
            System.out.println("Something went wrong");
            return null;
        }
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            closeConnection(conn);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public String getRank(Connection connection, String rankIndex) {
        try {
            ResultSet rank = connection.createStatement().executeQuery("SELECT * FROM rank where id = " + rankIndex);
            rank.next();
            return rank.getString("rank");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getTeamID(Connection connection, String teamIndex) {
        try {
            ResultSet team = connection.createStatement().executeQuery("SELECT * FROM team_changes WHERE userid = " + teamIndex);
            team.next();
            return team.getString("teamid");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSex(Connection connection, String sexIndex) {
        try {
            ResultSet rank = connection.createStatement().executeQuery("SELECT * FROM sex where id = " + sexIndex);
            rank.next();
            return rank.getString("sex");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User checkLoggingData(String id) {
        String query = "SELECT * FROM Users WHERE id='" + id+ "';";
        Connection connection = openConnection();
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            User user = new User();
            ResultSet result = statement.executeQuery(query);

            if(!result.isBeforeFirst()) {
                // Noone with this credentials in database
                return null;
            }
            result.next();


            user.setFirstName(result.getString("firstName"));
            user.setLastName(result.getString("lastName"));
            user.setSex(this.getSex(connection, result.getString("sex")));
            user.setRank(this.getRank(connection, result.getString("rank")));
            user.setTeam(this.getTeamID(connection, result.getString("id")));


            return user;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getCriminalsWithOffset(int offset){
        String querry = "SELECT * FROM criminal LIMIT 20 OFFSET " + String.valueOf(offset);
        Connection connection = openConnection();
        if (connection == null){
            System.out.println("Something went wrong");
            return null;
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            closeConnection(connection);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public String translateSex(int id){
        String querry = "SELECT sex FROM sex WHERE id=" + id;
        Connection connection = openConnection();
        if (connection == null){
            System.out.println("Something went wrong");
            return null;
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(querry);
            closeConnection(connection);
            String result = "";
            while (rs.next())
                result = rs.getString("sex");
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return "";
    }

}
