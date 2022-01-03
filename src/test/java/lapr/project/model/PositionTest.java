package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    private Position p= new Position("Lisboa","Portugal",12.1,15.2);;
    private Position p1= new Position("Lisboa","Portugal",12.1,15.2);;


    public void setUp() {
        p = new Position("Lisboa","Portugal",12.1,15.2);
        p1 = new Position("Lisboa","Portugal",12.1,15.2);
    }

    @Test
    void getLongitude() {
        assertEquals(15.2,p.getLongitude());
    }

    @Test
    void getLatitude() {
        assertEquals(12.1,p.getLatitude());
    }

    @Test
    void getName() {
        assertEquals("Lisboa",p.getName());
    }

    @Test
    void getCountryName() {
        assertEquals("Portugal",p.getCountryName());
    }

    @Test
    void testToString() {
        assertEquals("Portugal, Lisboa",p.toString());
    }

    @Test
    void testEquals() {
        assertTrue(p.equals(p1));
        assertTrue(p.equals(p));
        assertFalse(p.equals(null));
        Integer a = 0;
        assertFalse(p.equals(a));
        p1 = new Position("Lisboa","Espnha",12.1,15.2);
        assertFalse(p.equals(p1));
        p1 = new Position("Madrid","Portugal",12.1,15.2);
        assertFalse(p.equals(p1));
        p1 = new Position("Lisboa","Portugal",12.2,15.2);
        assertFalse(p.equals(p1));
        p1 = new Position("Lisboa","Portugal",12.1,15.5);
        assertFalse(p.equals(p1));
    }

    @Test
    void testHashCode() {
        assertEquals(1152901665,p.hashCode());
    }

    @Test
    void setLatitude() {
        p.setLatitude(1.0);
        assertEquals(1.0,p.getLatitude());
    }

    @Test
    void setLongitude() {
        p.setLongitude(3.0);
        assertEquals(3.0,p.getLongitude());
    }
}