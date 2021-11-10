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

}
