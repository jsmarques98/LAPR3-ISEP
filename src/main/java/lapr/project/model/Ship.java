package lapr.project.model;

import java.time.LocalDateTime;


public class Ship {

    private static int mmsi;
    private LocalDateTime baseDateTime;
    private double lat;
    private double lon;
    private double sog;
    private double cog;
    private double heading;
    private static String vesselName;
    private static int imo;
    private static String callSign;
    private static int vesselType;
    private static double length;
    private static double width;
    private static double draft;
    private double cargo;
    private char transcrieverClass;


    public Ship() {
    }

    public Ship(int mmsi,LocalDateTime baseDateTime, double lat, double lon, double sog, double cog, double heading, String vesselName,
                int imo, String callSign, int vesselType, double length, double width, double draft,double cargo, char transcrieverClass) {
        this.mmsi = mmsi;
        this.baseDateTime = baseDateTime;
        this.lat = lat;
        this.lon = lon;
        this.sog = sog;
        this.cog = cog;
        this.heading = heading;
        this.vesselName = vesselName;
        this.imo = imo;
        this.callSign = callSign;
        this.vesselType = vesselType;
        this.length = length;
        this.width = width;
        this.draft = draft;
        this.cargo = cargo;
        this.transcrieverClass = transcrieverClass;
    }

    public static int getMmsi() {
        return mmsi;
    }

    public static void setMmsi(int mmsi) {
        Ship.mmsi = mmsi;
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

    public static String getVesselName() {
        return vesselName;
    }

    public static void setVesselName(String vesselName) {
        Ship.vesselName = vesselName;
    }

    public static int getImo() {
        return imo;
    }

    public static void setImo(int imo) {
        Ship.imo = imo;
    }

    public static String getCallSign() {
        return callSign;
    }

    public static void setCallSign(String callSign) {
        Ship.callSign = callSign;
    }

    public static int getVesselType() {
        return vesselType;
    }

    public static void setVesselType(int vesselType) {
        Ship.vesselType = vesselType;
    }

    public static double getLength() {
        return length;
    }

    public static void setLength(int length) {
        Ship.length = length;
    }

    public static double getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Ship.width = width;
    }

    public static double getDraft() {
        return draft;
    }

    public static void setDraft(int draft) {
        Ship.draft = draft;
    }

    public double getCargo() {
        return cargo;
    }

    public void setCargo(double cargo) {
        this.cargo = cargo;
    }

    public char getTranscrieverClass() {
        return transcrieverClass;
    }

    public void setTranscrieverClass(char transcrieverClass) {
        this.transcrieverClass = transcrieverClass;
    }
}
