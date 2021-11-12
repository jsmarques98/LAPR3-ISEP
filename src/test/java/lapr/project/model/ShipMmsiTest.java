package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipMmsiTest {

    public ShipMmsiTest(){
    }

    @Test
    public void compareTo() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(1111, shipArray, "maria", 1111, "1111", 70, 2.2, 3.0, 2.4, 12.0);
        Ship ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 12.1);

        ShipMmsi o = new ShipMmsi(ship);
        ShipMmsi o2 = new ShipMmsi(ship1);

        assertEquals(-1, o.compareTo(o2));
    }
    @Test
    public void compareTo1() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(1111, shipArray, "maria", 1111, "1111", 70, 2.2, 3.0, 2.4, 12.0);
        Ship ship1 = new Ship(1111, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 12.1);

        ShipMmsi o = new ShipMmsi(ship);
        ShipMmsi o2 = new ShipMmsi(ship1);

        assertEquals(0, o.compareTo(o2));
    }
    @Test
    public void compareTo2() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(2222, shipArray, "maria", 1111, "1111", 70, 2.2, 3.0, 2.4, 12.0);
        Ship ship1 = new Ship(1111, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 12.1);

        ShipMmsi o = new ShipMmsi(ship);
        ShipMmsi o2 = new ShipMmsi(ship1);

        assertEquals(1, o.compareTo(o2));
    }
}