package lapr.project.model;

public class ShipMmsi extends Ship implements Comparable<ShipMmsi> {
    public ShipMmsi(int mmsi, String vesselName, int imo, String callSign, int vesselType, double length, double width, double draft, double cargo) {
        super(mmsi, vesselName, imo, callSign, vesselType, length, width, draft, cargo);
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
