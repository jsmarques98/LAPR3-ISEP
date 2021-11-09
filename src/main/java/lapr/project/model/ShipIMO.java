package lapr.project.model;

public class ShipIMO extends Ship implements Comparable<ShipIMO>  {
    public ShipIMO(int mmsi, String vesselName, int imo, String callSign, int vesselType, double length, double width, double draft, double cargo) {
        super(mmsi, vesselName, imo, callSign, vesselType, length, width, draft, cargo);
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
