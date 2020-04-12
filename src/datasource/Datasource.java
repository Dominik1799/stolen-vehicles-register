package datasource;

import entities.Criminal;
import entities.User;

import java.sql.*;

public class Datasource {
    private static Datasource instance = null;

    private Datasource() {
    }

    public static Datasource getInstance() {
        if (instance == null)
            instance = new Datasource();

        return instance;
    }

    private Connection openConnection() {
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
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int createUser(String FirstName, String LastName, String dateOfBirth, String sex, String rank) {
        String sql = "INSERT INTO users(firstname,lastname,birthdate,rank,sex) VALUES (?,?,?,?,?)";
        String getID = "SELECT max(id) FROM users";
        Connection conn = openConnection();
        if (conn == null) {
            System.out.println("Something went wrong");
            return 0;
        }
        try {
            ResultSet sexrs = conn.createStatement().executeQuery("SELECT id FROM sex WHERE sex='" + sex + "'");
            ResultSet ranksrs = conn.createStatement().executeQuery("SELECT id FROM rank WHERE rank='" + rank + "'");
            ResultSet id = conn.createStatement().executeQuery("SELECT max(id) FROM users");
            id.next();
            sexrs.next();
            ranksrs.next();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, FirstName);
            statement.setString(2, LastName);
            statement.setDate(3, java.sql.Date.valueOf(dateOfBirth));
            statement.setInt(4, ranksrs.getInt("id"));
            statement.setInt(5, sexrs.getInt("id"));
            int status = statement.executeUpdate();
            if (status == 1)
                return id.getInt("max");
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return 0;
    }

    // function that can be used to get values from enums. Simply pass table name and it will return all rows.
    public ResultSet selectAllFrom(String tableName) {
        Connection conn = openConnection();
        if (conn == null) {
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

    public void updateUser(User user) {

        String query = String.format("UPDATE Users SET firstname= '%s', lastname= '%s', birthdate= '%s' WHERE id= '%s';", user.getFirstName(), user.getLastName(), user.getBirthdate(), user.getId());
        Connection connection = openConnection();
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getRank(Connection connection, String rankIndex) {
        try {
            ResultSet rank = connection.createStatement().executeQuery("SELECT * FROM rank where id = " + rankIndex);
            rank.next();
            return rank.getString("rank");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getTeamID(Connection connection, String teamIndex) {
        try {
            ResultSet team = connection.createStatement().executeQuery("SELECT * FROM team_changes WHERE userid = " + teamIndex);
            if (!team.next())
                return "Not a member of any team";
            return team.getString("teamid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSex(Connection connection, String sexIndex) {
        try {
            ResultSet rank = connection.createStatement().executeQuery("SELECT * FROM sex where id = " + sexIndex);
            rank.next();
            return rank.getString("sex");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User checkLoggingData(String id) {
        String query = "SELECT * FROM Users WHERE id='" + id + "';";

        Connection connection = openConnection();
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            User user = new User();
            ResultSet result = statement.executeQuery(query);

            if (!result.isBeforeFirst()) {
                // Noone with this credentials in database
                return null;
            }
            result.next();


            user.setId(result.getString("id"));
            user.setFirstName(result.getString("firstName"));
            user.setLastName(result.getString("lastName"));
            user.setSex(this.getSex(connection, result.getString("sex")));
            user.setRank(this.getRank(connection, result.getString("rank")));
            user.setTeam(this.getTeamID(connection, result.getString("id")));
            user.setBirthdate(result.getDate("birthdate"));

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getCriminalsWithOffset(int offset) {
        String query = "SELECT * FROM criminal ORDER BY id LIMIT 16 OFFSET " + String.valueOf(offset);
        Connection connection = openConnection();
        if (connection == null) {
            System.out.println("Something went wrong");
            return null;
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            closeConnection(connection);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }

    public String translateSex(int id) {
        String query = "SELECT sex FROM sex WHERE id=" + id;
        Connection connection = openConnection();
        if (connection == null) {
            System.out.println("Something went wrong");
            return null;
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
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


    public void deleteCriminal(Criminal criminal) {
        String query = String.format("DELETE FROM criminal WHERE id='%s';", criminal.getId());
        Connection connection = openConnection();
        try {
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getGroupName(Integer id) throws SQLException {
        String query = String.format("SELECT groupname FROM criminalgroup WHERE id=%s;", id);
        Connection connection = openConnection();
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            result.next();
            closeConnection(connection);
            return result.getString("groupname");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getGroupAmount(Integer id) {
        //literally the same god damn method as previous but with different query
        // what a fuckin joke
        String query = String.format("SELECT criminal.criminalgroup, COUNT(*) " +
                "FROM criminal " +
                "WHERE criminal.criminalgroup=%s " +
                "GROUP BY criminal.criminalgroup;", id);

        Connection connection = openConnection();
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            result.next();
            closeConnection(connection);
            return result.getString("count");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
