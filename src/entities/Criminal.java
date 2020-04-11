package entities;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Criminal {
    private String id, name, surname, sex, nationality, description;
    private int caseid,group,age;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Criminal(String id, String name, String surname, String sex, String nationality, String description, int caseid, int group, LocalDate birthdate){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.nationality = nationality;
        this.description = description;
        this.caseid = caseid;
        this.group = group;
        LocalDate today = LocalDate.now();
        Period p = Period.between(birthdate,today);
        this.age = p.getYears();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
