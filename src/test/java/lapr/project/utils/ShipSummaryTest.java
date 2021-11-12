package lapr.project.utils;

import lapr.project.model.DynamicShip;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShipSummaryTest {


    ArrayList<DynamicShip> dynamicShip = new ArrayList<>();

    LocalDateTime time1 = LocalDateTime.of(2020, 12, 31, 23, 28, 00);
    LocalDateTime time2 = LocalDateTime.of(2020, 12, 31, 23, 31, 00);

    DynamicShip shipData1 = new DynamicShip(time1, 54.23188,-130.33667, 0.1, 82.8, 0, 'A');
    DynamicShip shipData2 = new DynamicShip(time2, 54.23184,-130.33702, 0.1, 34.6, 0, 'A');

    Ship ship = new Ship(229961000, dynamicShip, "ARABELLA", 9700122, "9HA3752", 70,
            199, 32, 14.4, 0);


    @Test
    public void createSumary() {
        ship.addDynamicShip(shipData1);
        ship.addDynamicShip(shipData2);
        ArrayList<Object> sumary = ShipSummary.create(ship, "MMSI");
        ArrayList<Object> expectRes = new ArrayList<>();
        expectRes.add(229961000);
        expectRes.add("ARABELLA");
        expectRes.add(70);
        expectRes.add(time1);
        expectRes.add(time2);
        expectRes.add(ShipSummary.getTime(time1,time2));
        expectRes.add(2);
        expectRes.add(0.1);
        expectRes.add(0.1);
        expectRes.add(82.8);
        expectRes.add(58.7);
        expectRes.add(54.23188);
        expectRes.add(-130.33667);
        expectRes.add(54.23184);
        expectRes.add(-130.33702);
        expectRes.add(ShipSummary.totalDistance(ship.getDynamicShip()));
        expectRes.add(ShipSummary.distanciaDelta(54.23188, 54.23184, -130.33667, -130.33702));

        assertEquals(expectRes, sumary);
    }

    @Test
    public void createSumary1() {
        ship.addDynamicShip(shipData1);
        ship.addDynamicShip(shipData2);
        ArrayList<Object> sumary = ShipSummary.create(ship, "IMO");
        ArrayList<Object> expectRes = new ArrayList<>();
        expectRes.add(9700122);
        expectRes.add("ARABELLA");
        expectRes.add(70);
        expectRes.add(time1);
        expectRes.add(time2);
        expectRes.add(ShipSummary.getTime(time1,time2));
        expectRes.add(2);
        expectRes.add(0.1);
        expectRes.add(0.1);
        expectRes.add(82.8);
        expectRes.add(58.7);
        expectRes.add(54.23188);
        expectRes.add(-130.33667);
        expectRes.add(54.23184);
        expectRes.add(-130.33702);
        expectRes.add(ShipSummary.totalDistance(ship.getDynamicShip()));
        expectRes.add(ShipSummary.distanciaDelta(54.23188, 54.23184, -130.33667, -130.33702));

        assertEquals(expectRes, sumary);
    }

    @Test
    public void createSumary3() {
        ship.addDynamicShip(shipData1);
        ship.addDynamicShip(shipData2);
        ArrayList<Object> sumary = ShipSummary.create(ship, "CallSign");
        ArrayList<Object> expectRes = new ArrayList<>();
        expectRes.add("9HA3752");
        expectRes.add("ARABELLA");
        expectRes.add(70);
        expectRes.add(time1);
        expectRes.add(time2);
        expectRes.add(ShipSummary.getTime(time1,time2));
        expectRes.add(2);
        expectRes.add(0.1);
        expectRes.add(0.1);
        expectRes.add(82.8);
        expectRes.add(58.7);
        expectRes.add(54.23188);
        expectRes.add(-130.33667);
        expectRes.add(54.23184);
        expectRes.add(-130.33702);
        expectRes.add(ShipSummary.totalDistance(ship.getDynamicShip()));
        expectRes.add(ShipSummary.distanciaDelta(54.23188, 54.23184, -130.33667, -130.33702));

        assertEquals(expectRes, sumary);
    }
}