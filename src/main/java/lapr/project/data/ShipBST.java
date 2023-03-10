package lapr.project.data;


import lapr.project.model.ShipCallSign;
import lapr.project.model.ShipIMO;
import lapr.project.model.ShipMmsi;
import lapr.project.utils.CsvReader;
import lapr.project.utils.ShipSummary;

import java.util.ArrayList;

public class ShipBST {

    ArrayList<Ship> shipArray;

    public ShipBST(){
        try {
            String path = "src/main/java/lapr/project/data/sships.csv";
            shipArray = CsvReader.readCSV(path);
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

    public void insert(ArrayList<Ship> shipArray){
        for (Ship s: shipArray) {
            this.shipMmsiAVL.insert(new ShipMmsi(s));
            this.shipImoAVL.insert(new ShipIMO(s));
            this.shipCallSignAVL.insert(new ShipCallSign(s));
        }
    }

    public boolean printTrees(){
        shipMmsiAVL.printTreeMmsi("\n");
        shipImoAVL.printTreeImo("\n");
        shipCallSignAVL.printTreeCallSign("\n");
        return true;
    }

    public Ship findShip(String s){
        Ship ship;
        if(s == null)
            return null;
        else if(s.length() == 9)
           ship = shipMmsiAVL.findMSSI(shipMmsiAVL.root(), s);
        else if (s.length() == 7)
            ship = shipImoAVL.findIMO(shipImoAVL.root(),s);
        else
            ship = shipCallSignAVL.findCALLSIGN(shipCallSignAVL.root(),s);
        return ship;
    }

    public Iterable<ShipMmsi> getTreeForDB(){
        return shipMmsiAVL.inOrder();
    }

    public  String shipSummary(String s){
        ShipSummary shipSummary = new ShipSummary();
        if(s.isEmpty())
            return "String vazia";
        Ship ship = findShip(s);
        if(ship == null)
            return "Ship doesn't exist.";
        else if(s.length() == 9)
            return shipSummary.create(ship,"MMSI").toString();
        else if (s.length() == 7)
            return shipSummary.create(ship,"IMO").toString();
        else
            return shipSummary.create(ship,"CallSign").toString();
    }

}
