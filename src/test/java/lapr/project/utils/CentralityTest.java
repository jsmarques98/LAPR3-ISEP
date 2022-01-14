package lapr.project.utils;

import lapr.project.data.*;
import lapr.project.model.KDTreePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CentralityTest {

    PositionMatrixGraph pmg;
    CountryStore cs;
    PortStore ps;
    SeaDistStore sds;
    BorderStore bs;
    KDTreePort kdt;
    Centrality cen;

    @BeforeEach
    public void initializeGraph() {
        pmg = new PositionMatrixGraph();
        cs = new CountryStore();
        kdt = new KDTreePort();
        sds = new SeaDistStore();
        ps = new PortStore(kdt);
        bs = new BorderStore();
        cen = new Centrality();
        pmg.fillMatrixGraph(3,cs.getCountryList(),ps.getPortList(),sds.getSeaDistArrayList(),bs.toMap(bs.getBorderArray(),cs.getCountryArray()));
    }

    @Test
    void getNCentralPorts() {
        assertEquals("Portugal, Ponta Delgada => with 156 detections in shostest paths. \n\n", cen.getNCentralPorts(pmg.getCompleteMap(),1));
    }

    @Test
    void testToString() {
        cen.getNCentralPorts(pmg.getCompleteMap(),1);
        assertEquals("Portugal, Ponta Delgada => with 156 detections in shostest paths. \n\n", cen.toString(1,cen.centralityPort));

    }
}