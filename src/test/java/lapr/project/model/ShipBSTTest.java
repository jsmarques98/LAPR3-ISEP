package lapr.project.model;

import lapr.project.utils.BST;
import lapr.project.utils.ShipSummary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipBSTTest {
    ShipBST sb = new ShipBST();
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
}