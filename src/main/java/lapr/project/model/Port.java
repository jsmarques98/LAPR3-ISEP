package lapr.project.model;

public class Port {

    private String continent;
    private String country;
    private int code;
    private String port;
    private double lat;
    private double lon;

    public Port(String continent, String country, int code, String port, double lat, double lon) {
        this.continent = continent;
        this.country = country;
        this.code = code;
        this.port = port;
        this.lat = lat;
        this.lon = lon;
    }

    public String getContinent() {
        return continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPort() {
        return port;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Port{" +
                "continent='" + continent + '\'' +
                ", country='" + country + '\'' +
                ", code=" + code +
                ", port='" + port + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

}
