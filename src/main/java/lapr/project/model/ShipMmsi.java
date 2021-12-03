package lapr.project.model;

public class ShipMmsi extends Ship implements Comparable<ShipMmsi> {
    public ShipMmsi(Ship ship) {
        super(ship.getMmsi(),
                ship.getDynamicShip(),
                ship.getVesselName(),
                ship.getImo(),
                ship.getCallSign(),
                ship.getVesselType(),
                ship.getLength(),
                ship.getWidth(),
                ship.getDraft(),
                ship.getNrGen(),
                ship.getCapacity(),
                ship.getGenPower());
    }

    @Override
    public int compareTo(ShipMmsi o){
        if (this.getMmsi() > o.getMmsi()) {
            return 1;
        } else if (this.getMmsi() < o.getMmsi()){
            return -1;
        } else {
            return 0;
        }
    }
}
