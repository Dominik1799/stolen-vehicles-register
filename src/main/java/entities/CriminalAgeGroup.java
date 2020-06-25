package entities;

public class CriminalAgeGroup {
    private  Integer id;
    private String groupName;
    private Integer criminalAmount;
    private Integer averageAge;

    public String getGroupName() {
        return groupName;
    }

    public CriminalAgeGroup(String groupName, Integer criminalAmount, Integer averageAge) {
        this.groupName = groupName;
        this.criminalAmount = criminalAmount;
        this.averageAge = averageAge;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCriminalAmount() {
        return criminalAmount;
    }

    public void setCriminalAmount(Integer criminalAmount) {
        this.criminalAmount = criminalAmount;
    }

    public Integer getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(Integer averageAge) {
        this.averageAge = averageAge;
    }
}
