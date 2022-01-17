package lapr.project.model;

import lapr.project.data.Ship;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipIMOTest {
    public ShipIMOTest(){
    }

    @Test
    public void compareTo() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(1111, shipArray, "maria", 1111, "1111", 70, 2.2, 3.0, 2.4, 15,0.8,0.1);
        Ship ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 15,0.8,0.1);;

        ShipIMO o = new ShipIMO(ship);
        ShipIMO o2 = new ShipIMO(ship1);
        Assertions.assertEquals(-1, o.compareTo(o2));
    }
    @Test
    public void compareTo1() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(1111, shipArray, "maria", 2222, "1111", 70, 2.2, 3.0, 2.4, 15,0.8,0.1);
        Ship ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 15,0.8,0.1);;

        ShipIMO o = new ShipIMO(ship);
        ShipIMO o2 = new ShipIMO(ship1);
        Assertions.assertEquals(0, o.compareTo(o2));
    }
    @Test
    public void compareTo2() {
        ArrayList<DynamicShip> shipArray = new ArrayList<>();
        Ship ship = new Ship(1111, shipArray, "maria", 3333, "1111", 70, 2.2, 3.0, 2.4, 15,0.8,0.1);
        Ship ship1 = new Ship(2222, shipArray, "jose", 2222, "2222", 71, 2.3, 3.1, 2.4, 15,0.8,0.1);;

        ShipIMO o = new ShipIMO(ship);
        ShipIMO o2 = new ShipIMO(ship1);
        Assertions.assertEquals(1, o.compareTo(o2));
    }
}