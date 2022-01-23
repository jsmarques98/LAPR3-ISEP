package lapr.project.model;

import java.time.LocalDateTime;

public class DynamicShip {

    private final LocalDateTime baseDateTime;
    private final double lat;
    private final double lon;
    private final double sog;
    private final double cog;
    private final double heading;
    private final char transcrieverClass;
    private double cargo;

    public DynamicShip(LocalDateTime baseDateTime, double lat, double lon, double sog, double cog, double heading,double cargo, char transcrieverClass) {
        this.baseDateTime = baseDateTime;
        this.lat = lat;
        this.lon = lon;
        this.sog = sog;
        this.cog = cog;
        this.heading = heading;
        this.transcrieverClass = transcrieverClass;
        this.cargo = cargo;
    }

    public LocalDateTime getBaseDateTime() {
        return baseDateTime;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getSog() {
        return sog;
    }

    public double getCog() {
        return cog;
    }

    public double getHeading() {
        return heading;
    }

    public char getTranscrieverClass() {
        return transcrieverClass;
    }

    @Override
    public String toString() {
        return "DynamicShip{" +
                "baseDateTime=" + baseDateTime +
                ", lat=" + lat +
                ", lon=" + lon +
                ", sog=" + sog +
                ", cog=" + cog +
                ", heading=" + heading +
                ", transcrieverClass=" + transcrieverClass +
                '}';
    }

    public double getCargo() {
        return cargo;
    }

    public void setCargo(double cargo) {
        this.cargo = cargo;
    }
}
