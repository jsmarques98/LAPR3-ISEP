package lapr.project.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipCallSignTest {

    @Test
    public void compareTo() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(1111, shipArray, "maria", 1111, "1111", 70, 2.2, 3.0, 2.4, 15,0.8,0.1);
        Ship ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 15,0.8,0.1);

        ShipCallSign o = new ShipCallSign(ship);
        ShipCallSign o2 = new ShipCallSign(ship1);

        assertEquals(-1, o.compareTo(o2));
    }

    @Test
    public void compareTo1() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(1111, shipArray, "maria", 1111, "2222", 70, 2.2, 3.0, 2.4, 12,0.1,0.1);
        Ship ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 15,0.8,0.1);

        ShipCallSign o = new ShipCallSign(ship);
        ShipCallSign o2 = new ShipCallSign(ship1);

        assertEquals(0, o.compareTo(o2));
    }
    @Test
    public void compareTo2() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(1111, shipArray, "maria", 1111, "3333", 70, 2.2, 3.0, 2.4, 15,0.8,0.1);
        Ship ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 15,0.8,0.1);

        ShipCallSign o = new ShipCallSign(ship);
        ShipCallSign o2 = new ShipCallSign(ship1);

        assertEquals(1, o.compareTo(o2));
    }

}