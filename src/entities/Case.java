package entities;

import javax.persistence.*;

@Entity
@Table(name= "cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "criminalgroup")
    private int criminalGroup;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private int status;
    @Column(name = "severity")
    private int severity;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int  getStatus() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
