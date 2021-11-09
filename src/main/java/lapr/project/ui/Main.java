package lapr.project.ui;

import lapr.project.model.CalculatorExample;
import lapr.project.model.Ship;
//import lapr.project.model.ShipIMO;
import lapr.project.model.ShipMmsi;
import lapr.project.utils.BST;
import lapr.project.utils.CsvReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Nuno Bettencourt <nmb@isep.ipp.pt> on 24/05/16.
 */
class Main {

    /**
     * Logger class.
     */
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Private constructor to hide implicit public one.
     */
    private Main() {

    }

    /**
     * Application main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        CalculatorExample calculatorExample = new CalculatorExample();
        int value = calculatorExample.sum(3, 5);

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, String.valueOf(value));
        }

       /* List<Ship> shipsRead = CsvReader.readCSV();
        List<ShipMmsi> shipArray = shipsRead;


        BST<ShipMmsi> shipBST = new BST<>();

        for (ShipMmsi s: shipArray) {
            shipBST.insert(s);
        }*/


        /*ArrayList<Ship> shipArray2;
        shipArray2 = CsvReader.readCSV();

        BST<ShipIMO> ship2BST = new BST<>();

        for (ShipIMO s: shipArray2) {
            ship2BST.insert(s);
        }*/

/*
        shipBST.printTree("\n");

        int cont=0;
        for (Ship s: shipArray) {
            cont++;
            System.out.println(s.toString());
        }
        System.out.println(cont);
    }*/
    }
}

