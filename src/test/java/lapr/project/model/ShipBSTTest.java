package lapr.project.model;

import lapr.project.utils.BST;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipBSTTest {

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
}