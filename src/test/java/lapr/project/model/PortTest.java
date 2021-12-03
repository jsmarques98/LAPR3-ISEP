package lapr.project.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortTest {

    Port newPort=new Port("Europe", "United Kingdom", 29002, "Liverpool", 53.46666667, -3.033333333);

    @Test
    public void testGetContinent() {
        assertEquals("Europe", newPort.getContinent());
    }

    @Test
    public void testGetCountry() {
        assertEquals("United Kingdom", newPort.getCountry());
    }

    @Test
    public void testGetPortID() {
        assertEquals(29002, newPort.getCode());
    }

    @Test
    public void testGetPortName() {
        assertEquals("Liverpool", newPort.getPorto());
    }

    @Test
    public void testGetLat() {
        assertEquals(53.46666667, newPort.getLat());
    }

    @Test
    public void testGetLon() {
        assertEquals(-3.033333333, newPort.getLon());
    }

    @Test
    void setCountry() {
        newPort.setCountry("Portugal");
        assertEquals(newPort.getCountry(),"Portugal");
    }

    @Test
    void setCode() {
        newPort.setCode(21345);
        assertEquals(newPort.getCode(),21345);
    }

    @Test
    void setLat() {
        newPort.setLat(52.92);
        assertEquals(newPort.getLat(),52.92);
    }

    @Test
    void setLon() {
        newPort.setLon(120.9);
        assertEquals(newPort.getLon(),120.9);
    }
}
