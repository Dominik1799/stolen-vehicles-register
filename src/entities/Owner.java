package entities;

public class Owner {
    private String firstName, lastName;

    public String getFirstName() {
        return firstName;
    }

    public Owner(String firstName, String lastName, int count) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.count = count;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;


}
