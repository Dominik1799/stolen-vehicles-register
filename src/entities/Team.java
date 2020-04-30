package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "team")
public class Team {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "memberamount")
    private int memberamount;
    @Column(name = "leaderid")
    private int leader;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberamount() {
        return memberamount;
    }

    public void setMemberamount(int memberamount) {
        this.memberamount = memberamount;
    }

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }
}
