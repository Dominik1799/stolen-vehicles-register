package entities;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name= "team")
@FilterDef(name = "validMember")
@FilterDef(name = "activeCase")
public class Team {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "memberamount")
    private int memberamount;
    @OneToOne
    @JoinColumn(name = "leaderid")
    private User leader;
    @ManyToMany
    @JoinTable(
            name = "team_changes",
            joinColumns = {@JoinColumn(name = "teamid")},
            inverseJoinColumns = {@JoinColumn(name = "userid")}
    )
    @FilterJoinTable(
            name = "validMember",
            condition = "status=1"
    )
    private Collection<User> members;
    @ManyToMany
    @JoinTable(
            name = "case_history",
            joinColumns = {@JoinColumn(name = "teamid")},
            inverseJoinColumns = {@JoinColumn(name = "caseid")}
    )
    @FilterJoinTable(
            name = "activeCase",
            condition = "active=true"
    )
    private Collection<Case> activeCases;


    public Collection<Case> getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(Collection<Case> activeCases) {
        this.activeCases = activeCases;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public Collection<User> getMembers() {
        return members;
    }

    public void setMembers(Collection<User> members) {
        this.members = members;
    }

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


}
