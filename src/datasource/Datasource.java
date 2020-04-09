package datasource;

import entities.User;

import javax.jws.soap.SOAPBinding;
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

    public User checkLoggingData(String id) {
        String query = "SELECT * FROM Users WHERE id='" + id+ "';";
        Connection connection = openConnection();
        try {
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
            user.setSex(result.getString("sex"));
            user.setRank(result.getString("rank"));

            closeConnection(connection);
            return user;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
