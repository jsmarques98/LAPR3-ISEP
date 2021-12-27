package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaDistTest {

    SeaDist seaDist = new SeaDist("Denmark",10358,"Aarhus","Turkey",246265,"Ambarli",3673);

    @Test
    void getFromCountry() {
        assertEquals(seaDist.getFromCountry(),"Denmark");
    }

    @Test
    void setFromCountry() {
        seaDist.setFromCountry("Brasil");
        assertEquals(seaDist.getFromCountry(),"Brasil");
    }

    @Test
    void getFromPortId() {
        assertEquals(seaDist.getFromPortId(),10358);
    }

    @Test
    void setFromPortId() {
        seaDist.setFromPortId(12345);
        assertEquals(seaDist.getFromPortId(),12345);
    }

    @Test
    void getFromPort() {
        assertEquals(seaDist.getFromPort(),"Aarhus");
    }

    @Test
    void setFromPort() {
        seaDist.setFromPort("Rio de Janeiro");
        assertEquals(seaDist.getFromPort(),"Rio de Janeiro");
    }

    @Test
    void getToCountry() {
        assertEquals(seaDist.getToCountry(),"Turkey");
    }

    @Test
    void setToCountry() {
        seaDist.setToCountry("Portugal");
        assertEquals(seaDist.getToCountry(),"Portugal");
    }

    @Test
    void getToPortId() {
        assertEquals(seaDist.getToPortId(),246265);
    }

    @Test
    void setToPortId() {
        seaDist.setToPortId(54321);
        assertEquals(seaDist.getToPortId(),54321);
    }

    @Test
    void getToPort() {
        assertEquals(seaDist.getToPort(),"Ambarli");
    }

    @Test
    void setToPort() {
        seaDist.setToPort("Lisboa");
        assertEquals(seaDist.getToPort(),"Lisboa");
    }

    @Test
    void getSeaDistance() {
        assertEquals(seaDist.getSeaDistance(),3673);
    }

    @Test
    void setSeaDistance() {
        seaDist.setSeaDistance(7531);
        assertEquals(seaDist.getSeaDistance(),7531);
    }
}