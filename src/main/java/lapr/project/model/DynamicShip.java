package lapr.project.model;

import java.time.LocalDateTime;

public class DynamicShip {

    private LocalDateTime baseDateTime;
    private double lat;
    private double lon;
    private double sog;
    private double cog;
    private double heading;
    private char transcrieverClass;

    public DynamicShip(LocalDateTime baseDateTime, double lat, double lon, double sog, double cog, double heading, char transcrieverClass) {
        this.baseDateTime = baseDateTime;
        this.lat = lat;
        this.lon = lon;
        this.sog = sog;
        this.cog = cog;
        this.heading = heading;
        this.transcrieverClass = transcrieverClass;
    }


    public LocalDateTime getBaseDateTime() {
        return baseDateTime;
    }

    public void setBaseDateTime(LocalDateTime baseDateTime) {
        this.baseDateTime = baseDateTime;
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

    public double getSog() {
        return sog;
    }

    public void setSog(double sog) {
        this.sog = sog;
    }

    public double getCog() {
        return cog;
    }

    public void setCog(double cog) {
        this.cog = cog;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public char getTranscrieverClass() {
        return transcrieverClass;
    }

    public void setTranscrieverClass(char transcrieverClass) {
        this.transcrieverClass = transcrieverClass;
    }
}
