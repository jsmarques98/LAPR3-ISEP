package lapr.project.model;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Ship {

    private final int mmsi;
    private ArrayList<DynamicShip> shipData;
    private final String vesselName;
    private final int imo;
    private final String callSign;
    private final int vesselType;
    private final double length;
    private final double width;
    private final double draft;
    private final double cargo;


    public Ship(int mmsi, String vesselName, int imo, String callSign, int vesselType, double length, double width, double draft, double cargo) {
        this.mmsi = mmsi;
        this.shipData = new ArrayList<>();
        this.vesselName = vesselName;
        this.imo = imo;
        this.callSign = callSign;
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;
        this.cargo = cargo;
    }
    public int getMmsi() {
        return mmsi;
    }

    public int getImo() {
        return imo;
    }

    public String getCallSign() {
        return callSign;
    }

    public ArrayList<DynamicShip> getShipData(){
        return shipData;
    }

    public void addDynamicShip(DynamicShip ship){
        this.shipData.add(ship);
    }



    @Override
    public String toString() {
        return "Ship{" +
                "mmsi=" + mmsi +
                ", vesselName='" + vesselName + '\'' +
                ", imo=" + imo +
                ", callSign='" + callSign + '\'' +
                ", vesselType=" + vesselType +
                ", length=" + length +
                ", width=" + width +
                ", draft=" + draft +
                ", cargo=" + cargo +
                '}';
    }
}
