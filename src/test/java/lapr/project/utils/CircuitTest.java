package lapr.project.utils;

import lapr.project.data.*;
import lapr.project.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CircuitTest {
    PositionMatrixGraph pmg;
    CountryStore cs;
    PortStore ps;
    SeaDistStore sds;
    BorderStore bs;
    KDTreePort kdt;
    Circuit circ;


    @BeforeEach
    public void initializeGraph() {
        pmg = new PositionMatrixGraph();
        cs = new CountryStore();
        kdt = new KDTreePort();
        sds = new SeaDistStore();
        ps = new PortStore(kdt);
        bs = new BorderStore();
        circ = new Circuit();
        pmg.fillMatrixGraph(3,cs.getCountryList(),ps.getPortList(),sds.getSeaDistArrayList(),bs.toMap(bs.getBorderArray(),cs.getCountryArray()));
    }

    @Test
    void mostEfficientCircuit(){
        assertEquals("Tbilisi\n" +
                "Ankara\n" +
                "Athens\n" +
                "Tirana\n" +
                "Skopje\n" +
                "Pristina\n" +
                "Podgorica\n" +
                "Sarajevo\n" +
                "Belgrade\n" +
                "Sofia\n" +
                "Bucharest\n" +
                "Chisinau\n" +
                "Kyiv\n" +
                "Budapest\n" +
                "Zagreb\n" +
                "Ljubljana\n" +
                "Rome\n" +
                "Bern\n" +
                "Vaduz\n" +
                "Vienna\n" +
                "Bratislava\n" +
                "Prague\n" +
                "Warsaw\n" +
                "Minsk\n" +
                "Vilnius\n" +
                "Riga\n" +
                "Tallinn\n" +
                "Moscow\n",circ.mostEfficientCircuit(pmg.getCompleteMap()));

    }

}