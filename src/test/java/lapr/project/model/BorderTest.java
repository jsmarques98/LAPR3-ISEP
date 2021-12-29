package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorderTest {

    Border newBorder = new Border("Portugal","Espanha");
    Border newBorder1 = new Border("Portugal","Espanha");

    @Test
    void getCountry1() {
        assertEquals(newBorder.getCountry1(),"Portugal");
    }

    @Test
    void setCountry1() {
        newBorder.setCountry1("Franca");
        assertEquals(newBorder.getCountry1(),"Franca");
    }

    @Test
    void getCountry2() {
        assertEquals(newBorder.getCountry2(),"Espanha");
    }

    @Test
    void setCountry2() {
        newBorder.setCountry2("Suecia");
        assertEquals(newBorder.getCountry2(),"Suecia");
    }

    @Test
    void testEquals() {
        assertTrue(newBorder.equals(newBorder1));
        assertTrue(newBorder.equals(newBorder));
        assertFalse(newBorder.equals(null));
        Integer a = 0;
        assertFalse(newBorder.equals(a));
        newBorder1 = new Border("Franca","Espanha");
        assertFalse(newBorder.equals(newBorder1));
        newBorder1 = new Border("Portugal","Franca");
        assertFalse(newBorder.equals(newBorder1));
    }

    @Test
    void testHashCode() {
        assertEquals(-943583381,newBorder.hashCode());
    }
}