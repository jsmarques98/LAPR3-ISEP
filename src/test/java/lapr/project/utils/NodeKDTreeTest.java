package lapr.project.utils;

import lapr.project.model.DynamicShip;
import lapr.project.model.Ship;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeKDTreeTest {

    NodeKDTree<Object> node1;
    NodeKDTree<Object> node2;
    NodeKDTree<Object> node3;
    NodeKDTree<Object> node4;
    NodeKDTree<Object> node5;

    Object obj1;
    Object obj2;
    Object obj3;
    Object obj4;
    Object obj5;

    public NodeKDTreeTest(){
        obj1 = new Object();
        obj2 = new Object();
        obj3 = new Object();
        obj4 = new Object();
        obj5 = new Object();

        node1 = new NodeKDTree<>(obj1, 0, 0);
        node2 = new NodeKDTree<>(obj2, 10, 10);
        node3 = new NodeKDTree<>(obj3, 0, 10);
        node4 = new NodeKDTree<>(obj4, 10, 0);
        node5 = new NodeKDTree<>(obj5, 20, 20);

    }

    @Test
    void setObject() {
        node1.setObject(obj1);
        assertEquals(node1.getObject(), obj1);
    }

}