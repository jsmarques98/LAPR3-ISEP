package lapr.project.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipTest {
    private final Ship ship;
    private final Ship ship1;
    ArrayList<DynamicShip> shipArray = new ArrayList<>();
    LocalDateTime t1 = LocalDateTime.of(2020,10,20,12,30,00);
    LocalDateTime t2 = LocalDateTime.of(2020, 03, 9, 14,15, 00);
    DynamicShip ds1 = new DynamicShip(t1, 30.2, 90.0, 1.3, 12.0,0,56,'B');
    DynamicShip ds2 = new DynamicShip(t2, 40.2, 120.0, 3.3, 45.0,0,45,'C');





    public ShipTest() {


        shipArray.add(ds1);
        ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 15,0.8,0.1) {
        };
        ship = new Ship(1111, shipArray, "maria", 1111, "1111", 70, 2.2, 3.0, 2.4, 15,0.8,0.1) {
        };

    }

    @Test
    void getMmsi() {
        int mmsi = 1111;
        int criada = ship.getMmsi();
        assertEquals(mmsi, criada);
    }


    @Test
    void getImo() {
        int imo = 1111;
        int criada = ship.getImo();
        assertEquals(imo, criada);
    }

    @Test
    void getCall_sign() {
        String callSign = "1111";
        String criada = ship.getCallSign();
        assertEquals(callSign, criada);
    }

    @Test
    void getVesselType() {
        int type = 70;
        int teste = ship.getVesselType();
        assertEquals(type, teste);
    }

    @Test
    public void getShipData() {
        ArrayList<DynamicShip> type = new ArrayList<>();
        type.add(ds1);
        assertEquals(type, ship.getDynamicShip());
    }

    @Test
    public void addDynamicShip() {
        ship.addDynamicShip(ds1);
        ship1.addDynamicShip(ds2);
    }

    @Test
    public void testToString() {
        {
            System.out.println("toString()");
            ArrayList<DynamicShip> shipArray = new ArrayList<>();

            Ship ship = new Ship(123456789, shipArray, "Primeiro", 1234567, "callsign", 1, 294.13, 32.31, 11.89, 15,0.8,0.1);
            String expRes = "Ship{" +
                    "mmsi=" + ship.getMmsi() +
                    ", dynamicShip=" + ship.getDynamicShip() +
                    ", vesselName='" + ship.getVesselName() + '\'' +
                    ", imo=" + ship.getImo()  +
                    ", callSign='" + ship.getCallSign() + '\'' +
                    ", vesselType=" + ship.getVesselType() +
                    ", length=" + ship.getLength() +
                    ", width=" + ship.getWidth() +
                    ", draft=" + ship.getDraft() +
                    '}';

            assertEquals(expRes, ship.toString(), "should be equal");
        }
    }
}