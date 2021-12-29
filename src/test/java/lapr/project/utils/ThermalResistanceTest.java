package lapr.project.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThermalResistanceTest {
    List<Double> dimensions = new ArrayList<>();
    List<Double> kMaterials = new ArrayList<>();
    ThermalResistance t;


    @BeforeEach
    public void setUp() {
        dimensions.add(5.0);
        dimensions.add(2.0);
        dimensions.add(2.0);


        kMaterials.add(52.0);
        kMaterials.add(0.035);
        kMaterials.add(0.170);

        t = new ThermalResistance();
    }

    @Test
    void calculateResistancerLayer() {
        assertEquals(0.075,t.calculateResistancerLayer(0.04,0.03,5.0,2.0));
    }

    @Test
    void calculateResistanceContainer() {
        assertEquals(0.931,t.calculateResistanceContainer(kMaterials,0.03,dimensions));
    }

    @Test
    void calculateResistanceContainer1() {
        dimensions.set(1,0.0);
        assertEquals(0,t.calculateResistanceContainer(kMaterials,0.03,dimensions));
    }
}