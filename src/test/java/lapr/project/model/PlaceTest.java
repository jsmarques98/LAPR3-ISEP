package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceTest {
    private Place p;
    private Place p1;

    @BeforeEach
    public void setUp() {
        p = new Place("Lisboa","Portugal");
        p1 = new Place("Lisboa","Portugal");
    }

    @Test
    void testEquals() {
        assertTrue(p.equals(p1));
        assertTrue(p.equals(p));
        assertFalse(p.equals(null));
        Integer a = 0;
        assertFalse(p.equals(a));
        p1 = new Place("Lisboa","Espnha");
        assertFalse(p.equals(p1));
        p1 = new Place("Madrid","Portugal");
        assertFalse(p.equals(p1));
    }

    @Test
    void testHashCode() {
        assertEquals(-1654862239,p.hashCode());
    }

    @Test
    void getName() {
        assertEquals("Lisboa",p.getName());
    }

    @Test
    void setName() {
        p.setName("Madrid");
        assertEquals("Madrid",p.getName());
    }

    @Test
    void getCountryName() {
        assertEquals("Portugal",p.getCountryName());
    }

    @Test
    void setCountryName() {
        p.setCountryName("Espanha");
        assertEquals("Espanha", p.getCountryName());
    }
}