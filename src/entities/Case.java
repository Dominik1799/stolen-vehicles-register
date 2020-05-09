package entities;

import javax.persistence.*;


@Entity(name = "case_status")
class Status {
    @Id
    private int id;
    @Column(name = "status")
    private String name;

    public Status() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

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

    @OneToOne
    @JoinColumn(name = "status")
    private Status status = new Status();

    @Column(name = "severity")
    private int severity;


    public Case() {
    }
    public Case(String name) {

    }

    public String getStatusName() {
        return this.getStatus().getName();
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCriminalGroup(CriminalGroup criminalGroup) {
        this.criminalGroup = criminalGroup;
    }

    public void setStatusId(int id) {
        this.status.setId(id);
    }

    public int getStatusId() {
        return this.status.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }


    public String getLeaderName() {
        if(this.getCriminalGroup().getId() != 0)
            return this.getCriminalGroup().getLeader().getName() + " " + this.criminalGroup.getLeader().getSurname();
        else return null;
    }
    public String getCriminalGroupName() {
        return this.criminalGroup.getGroupName();
    }


}
