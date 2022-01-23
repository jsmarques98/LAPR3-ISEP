package lapr.project.model;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicShipTest {

    @Test
    public void getBaseDateTime() {
    }

    @Test
    public void getLat() {
        System.out.println("getLatitude()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,23 ,'B');
        double expRes = 124.7;

        assertEquals(expRes, ship.getLat(), "Should be equal");
    }

    @Test
    public void getLon() {
        System.out.println("getLongitude()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,23, 'B');
        double expRes = 315.14;

        assertEquals(expRes, ship.getLon(), "Should be equal");
    }

    @Test
    public void getSog() {
        System.out.println("getSog()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,43, 'B');
        double expRes = 11.47;

        assertEquals(expRes, ship.getSog(), "Should be equal");
    }

    @Test
    public void getCog() {
        System.out.println("getCog()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,45, 'B');
        double expRes = 46.17;

        assertEquals(expRes, ship.getCog(), "Should be equal");
    }

    @Test
    public void getHeading() {
        System.out.println("getHeading()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,32, 'B');
        double expRes = 43.3;

        assertEquals(expRes, ship.getHeading(), "Should be equal");
    }

    @Test
    public void getTranscrieverClass() {
        System.out.println("getTranscrieverClass()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,78, 'B');
        char expRes = 'B';

        assertEquals(expRes, ship.getTranscrieverClass(), "Should be equal");
    }

    @Test
    public void testToString() {
        System.out.println("toString()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,67, 'B');
        String expRes = "DynamicShip{" +
                "baseDateTime=" + ship.getBaseDateTime() +
                ", lat=" + ship.getLat() +
                ", lon=" + ship.getLon() +
                ", sog=" + ship.getSog() +
                ", cog=" + ship.getCog() +
                ", heading=" + ship.getHeading() +
                ", transcrieverClass=" + ship.getTranscrieverClass() +
                '}';

        assertEquals(expRes, ship.toString());
    }

    @Test
    void getCargo() {
        System.out.println("getCargo()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,78, 'B');

        assertEquals(78, ship.getCargo(), "Should be equal");
    }

    @Test
    void setCargo() {
        System.out.println("setCargo()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3,78, 'B');
        ship.setCargo(2);
        assertEquals(2, ship.getCargo(), "Should be equal");
    }
}