package entities;

public class Owner {
    private String firstname, lastname;
    private int count, id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Owner(String firstname, String lastname, int count) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.count = count;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Owner() {

    }


}
