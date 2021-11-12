package lapr.project.store;

import lapr.project.model.ShipBST;

public class ShipStore {

    private ShipBST shipBST;

    public ShipStore(){
        shipBST = new ShipBST();
    }

    public String findShip(String s){
        return shipBST.findShip(s);
    }

    public void insert(){
        shipBST.insert();
    }

    public void print(){
        shipBST.printTrees();
    }

}
