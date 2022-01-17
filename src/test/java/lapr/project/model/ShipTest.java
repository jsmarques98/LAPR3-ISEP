package lapr.project.model;

import lapr.project.data.Ship;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipTest {
    private final Ship ship;
    private final Ship ship1;
    ArrayList<DynamicShip> shipArray = new ArrayList<>();
    ArrayList<DynamicShip> shipArray1 = new ArrayList<>();
    LocalDateTime t1 = LocalDateTime.of(2020,10,20,12,30,00);
    LocalDateTime t2 = LocalDateTime.of(2020, 03, 9, 14,15, 00);
    LocalDateTime t3 = LocalDateTime.of(2021, 03, 9, 14,15, 00);
    DynamicShip ds1 = new DynamicShip(t1, 30.2, 90.0, 1.3, 12.0,0,56,'B');
    DynamicShip ds2 = new DynamicShip(t2, 40.2, 120.0, 3.3, 45.0,0,45,'C');





    public ShipTest() {


        shipArray.add(ds1);
        shipArray1.add(ds2);
        ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 15,0.8,0.1) {
        };
        ship = new Ship(1111, shipArray1, "maria", 1111, "1111", 70, 2.2, 3.0, 2.4, 15,0.8,0.1) {
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
        assertEquals(type, ship1.getDynamicShip());
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

    @Test
    void getDataByDate() {
        assertEquals(ship1.getDataByDate(t1).toString(),"DynamicShip{baseDateTime=2020-10-20T12:30, lat=30.2, lon=90.0, sog=1.3, cog=12.0, heading=0.0, transcrieverClass=B}");
        assertEquals(ship.getDataByDate(t2).toString(),"DynamicShip{baseDateTime=2020-03-09T14:15, lat=40.2, lon=120.0, sog=3.3, cog=45.0, heading=0.0, transcrieverClass=C}");
        assertEquals(ship.getDataByDate(t3).toString(),"DynamicShip{baseDateTime=0000-01-01T00:00, lat=0.0, lon=0.0, sog=0.0, cog=0.0, heading=0.0, transcrieverClass=0}");
    }
}