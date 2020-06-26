package datasource;

import entities.*;
import javafx.collections.FXCollections;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.List;

public class Datasource {
    private static Datasource instance = null;

    private Datasource() {
    }

    public static Datasource getInstance() {
        if (instance == null)
            instance = new Datasource();

        return instance;
    }


    private DatabaseAccount getAccountData() {
        // loads serialized object of account information from file account_credentials.ser
        DatabaseAccount databaseAccount;
        try {
            FileInputStream fileIn = new FileInputStream("account_credentials.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            databaseAccount = (DatabaseAccount) in.readObject();
            in.close();
            fileIn.close();
            return databaseAccount;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Connection openConnection() {
        try {
            DatabaseAccount account = this.getAccountData();
            return DriverManager.getConnection(account.getURL(), account.getUsername(), account.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkConnection(DatabaseAccount account) {
        // Checks if there could be a connection with the server
        try {
            Connection connection = DriverManager.getConnection(account.getURL(), account.getUsername(), account.getPassword());
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            ResultSet team = connection.createStatement().executeQuery("SELECT * FROM team_changes WHERE userid = " + teamIndex + " AND status=1");
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
        String query = String.format("SELECT * FROM Users WHERE id='%s';", id);

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


            user.setId(result.getInt("id"));
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

    public ResultSet getCriminalsWithOffset(int offset,String ... args) {
        String query = buildQueryForCrimminals(args) + " ORDER BY id LIMIT 16 OFFSET " + offset;
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

    private String buildQueryForCrimminals(String ... args){
        String[] conditions = {" WHERE firstname='?'"," WHERE lastname='?'"," WHERE nationality='?'"," WHERE sex=?"," WHERE criminalgroup=?"};
        boolean isAlreadyConditioned = false;
        StringBuilder finalQuery = new StringBuilder("SELECT * FROM criminal");
        if (args.length == 0){
            return finalQuery.toString();
        }
        for (int i = 0; i<args.length;i++){
            if (args[i].equals(""))
                continue;
            if (isAlreadyConditioned){
                finalQuery.append(" AND");
                conditions[i] = conditions[i].replace(" WHERE","");
            }
            isAlreadyConditioned = true;
            finalQuery.append(conditions[i].replace("?",args[i]));
        }
        return finalQuery.toString();
    }



    public String translateSex(int id,String sex) {
        String query = "SELECT sex FROM sex WHERE id=" + id;
        if (!sex.equals(""))
            query = "SELECT id FROM sex WHERE sex='?'".replace("?",sex);
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
                if (!sex.equals(""))
                    return String.valueOf(rs.getInt("id"));
                else
                    return rs.getString("sex");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return "";
    }


    public void deleteCriminal(Criminal criminal) {
        String query = String.format("DELETE FROM criminal WHERE id='%s';", criminal.getId());
        String updateMemberAmount = "UPDATE criminalgroup SET member_amount=$$ WHERE id=" + criminal.getGroupID();
        Connection connection = openConnection();
        try {
            connection.createStatement().executeUpdate(query);
            connection.createStatement().execute(updateMemberAmount.replace("$$",String.valueOf(Integer.parseInt(criminal.getGroupAmount())- 1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getGroupName(Integer id)  {
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

    public void addUserToTeam(User user, Team team){
        String updateTeam = "UPDATE team SET memberamount=memberamount+1 WHERE id=?";
        String associateUserAndTeam = "INSERT INTO team_changes(id,userid,teamid,status) VALUES (nextval('team_changes_sequence'),?,?,?)";
        Connection connection = openConnection();
        if (connection == null)
            return;
        try {
            connection.setAutoCommit(false);
            PreparedStatement updateTeamPS = connection.prepareStatement(updateTeam);
            PreparedStatement associateUserAndTeamPS = connection.prepareStatement(associateUserAndTeam);

            updateTeamPS.setInt(1,team.getId());
            updateTeamPS.executeUpdate();

            associateUserAndTeamPS.setInt(1,user.getId());
            associateUserAndTeamPS.setInt(2,team.getId());
            associateUserAndTeamPS.setInt(3,1);
            associateUserAndTeamPS.executeUpdate();

            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public void removeUserFromTeam(User user, Team team){
        String updateTeam = "UPDATE team SET memberamount=memberamount-1 WHERE id=?";
        String updateRelation = "UPDATE team_changes SET status=3 WHERE userid=? AND teamid=?";
        Connection connection = openConnection();
        if (connection == null)
            return;
        try {
            connection.setAutoCommit(false);
            PreparedStatement updateTeamPS = connection.prepareStatement(updateTeam);
            PreparedStatement updateRelationPS = connection.prepareStatement(updateRelation);

            updateTeamPS.setInt(1,team.getId());
            updateTeamPS.executeUpdate();

            updateRelationPS.setInt(1,user.getId());
            updateRelationPS.setInt(2,team.getId());
            updateRelationPS.executeUpdate();

            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }


    public String getGroupAmount(Integer id) {
        int memberAmount = 0;
        String checkIfEmpty = "SELECT member_amount FROM criminalgroup WHERE id=" + id;
        String countMembers = "SELECT count(*) FROM criminal WHERE criminalgroup=" + id;
        String updateCriminalGroup = "UPDATE criminalgroup SET member_amount=$$ WHERE id=" + id;
        Connection connection = openConnection();
        if (connection == null)
            return "";
        try {
            ResultSet rs = connection.createStatement().executeQuery(checkIfEmpty);
            rs.next();
            memberAmount = rs.getInt("member_amount");
            if (memberAmount == 0){
                ResultSet rsMemberAmount = connection.createStatement().executeQuery(countMembers);
                rsMemberAmount.next();
                memberAmount = rsMemberAmount.getInt("count");
                connection.createStatement().execute(updateCriminalGroup.replace("$$",String.valueOf(memberAmount)));
                connection.close();
                return String.valueOf(memberAmount);
            }
            return String.valueOf(memberAmount);

        } catch (SQLException e){
            e.printStackTrace();
        }
        this.closeConnection(connection);
        return "";
    }

    public ResultSet getTopCars(String name, String amount) {

        String query = String.format("SELECT vehicles.id, count, concat(owners.firstname, ' ', owners.lastname) AS ownername, brand, model, modelyear, vin \n" +
                        "FROM (SELECT vehicleid, COUNT(*) " +
                        "FROM vehicle_history " +
                        "GROUP BY vehicleid " +
                        "ORDER BY COUNT DESC) AS topvehicles " +
                        "INNER JOIN vehicles ON " +
                        "vehicles.id = topvehicles.vehicleid " +
                        "inner join owners on " +
                        "owner = owners.id " +
                        "WHERE UPPER(vehicles.model) LIKE UPPER('%%%s%%') OR UPPER(vehicles.brand) LIKE UPPER('%%%s%%')" +
                        "LIMIT %s", name, name, amount);

        Connection connection = openConnection();
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            closeConnection(connection);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getTopOwners(String name, String amount) {
        String query = String.format("SELECT id, firstname,lastname,vehicleCount " +
                "FROM owners INNER JOIN (SELECT owner,count(*) AS vehicleCount FROM vehicles GROUP BY owner) " +
                "table2 on table2.owner=id WHERE UPPER(concat(owners.firstname, ' ', owners.lastname)) LIKE UPPER('%%%s%%') " +
                "AND vehicleCount > 1 " +
                "ORDER BY vehicleCount desc LIMIT %s;", name, amount);

        Connection connection = openConnection(); // :(
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            closeConnection(connection);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CriminalAgeGroup> findAgeCriminals(String criminalName, int offset, Integer age, String compare, boolean desc) {

        String clause = "";
        if(age != null)
            clause = String.format(" AND demand.averageage %s %s ",compare, age);

        String query = String.format(
                "SELECT * " +
                "FROM (SELECT DISTINCT " +
                "criminal.criminalgroup, " +
                "groupname , " +
                "COUNT(*) OVER (PARTITION BY criminal.criminalgroup) AS CriminalAmount, " +
                "EXTRACT(YEAR FROM (AVG(AGE(now(),birthdate)) over (PARTITION BY criminal.criminalgroup))) AS averageAge " +
                "FROM criminal " +
                "INNER JOIN criminalgroup " +
                "ON criminal.criminalgroup = criminalgroup.id) AS  demand " +
                "WHERE  demand.criminalgroup > 0 AND " +
                "UPPER(groupname) LIKE UPPER('%%%s%%') %s" +
                "ORDER BY averageage ", criminalName, clause);

        if(desc) query += "DESC ";
        query += "OFFSET " + offset + " LIMIT 16;";

        Connection connection = openConnection(); // :(
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            closeConnection(connection);
            List<CriminalAgeGroup> list = FXCollections.observableArrayList();

            while (result.next()) {
                String name = null;
                try {
                    name = result.getString("groupname");
                    Integer criminalAmount = result.getInt("criminalamount");
                    Integer averageAge = result.getInt("averageage");
                    list.add(new CriminalAgeGroup(name, criminalAmount, averageAge));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




}
