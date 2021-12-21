package lapr.project.model;

public class Country {

    private String continent;
    private String alpha2Code;
    private String alpha3Code;
    private String country;
    private int population;
    private String capital;
    private String latitude;
    private String longitude;

    public Country(String continent, String alpha2Code, String alpha3Code, String country,
                   int population, String capital, String latitude, String longitude) {
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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
