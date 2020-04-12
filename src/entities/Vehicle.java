package entities;

public class Vehicle {
    private int id,count, modelYear;
    private String brand, model, vin;
    //private Owner owner; TODO add owner as an extra info of vehicle


    public Vehicle(int id, int count, int modelYear, String brand, String model, String vin) {
        this.id = id;
        this.count = count;
        this.modelYear = modelYear;
        this.brand = brand;
        this.model = model;
        this.vin = vin;
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
