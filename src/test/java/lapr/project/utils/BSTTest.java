package lapr.project.utils;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BSTTest {
    BST<Integer> bst1;
    BST<String> bst2;
    Integer[] arr = {20,15,10,13,8,17,40,50,30,7};
    Integer[] inorderT = {7,8,10,13,15,17,20,30,40,50};


    public BSTTest() {
    }

    @BeforeEach
    public void setUp() {
        bst1 = new BST();
        for(int i :arr)
            bst1.insert(i);
        bst2 = new BST();
    }

    @AfterEach
    public void teardown() throws Exception{
    }

    /**
     * Test of insert method in class BST.
     */
    @Test
    public void testInsertBST() {
        System.out.println("\t\tExecuting " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        BST<Integer> instance = new BST();
        int[] new_arr = {20,15,10,13,8,17,40,50,30,20,15,10};


        for (int i=0; i< 9; i++){            //new elements
            instance.insert(new_arr[i]);
            assertEquals( i+1, instance.size(), "size should be = "+(i+1));
        }

        for(int i=9; i<new_arr.length; i++){    //duplicated elements => same size
            instance.insert(new_arr[i]);
            assertEquals(9, instance.size(), "size should be = 9");
        }
    }


    /**
     * Test of ensure the same size of BST.
     */
    @Test
    public void testEnsureSameSizeBST() {
        System.out.println("\t\tExecuting " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        assertEquals(arr.length, bst1.size(),"size should be = 10");
    }

    /**
     * Test for ensure increase size of BST.
     */
    @Test
    public void testEnsureIncreaseSizeBST() {
        System.out.println("\t\tExecuting " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        bst2.insert("A");
        assertEquals(1, bst2.size(), "size should be = 1");

        bst2.insert("B");
        assertEquals(2, bst2.size(), "size should be = 2");

        bst2.insert("C");
        assertEquals(3, bst2.size(), "size should be = 3");
    }

    /**
     * Test for ensure same size for same element in BST.
     */
    @Test
    public void testEnsureSameSizeSameElementBST() {
        System.out.println("\t\tExecuting " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        int expected = 1;

        bst2.insert("A");
        assertEquals(expected, bst2.size(), "size should be = 1");

        bst2.insert("A");
        assertEquals(expected, bst2.size(), "size should be = 1");

        bst2.insert("A");
        assertEquals(expected, bst2.size(), "size should be = 1");
    }


    /**
     * Test of toString method of class BST.
     */
    @Test
    public void testToStringBST() {
        System.out.println("\t\tExecuting " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        String expected = "|\t|-------50\n" +
                "|-------40\n" +
                "|\t|-------30\n" +
                "20\n" +
                "|\t|-------17\n" +
                "|-------15\n" +
                "|\t|\t|-------13\n" +
                "|\t|-------10\n" +
                "|\t|\t|-------8\n" +
                "|\t|\t|\t|-------7\n";
        String result = bst1.toString();
        assertEquals(expected, result);
    }

    /**
     * Test of smallestElement method, of class TREE.
     */
    @Test
    public void testSmallestElement() {
        System.out.println("\t\tExecuting " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        assertEquals(Integer.valueOf(7), bst1.smallestElement());
    }

    /**
     * Test of inOrder method, of class BST.
     */
    @Test
    public void testInOrder() {
        System.out.println("\t\tExecuting " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        List<Integer> lExpected = Arrays.asList(inorderT);
        assertEquals(lExpected, bst1.inOrder(), "inOrder should be "+lExpected.toString());
    }


    @Test
    void printTreeMmsi() {
        assertTrue(true);
    }

    @Test
    void printTreeImo() {
        assertTrue(true);
    }

    @Test
    void printTreeCallSign() {
        assertTrue(true);
    }

    @Test
    void testPrintTreeMmsi() {
        assertTrue(true);
    }

    @Test
    void testPrintTreeImo() {
        assertTrue(true);
    }

    @Test
    void testPrintTreeCallSign() {
        assertTrue(true);
    }

    @Test
    void findMSSI() {
        assertTrue(true);
    }

    @Test
    void findIMO() {
        assertTrue(true);
    }

    @Test
    void findCALLSIGN() {
        assertTrue(true);
    }
}