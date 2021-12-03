package lapr.project.model;

public class ShipIMO extends Ship implements Comparable<ShipIMO>  {
    public ShipIMO(Ship ship) {
        super(ship.getMmsi(),
                ship.getDynamicShip(),
                ship.getVesselName(),
                ship.getImo(),
                ship.getCallSign(),
                ship.getVesselType(),
                ship.getLength(),
                ship.getWidth(),
                ship.getDraft(),
                ship.getNr_gen(),
                ship.getCapacity(),
                ship.getGen_power());
    }

    @Override
    public int compareTo(ShipIMO o){
        if (this.getImo() > o.getImo()) {
            return 1;
        } else if (this.getImo() < o.getImo()){
            return -1;
        } else {
            return 0;
        }
    }
}
