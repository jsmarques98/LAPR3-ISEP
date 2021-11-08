package lapr.project.model;

public class Truck {
    private String plate;
    private double fuel_capacity;

    public Truck(){
    }

    public Truck(String plate, double fuel){
        setPlate(plate);
        setFuel(fuel);
    }

    public boolean setPlate( String plate){
        this.plate=plate;
        return true;
    }
    public boolean setFuel(double fuel){
        this.fuel_capacity=fuel;
        return true;
    }

    public String getPlate(){
        return this.plate;
    }

    public double getFuel(){
        return this.fuel_capacity;
    }
}
