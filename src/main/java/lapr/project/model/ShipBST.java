package lapr.project.model;


import lapr.project.utils.AVL;
import lapr.project.utils.BST;
import lapr.project.utils.CsvReader;

import java.util.ArrayList;

public class ShipBST {

    ArrayList<Ship> shipArray;{
        try {
            shipArray = CsvReader.readCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AVL<ShipMmsi> shipMmsiAVL = new AVL<>();
    AVL<ShipIMO> shipImoAVL = new AVL<>();
    AVL<ShipCallSign> shipCallSignAVL = new AVL<>();

    public void insert() {
        for (Ship s: this.shipArray) {
            this.shipMmsiAVL.insert(new ShipMmsi(s));
            this.shipImoAVL.insert(new ShipIMO(s));
            this.shipCallSignAVL.insert(new ShipCallSign(s));

        }
    }

    public void printTrees(){
        shipMmsiAVL.printTreeMmsi("\n");
        shipImoAVL.printTreeImo("\n");
        shipCallSignAVL.printTreeCallSign("\n");
    }

}
