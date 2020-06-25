package entities;

public class Vehicle {
    private int id,count, modelYear;
    private String brand, model, vin, ownerName;
    //private Owner owner; TODO add owner as an extra info of vehicle


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Vehicle(int id, int count, int modelYear, String brand, String model, String vin, String ownerName) {
        this.id = id;
        this.count = count;
        this.modelYear = modelYear;
        this.brand = brand;
        this.model = model;
        this.vin = vin;
        this.ownerName = ownerName;
    }

    public Vehicle() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
