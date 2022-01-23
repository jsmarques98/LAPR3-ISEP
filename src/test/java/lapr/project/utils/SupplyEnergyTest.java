package lapr.project.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplyEnergyTest {

    SupplyEnergy se;
    double[][] areas;
    double[][] areas2;
    List<Double> time;
    List<Double> temp;
    Double resistivityC;
    Double resistivityS;

    @BeforeEach
    public void setUp(){
        areas = new double[][]{{24.0, 20.0, 24.0}, {14.0, 10.0, 14.0}, {14.0, 10.0, 14.0}, {24.0, 20.0, 24.0}};
        areas2 = new double[][]{{24.0, 20.0,20.0,20.0, 24.0}, {14.0, 10.0,10.0,10.0, 14.0},{14.0, 10.0,10.0,10.0, 14.0}, {24.0, 20.0,20.0,20.0, 24.0}};
        time = new ArrayList<>();
        temp = new ArrayList<>();
        resistivityC = 1.38;
        resistivityS = 0.96;
        se = new SupplyEnergy();
        temp.add(20.0);
        temp.add(15.0);
        time.add(0.836667);
        time.add(0.836667);
    }

    @Test
    void calcPowerEmittedSun() {
        assertEquals(10058.651529110737,se.calcPowerEmittedSun(areas[0][0],temp.get(0)));
    }

    @Test
    void calcEnergy() {
        assertEquals(54565.23913043479,se.calcEnergy(resistivityC,temp.get(0),-5.0,time.get(0)));
        assertEquals(0.0,se.calcEnergy(0.0,temp.get(0),-5.0,time.get(0)));
    }

    @Test
    void calcTripEnergy() {
        assertEquals(98217.43043478263,se.calcTripEnergy(temp,time,resistivityC,-5.0));
    }

    @Test
    void calcContainersTripEnergy() {
        assertEquals(658875.2625000002,se.calcContainersTripEnergy(temp,time,resistivityS,7.0,10));
    }

    @Test
    void numOfGen() {
        assertEquals(3,se.numOfGen(temp,time,areas2,2));
    }

    @Test
    void calcTotalEnergy() {
        assertEquals(1.1364492656336825E9,se.calcTotalEnergy(2,areas2,temp,time,resistivityC,-5.0));
    }

    @Test
    void journetTime() {
        assertEquals(1.673334,se.journetTime(time));
    }
}