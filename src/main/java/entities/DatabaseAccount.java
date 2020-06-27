package entities;

import java.io.Serializable;

public class DatabaseAccount implements Serializable {
    private String username, serverAddress, password, portNumber, dbRestriction;

    public String getDbRestriction() {
        return dbRestriction;
    }

    public void setDbRestriction(String dbRestriction) {
        this.dbRestriction = dbRestriction;
    }

    public DatabaseAccount(String username, String serverAddress, String password, String portNumber, String dbRestriction) {
        this.username = username;
        this.serverAddress = serverAddress;
        this.password = password;
        this.portNumber = portNumber;
        this.dbRestriction = dbRestriction;
    }

    public String getUsername() {
        return username;
    }



    public String getURL() {

        String wholeAddress =  "jdbc:postgresql://" + this.serverAddress + ":" + this.portNumber;
        if (this.dbRestriction.isEmpty()) {
            return wholeAddress;
        } else {
            return wholeAddress + "/" + this.username;
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }
}
