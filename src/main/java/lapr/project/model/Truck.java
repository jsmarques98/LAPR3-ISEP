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
        if(plate.matches("^(([A-Z]{2}:\\d{2}:(\\d{2}|[A-Z]{2}))|(\\d{2}:(\\d{2}:[A-Z]{2}|[A-Z]{2}:\\d{2})))$$")){
            this.plate=plate;
            return true;
        }

        return false;
    }
    public boolean setFuel(double fuel){
        if (fuel>0) {
            this.fuel_capacity = fuel;
            return true;
        }
        return false;
    }

    public String getPlate(){
        return this.plate;
    }

    public double getFuel(){
        return this.fuel_capacity;
    }
}
