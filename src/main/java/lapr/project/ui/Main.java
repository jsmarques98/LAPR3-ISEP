package lapr.project.ui;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.DataBaseConnection;
import lapr.project.model.*;
import lapr.project.store.ShipStore;
import lapr.project.utils.CsvReader;
import lapr.project.utils.DateReader;
import lapr.project.utils.KDTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.time.LocalDateTime;
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
        ShipStore st = new ShipStore();
        KDTreePort portTree = new KDTreePort();
        portTree.insertPorts();
        String option = new String();
        // TODO code application logic here
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        st.uploadShipsToDB(databaseConnection);
        st.loadFromDatabase(databaseConnection);
        while (!option.equals("0")) {
            System.out.println("Please make your selection");
            System.out.println("1) Search ship");
            System.out.println("2) Search ship summary");
            System.out.println("3) Search nearest Port");
            System.out.println("0) Leave");
            option = read.readLine();
            String value;
            switch (option){
                case "1":
                    System.out.println("Insert the value: ");
                    value = read.readLine();
                    System.out.println(st.findShip(value));
                    break;
                case "2":
                    System.out.println("Insert the value: ");
                    value = read.readLine();
                    System.out.println(st.shipSummary(value));
                    break;
                case "3":
                    LocalDateTime date;
                    Ship currentShip;
                    ShipBST bst = new ShipBST();
                    bst.insert();
                    System.out.print("Insert the ship's CallSign:");
                    String callSign = read.readLine();
                    if (bst.findShip(callSign) != null) {
                        currentShip = bst.findShip(callSign);
                        date = DateReader.readDate(read, "Insert date: ");
                        DynamicShip data = currentShip.getDataByDate(date);
                        if (data != null) {
                            Port nearestPort = portTree.nearestPort(data.getLat(), data.getLon());
                            System.out.println("The nearest port is "+nearestPort.getPort());
                        }
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case "0":
                    System.out.println("bye");
                    break;
            }
        }
    }
}


