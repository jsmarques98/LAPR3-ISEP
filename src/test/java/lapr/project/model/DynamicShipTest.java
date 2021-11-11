package lapr.project.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicShipTest {

    @Test
    public void getBaseDateTime() {
    }

    @Test
    public void getLat() {
        System.out.println("getLatitude()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3, 'B');
        double expRes = 124.7;

        assertEquals(expRes, ship.getLat(), "Should be equal");
    }

    @Test
    public void getLon() {
        System.out.println("getLongitude()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3, 'B');
        double expRes = 315.14;

        assertEquals(expRes, ship.getLon(), "Should be equal");
    }

    @Test
    public void getSog() {
        System.out.println("getSog()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3, 'B');
        double expRes = 11.47;

        assertEquals(expRes, ship.getSog(), "Should be equal");
    }

    @Test
    public void getCog() {
        System.out.println("getCog()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3, 'B');
        double expRes = 46.17;

        assertEquals(expRes, ship.getCog(), "Should be equal");
    }

    @Test
    public void getHeading() {
        System.out.println("getHeading()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3, 'B');
        double expRes = 43.3;

        assertEquals(expRes, ship.getHeading(), "Should be equal");
    }

    @Test
    public void getTranscrieverClass() {
        System.out.println("getTranscrieverClass()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3, 'B');
        char expRes = 'B';

        assertEquals(expRes, ship.getTranscrieverClass(), "Should be equal");
    }

    @Test
    public void testToString() {
        System.out.println("toString()");

        DynamicShip ship = new DynamicShip(LocalDateTime.now(), 124.7, 315.14, 11.47, 46.17, 43.3, 'B');
        String expRes = "Date: " + ship.getBaseDateTime().toLocalDate() +
                ", Time: " + ship.getBaseDateTime().toLocalTime() +
                ", Latitude: " + ship.getLat() +
                ", Longitude: " + ship.getLon() +
                ", SOG: " + ship.getSog() +
                ", COG: " + ship.getCog() +
                ", Heading: " + ship.getHeading() +
                ", Transceiver: " + ship.getTranscrieverClass();

        assertEquals(expRes, ship.toString());
    }
}