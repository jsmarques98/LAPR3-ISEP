package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    Country newCountry = new Country("Europe","CY","CYP","Cyprus",0.85,"Nicosia",35.16666667,33.366667);

    @Test
    void getContinent() {
        assertEquals(newCountry.getContinent(),"Europe");
    }

    @Test
    void setContinent() {
        newCountry.setContinent("America");
        assertEquals(newCountry.getContinent(),"America");
    }

    @Test
    void getAlpha2Code() {
        assertEquals(newCountry.getAlpha2Code(),"CY");
    }

    @Test
    void setAlpha2Code() {
        newCountry.setAlpha2Code("YC");
        assertEquals(newCountry.getAlpha2Code(),"YC");
    }

    @Test
    void getAlpha3Code() {
        assertEquals(newCountry.getAlpha3Code(),"CYP");
    }

    @Test
    void setAlpha3Code() {
        newCountry.setAlpha3Code("PYC");
        assertEquals(newCountry.getAlpha3Code(),"PYC");
    }

    @Test
    void getCountry() {
        assertEquals(newCountry.getCountry(),"Cyprus");
    }

    @Test
    void setCountry() {
        newCountry.setCountry("Poland");
        assertEquals(newCountry.getCountry(),"Poland");
    }

    @Test
    void getPopulation() {
        assertEquals(newCountry.getPopulation(),0.85);
    }

    @Test
    void setPopulation() {
        newCountry.setPopulation(1.85);
        assertEquals(newCountry.getPopulation(),1.85);
    }

    @Test
    void getCapital() {
        assertEquals(newCountry.getCapital(),"Nicosia");
    }

    @Test
    void setCapital() {
        newCountry.setCapital("Algarve");
        assertEquals(newCountry.getCapital(), "Algarve");
    }

    @Test
    void getLatitude() {
        assertEquals(newCountry.getLatitude(),35.16666667);
    }

    @Test
    void setLatitude() {
        newCountry.setLatitude(1);
        assertEquals(newCountry.getLatitude(), 1);
    }

    @Test
    void getLongitude() {
        assertEquals(newCountry.getLongitude(),33.366667);
    }

    @Test
    void setLongitude() {
        newCountry.setLongitude(2);
        assertEquals(newCountry.getLongitude(),2);
    }
}