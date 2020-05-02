package entities;

import javax.persistence.*;

@Entity
@Table(name= "cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "criminalgroup")
    private String criminalGroup;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;
    @Column(name = "severity")
    private String severity;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String  getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getCriminalGroup() {
        return criminalGroup;
    }

    public void setCriminalGroup(String criminalGroup) {
        this.criminalGroup = criminalGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
