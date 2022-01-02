package lapr.project.model;


public class SeaDist implements Comparable<SeaDist> {

    private String fromCountry;
    private int fromPortId;
    private String fromPort;
    private String toCountry;
    private int toPortId;
    private String toPort;
    private Place fromPlace;
    private Place toPlace;
    private double seaDistance;

    public SeaDist(String fromCountry, int fromPortId, String fromPort,
                   String toCountry, int toPortId, String toPort, double seaDistance) {
        this.fromCountry = fromCountry;
        this.fromPortId = fromPortId;
        this.fromPort = fromPort;
        this.toCountry = toCountry;
        this.toPortId = toPortId;
        this.toPort = toPort;
        this.seaDistance = seaDistance;
        this.fromPlace = new Place(fromPort,fromCountry);
        this.toPlace = new Place(toPort,toCountry);
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public int getFromPortId() {
        return fromPortId;
    }

    public void setFromPortId(int fromPortId) {
        this.fromPortId = fromPortId;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public int getToPortId() {
        return toPortId;
    }

    public void setToPortId(int toPortId) {
        this.toPortId = toPortId;
    }

    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public double getSeaDistance() {
        return seaDistance;
    }

    public void setSeaDistance(double seaDistance) {
        this.seaDistance = seaDistance;
    }

    public Place getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(Place fromPlace) {
        this.fromPlace = fromPlace;
    }

    public Place getToPlace() {
        return toPlace;
    }

    public void setToPlace(Place toPlace) {
        this.toPlace = toPlace;
    }

    @Override
    public int compareTo(SeaDist sd){
        if (this.getSeaDistance() > sd.getSeaDistance()) {
            return 1;
        } else if (this.getSeaDistance() < sd.getSeaDistance()){
            return -1;
        } else {
            return 0;
        }
    }
}
