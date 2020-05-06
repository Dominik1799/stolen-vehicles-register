package entities;

import javax.persistence.*;

@Entity
@Table(name= "cases")
public class Case {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "criminalgroup")
    private CriminalGroup criminalGroup;

    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private int status;

    @Column(name = "severity")
    private int severity;


    public Case() {
    }
    public Case(String name) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CriminalGroup getCriminalGroup() {
        return criminalGroup;
    }

    public void setCriminalGroup(CriminalGroup criminalGroup) {
        this.criminalGroup = criminalGroup;
    }

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


    public String getLeaderName() {
        return this.getCriminalGroup().getLeader().getName() + " " + this.criminalGroup.getLeader().getSurname();

    }
    public String getCriminalGroupName() {
        return this.criminalGroup.getGroupName();
    }


}
