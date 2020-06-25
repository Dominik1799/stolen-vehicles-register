package entities;

import javax.persistence.*;

@Entity
@Table(name = "criminalgroup")
public class CriminalGroup {
    @Id
    int id;
    @OneToOne
    @JoinColumn(name = "leaderid")
    private Criminal leader;
    @Column(name = "groupname")
    private String groupName;



    public int getId() {
        return id;
    }

    public Criminal getLeader() {
        return leader;
    }

    public void setLeader(Criminal leader) {
        this.leader = leader;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setId(int id) {
        this.id = id;
    }


}
