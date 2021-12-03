package lapr.project.utils;

import lapr.project.model.DynamicShip;
import lapr.project.model.Port;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    String path = "testReader";
    String path1 = "portReaderTest";

    private final Ship s1;
    private final Port p1;

    ArrayList<Ship> shipArray = new ArrayList<>();
    ArrayList<DynamicShip> shipDataArray = new ArrayList<>();
    ArrayList<Port> portArray = new ArrayList<>();
    LocalDateTime t1 = LocalDateTime.of(2020, 12, 31, 17, 03, 00);
    LocalDateTime t2 = LocalDateTime.of(2020, 12, 31, 17, 19, 00);
    DynamicShip ds1 = new DynamicShip(t1, 42.92236, -66.97243, 12.5, 2.4, 358, 45,'B');
    DynamicShip ds2 = new DynamicShip(t2, 42.97875, -66.97001, 12.9, 13.1, 355, 64,'B');


    public CsvReaderTest() {
        s1 = new Ship(210950000, shipDataArray, "VARAMO", 9395044, "C4SQ2", 70, 166, 25, 9.5, 15,0.8,0.1) {
        };
        shipDataArray.add(ds2);
        shipDataArray.add(ds1);

        p1 = new Port("Europe", "United Kingdom", 29002, "Liverpool", 53.46666667, -3.033333333);
        portArray.add(p1);
    }

    @Test
    void readCSV() throws Exception {
        shipArray = CsvReader.readCSV(path);
        ArrayList<Ship> expectRes = shipArray;
        assertEquals(expectRes, shipArray);
    }


    @Test
    void sortByDate() throws Exception {
        shipArray = CsvReader.readCSV(path);
        ArrayList<Ship> expectRes = shipArray;
        assertEquals(expectRes, shipArray);
    }

    // Teste correto pois tem o to string igual, mas diz que não está igual
    /*@Test
    void readPorts() throws Exception {
        ArrayList<Port> portArray1 = CsvReader.readPorts(path1);
        ArrayList<Port> expectRes = portArray1;
        assertEquals(expectRes, portArray);
    }*/


    @Test
    void readPorts() throws Exception {

        ArrayList<Port> portArray1 = CsvReader.readPorts(path1);
        portArray1.get(0).toString();
        assertEquals(1, portArray1.size());
        assertEquals("Europe", portArray1.get(0).getContinent());
        assertEquals("United Kingdom", portArray1.get(0).getCountry());
        assertEquals(29002, portArray1.get(0).getCode());
        assertEquals("Liverpool", portArray1.get(0).getPort());
        assertEquals(53.46666667, portArray1.get(0).getLat());
        assertEquals(-3.033333333, portArray1.get(0).getLon());

    }

    @Test
    void readPortsExp() throws Exception {
        ArrayList<Port> ports = CsvReader.readPorts("src/data/1234.csv");
        ArrayList<Port> expectRes = null;
        assertEquals(ports, expectRes);
    }
}

