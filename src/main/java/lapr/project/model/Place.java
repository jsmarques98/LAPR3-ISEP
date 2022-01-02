package lapr.project.model;

import java.util.Objects;

public class Place extends Position{
    private String name;
    private String countryName;

    public Place(String name, String countryName) {
        super(name,countryName);
        this.name = name;
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return name.equals(place.name) && countryName.equals(place.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, countryName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCountryName() {
        return name;
    }

    public void setCountryName(String countryName){
        this.countryName = countryName;
    }

}
