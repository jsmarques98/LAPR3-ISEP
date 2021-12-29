package lapr.project.model;

import java.util.Objects;

public class Country {

    private String continent;
    private String alpha2Code;
    private String alpha3Code;
    private String country;
    private double population;
    private String capital;
    private double latitude;
    private double longitude;

    public Country(String continent, String alpha2Code, String alpha3Code, String country,
                   double population, String capital, double latitude, double longitude) {
        this.continent = continent;
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
        this.country = country;
        this.population = population;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country1 = (Country) o;
        return Double.compare(country1.population, population) == 0 && Double.compare(country1.latitude, latitude) == 0 && Double.compare(country1.longitude, longitude) == 0 && continent.equals(country1.continent) && alpha2Code.equals(country1.alpha2Code) && alpha3Code.equals(country1.alpha3Code) && country.equals(country1.country) && capital.equals(country1.capital);
    }

    @Override
    public int hashCode() {
        return Objects.hash(continent, alpha2Code, alpha3Code, country, population, capital, latitude, longitude);
    }
}
