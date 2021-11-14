package lapr.project.model;

import lapr.project.utils.BST;
import lapr.project.utils.ShipSummary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipBSTTest {
    ShipBST sb = new ShipBST();
     public ShipBSTTest(){
         sb.insert();
     }
    @Test
    public void insert() {
        System.out.println("insert");
        int arr[] = {20,15,10,13,8,17,40,50,30,20,15,10};
        BST<Integer> instance = new BST();
        for (int i=0; i<9; i++){            //new elements
            instance.insert(arr[i]);
            assertEquals(instance.size(), i+1,"size should be = "+(i+1));
        }
        for(int i=9; i<arr.length; i++){    //duplicated elements => same size
            instance.insert(arr[i]);
            assertEquals(instance.size(), 9,"size should be = 9");
        }
    }

    @Test
    public void printTrees() {
    }

    @Test
    void shipSummaryEmpty() {
        assertEquals(sb.shipSummary(""),"String vazia");
    }
    @Test
    void shipSummaryNull() {
        assertEquals(sb.shipSummary("1234"),"Ship doesn't exist.");
    }
    @Test
    void shipSummaryMMSI() {
        assertEquals(sb.shipSummary("210950000"),"[210950000, VARAMO, 70, 2020-12-31T16:00, 2020-12-31T18:31, 2H31M, 25, 13.7, 13.024, 16.3, -15.611999999999998, 42.69577, -66.97808, 43.22513, -66.96725, 414257.6276107956, 58.8294974871741]");
    }
    @Test
    void shipSummaryIMO() {
        assertEquals(sb.shipSummary("9395044"),"[9395044, VARAMO, 70, 2020-12-31T16:00, 2020-12-31T18:31, 2H31M, 25, 13.7, 13.024, 16.3, -15.611999999999998, 42.69577, -66.97808, 43.22513, -66.96725, 414257.6276107956, 58.8294974871741]");
    }
    @Test
    void shipSummaryCallSign() {
        assertEquals(sb.shipSummary("C4SQ2"),"[C4SQ2, VARAMO, 70, 2020-12-31T16:00, 2020-12-31T18:31, 2H31M, 25, 13.7, 13.024, 16.3, -15.611999999999998, 42.69577, -66.97808, 43.22513, -66.96725, 414257.6276107956, 58.8294974871741]");
    }

    @Test
    void findShipCallSign() {
        assertNotNull(sb.findShip("C4SQ2"));
    }
    @Test
    void findShipIMO() {
        assertNotNull(sb.findShip("9395044"));
    }
    @Test
    void findShipMMSI() {
        assertNotNull(sb.findShip("210950000"));
    }
    @Test
    void findShipNull() {
        assertNull(sb.findShip(null));

    }
}