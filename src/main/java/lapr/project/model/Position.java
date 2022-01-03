package lapr.project.model;

import java.util.Objects;

public class Position {

    private Double latitude = null;
    private Double longitude = null;
    private final String name;
    private final String countryName;

    public Position(String name, String countryName){
        this.name=name;
        this.countryName=countryName;
    }

    public Position(String name, String countryName, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name=name;
        this.countryName=countryName;
    }

<<<<<<< HEAD
=======
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

>>>>>>> f5583e88f043955de5753e989692c799a76f3eb7
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    public String getCountryName() {
        return countryName;
    }

    @Override
    public String toString() {
        return String.format("%s, %s",countryName,name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(latitude, position.latitude) && Objects.equals(longitude, position.longitude) && Objects.equals(name, position.name) && Objects.equals(countryName, position.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, name, countryName);
    }

}
