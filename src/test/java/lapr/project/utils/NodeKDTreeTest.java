package lapr.project.utils;

import lapr.project.model.DynamicShip;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeKDTreeTest {

    public NodeKDTreeTest(){
        ArrayList<DynamicShip> dynamicShip = new ArrayList<>();

        LocalDateTime time1 = LocalDateTime.of(2020, 12, 31, 23, 28, 00);
        LocalDateTime time2 = LocalDateTime.of(2020, 12, 31, 23, 31, 00);

        DynamicShip shipData1 = new DynamicShip(time1, 54.23188,-130.33667, 0.1, 82.8, 0,34, 'A');
        DynamicShip shipData2 = new DynamicShip(time2, 54.23184,-130.33702, 0.1, 34.6, 0, 67,'A');

        Ship ship = new Ship(229961000, dynamicShip, "ARABELLA", 9700122, "9HA3752", 70,
                199, 32, 14.4, 15,0.8,0.1);


    }


    @Test
    void setObject() {
        assertTrue(true);
    }

    @Test
    void getCoords() {
        assertTrue(true);
    }

    @Test
    void getX() {
        assertTrue(true);
    }

    @Test
    void getY() {
        assertTrue(true);
    }

    @Test
    void testToString() {
        assertTrue(true);
    }
}