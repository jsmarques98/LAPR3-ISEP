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
    }
/*
    @Test
    void testHashCode(){
        assertEquals(-301671663,m.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Aco With Thermal Conductivity 52,000 W/mk, good for Outside layer",m.toString());
    }*/
}