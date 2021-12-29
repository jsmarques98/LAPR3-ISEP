package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {

    private Material m;
    private Material m2;


    @BeforeEach
    public void setUp() {

        m = new Material("Aco", 52.00, "Outside");
        m2 = new Material("Aco", 52.00, "Outside");
    }

    @Test
    void testGetName() {
        assertEquals("Aco",m.getName());
    }

    @Test
    void testGetThermalConductivity() {
        assertEquals(52.00,m.getThermalConductivity());
    }

    @Test
    void testGetWall() {
        assertEquals("Outside",m.getWall());
    }

    @Test
    void testEquals(){
        assertTrue(m.equals(m2));
        assertTrue(m.equals(m));
        assertFalse(m.equals(null));
        Integer a = 0;
        assertFalse(m.equals(a));
        Material m3 = new Material("Madeira", 52.00, "Outside");
        assertFalse(m.equals(m3));
        m3 = new Material("Aco", 51.00, "Outside");
        assertFalse(m.equals(m3));
        m3 = new Material("Aco", 52.00, "Middle");
        assertFalse(m.equals(m3));
    }

    @Test
    void testHashCode(){
        assertEquals(-301671663,m.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(m.toString());
    }
}