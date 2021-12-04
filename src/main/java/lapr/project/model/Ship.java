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
    private final int nrGen;
    private final double capacity;
    private final double genPower;
    
    
    public Ship(int mmsi, ArrayList<DynamicShip> shipArray, String vesselName, int imo, String callSign, int vesselType, double length, double width, double draft, int nrGen, double capacity, double genPower) {
        this.mmsi = mmsi;
        this.shipData = shipArray;
        this.vesselName = vesselName;
        this.imo = imo;
        this.callSign = callSign;
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;
        this.nrGen = nrGen;
        this.capacity = capacity;
        this.genPower = genPower;
    }


    public int getMmsi() {
        return mmsi;
    }

    public ArrayList<DynamicShip> getDynamicShip(){
        return shipData;
    }

    public void setShipData(ArrayList<DynamicShip> shipData) {
        this.shipData = shipData;
    }

    public int getImo() {
        return imo;
    }

    public String getCallSign() {
        return callSign;
    }

    public String getVesselName() {
        return vesselName;
    }

    public int getVesselType() {
        return vesselType;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getDraft() {
        return draft;
    }

    public void addDynamicShip(DynamicShip ship){
        this.shipData.add(ship);
    }

    public void initializeDynamicShip(){
        this.shipData = new ArrayList<>();
    }



    @Override
    public String toString() {
        return "Ship{" +
                "mmsi=" + mmsi +
                ", dynamicShip=" + getDynamicShip() +
                ", vesselName='" + vesselName + '\'' +
                ", imo=" + imo +
                ", callSign='" + callSign + '\'' +
                ", vesselType=" + vesselType +
                ", length=" + length +
                ", width=" + width +
                ", draft=" + draft +

                '}';
    }

    public String toString1() {
        return "Ship{" +
                "mmsi=" + mmsi +
                ", vesselName='" + vesselName + '\'' +
                ", imo=" + imo +
                ", callSign='" + callSign + '\'' +
                ", vesselType=" + vesselType +
                ", length=" + length +
                ", width=" + width +
                ", draft=" + draft +

                '}';
    }

    public DynamicShip getDataByDate(LocalDateTime date) {
        LocalDateTime t1 = LocalDateTime.of(0,1,1,0,0,0);
        DynamicShip foundData = new DynamicShip(t1,0.0,0.0,0.0,0.0,0.0,0,'0');
        for (DynamicShip data : this.shipData) {
            LocalDateTime currentDate = data.getBaseDateTime();
            if (currentDate.isEqual(date)) {
                foundData = data;
            }
        }
        return foundData;
    }

    public int getNrGen() {
        return nrGen;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getGenPower() {
        return genPower;
    }
}
