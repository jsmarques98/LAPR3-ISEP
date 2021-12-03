package lapr.project.model;

import lapr.project.model.KDTreePort;
import lapr.project.model.Port;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class KDTreePortTest {

    Port port1;
    Port port2;
    Port port3;

    public KDTreePortTest (){
        port1 = new Port("Europe","United Kingdom", 29002,"Liverpool",53.46666667,-3.033333333);
        port2 = new Port("America","United States",14635,"Los Angeles",33.71666667,-118.2666667);
        port3 = new Port("America","United States",25007,"New Jersey",40.66666667,-74.16666667);
    }

    @Test
    public void testInsertPorts(){
        KDTreePort portTree = new KDTreePort();
        ArrayList<Port> portArray = new ArrayList<>();
        portArray.add(port1);
        portArray.add(port2);
        portArray.add(port3);

        portTree.insertPorts();

        Port node;
        node = portTree.nearestPort(33.71666667,-118.2666667);
        assertEquals(port2.toString(), node.toString());
        node = portTree.nearestPort(40.66666667, -74.16666667);
        assertEquals(port3.toString(), node.toString());
    }
}