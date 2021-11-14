package lapr.project.store;

import lapr.project.model.Ship;
import lapr.project.model.ShipBST;

public class ShipStore {

    private ShipBST shipBST = new ShipBST();

    public ShipStore(){
        shipBST.insert();
    }

    public String findShip(String s){
        Ship ship = shipBST.findShip(s);
        if(ship == null)
            return "Ship doesn't exist.";
        else
            return ship.toString1();
    }

    public boolean insert(){
        shipBST.insert();
        return true;
    }

   public boolean print(){shipBST.printTrees();
    return true;
    }

    public String shipSummary(String s){
        return shipBST.shipSummary(s);
    }

}
