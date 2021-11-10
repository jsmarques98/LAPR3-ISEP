package lapr.project.model;

public class ShipCallSign extends Ship implements Comparable<ShipCallSign>{

    public ShipCallSign(Ship ship) {
        super(ship.getMmsi(),
                ship.getDynamicShip(),
                ship.getVesselName(),
                ship.getImo(),
                ship.getCallSign(),
                ship.getVesselType(),
                ship.getLength(),
                ship.getWidth(),
                ship.getDraft(),
                ship.getCargo());
    }


    @Override
    public int compareTo(ShipCallSign o){
        return this.getCallSign().compareTo(o.getCallSign());
        }
    }
