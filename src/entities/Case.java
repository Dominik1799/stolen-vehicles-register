package entities;

import javax.persistence.*;

@Entity
@Table(name= "cases")
public class Case {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "criminalgroup")
    private int criminalGroup;
    private String description;
    private int status;
    private int severity;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getCriminalGroup() {
        return criminalGroup;
    }

    public void setCriminalGroup(int criminalGroup) {
        this.criminalGroup = criminalGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
