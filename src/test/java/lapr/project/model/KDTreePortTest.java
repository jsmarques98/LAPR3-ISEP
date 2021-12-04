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
        port1 = new Port("America","Colombia",28261,"Buenaventura",3.916666667,-77.05);
        port2 = new Port("America","United States",25007,"New Jersey",40.66666667,-74.16666667);
    }

    @Test
    public void testInsertPorts(){
        KDTreePort portTree = new KDTreePort();
        ArrayList<Port> portArray = new ArrayList<>();
        portArray.add(port1);
        portArray.add(port2);

        portTree.insertPorts();

        Port node;
        node = portTree.nearestPort(4.71666667,-77.2666667);
        assertEquals(port1.toString(), node.toString());
        node = portTree.nearestPort(40.66666667, -74.16666667);
        assertEquals(port2.toString(), node.toString());
    }
}