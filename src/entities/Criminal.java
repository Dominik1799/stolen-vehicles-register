package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "criminal")
public class Criminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "firstname")
    private String name;
    @Column(name = "lastname")
    private String surname;

    @Column(name = "criminalgroup")
    private int groupID;
    private String sex;
    private String nationality;
    private String description;
    private String groupAmount;
    private String birthday;
    private int caseid;
    private String group;
    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupAmount() {
        return groupAmount;
    }

    public void setGroupAmount(String groupAmount) {
        this.groupAmount = groupAmount;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public Criminal() {
    }

    public Criminal(String id, String name, String surname, String sex, String nationality, String description, int caseid, String group, LocalDate birthdate, String groupAmount, int groupID){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.nationality = nationality;
        this.description = description;
        this.caseid = caseid;
        this.group = group;
        this.birthday = birthdate.toString();
        LocalDate today = LocalDate.now();
        Period p = Period.between(birthdate,today);
        this.age = p.getYears();
        this.groupID = groupID;
        if(this.groupID == 0) {
            this.groupAmount = "1";
        }
        else{
            this.groupAmount = groupAmount;
        }
    }

    public String getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCaseid() {
        return caseid;
    }

    public void setCaseid(int caseid) {
        this.caseid = caseid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
