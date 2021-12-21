package lapr.project.model;

public class SeaDist {

    private String fromCountry;
    private int fromPortId;
    private String fromPort;
    private String toCountry;
    private int toPortId;
    private String toPort;
    private double sea_distance;

    public SeaDist(String fromCountry, int fromPortId, String fromPort,
                   String toCountry, int toPortId, String toPort, double sea_distance) {
        this.fromCountry = fromCountry;
        this.fromPortId = fromPortId;
        this.fromPort = fromPort;
        this.toCountry = toCountry;
        this.toPortId = toPortId;
        this.toPort = toPort;
        this.sea_distance = sea_distance;
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

    public double getSea_distance() {
        return sea_distance;
    }

    public void setSea_distance(double sea_distance) {
        this.sea_distance = sea_distance;
    }
}
