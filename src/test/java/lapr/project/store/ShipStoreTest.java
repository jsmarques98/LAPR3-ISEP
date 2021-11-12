package lapr.project.store;

import lapr.project.model.DynamicShip;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShipStoreTest {
    ShipStore st = new ShipStore();


    @Test
    void findShip() {
        assertNotNull(st.findShip("210950000"));
    }

    @Test
    void insert() {
        assertTrue(st.insert());
    }

    @Test
    void findShipError(){
        assertEquals(st.findShip("21050000"),"Ship doesn't exist.");
    }

    @Test
    void shipSummary() {
        assertEquals(st.shipSummary("210950000"),"[210950000, VARAMO, 70, 2020-12-31T16:00, 2020-12-31T18:31, 2H31M, 25, 13.7, 13.024, 16.3, -15.611999999999998, 42.69577, -66.97808, 43.22513, -66.96725, 414257.6276107956, 58.8294974871741]");
    }
}