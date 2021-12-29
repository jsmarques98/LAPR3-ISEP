package lapr.project.ui;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.DataBaseConnection;
import lapr.project.model.*;
import lapr.project.data.PortStore;
import lapr.project.data.ShipStore;
import lapr.project.store.MaterialStore;
import lapr.project.utils.ThermalResistance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
        ShipStore st = new ShipStore();
        KDTreePort portTree = new KDTreePort();
        portTree.insertPorts();
        String option = new String();
        PortStore pt = new PortStore(portTree);
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
        while (!option.equals("0")) {
            System.out.println("Please make your selection");
            System.out.println("1) Import data to the Database");
            System.out.println("2) Search ship");
            System.out.println("3) Search ship summary");
            System.out.println("4) Search nearest Port");
            System.out.println("5) Container phisics");
            System.out.println("0) Leave");
            option = read.readLine();
            String value;
            switch (option){
                case "1":
                    importMenu();
                    break;
                case "2":
                    System.out.println("Insert the value: ");
                    value = read.readLine();
                    System.out.println(st.findShip(value));
                    break;
                case "3":
                    System.out.println("Insert the value: ");
                    value = read.readLine();
                    System.out.println(st.shipSummary(value));
                    break;
                case "4":
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
                            System.out.println("The nearest port is "+nearestPort.getPorto());
                        }
                    } else {
                        System.out.println("Ship not found");
                    }
                    break;
                case "5":
                    physicsMenu();
                    break;
                case "0":
                    System.out.println("bye");
                    break;
            }
        }
    }
    private static void importMenu() throws IOException, SQLException {
        ShipStore st = new ShipStore();
        KDTreePort portTree = new KDTreePort();
        String option = new String();
        PortStore pt = new PortStore(portTree);
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
        System.out.println("1) Import Ship's");
        System.out.println("2) Import Port's");
        System.out.println("0) Leave");
        option = read.readLine();
        switch (option) {
            case "1":
                st.uploadShipsToDB(databaseConnection);
                st.loadFromDatabase(databaseConnection);
                break;
            case "2":
                pt.uploadPortstoDatabase(databaseConnection);
                pt.loadPortFromDatabase(databaseConnection);
                break;
            case "0":
                System.out.println("bye");
                break;
        }
    }

    private static void physicsMenu() throws IOException {
        String option = new String();
        MaterialStore ms = new MaterialStore();
        Material m1 = new Material("Aco",52.00,"OutsideLayer");
        Material m2 = new Material("Poliestireno Extrudido",0.035,"MiddleLayer");
        Material m3 = new Material("Madeira Composta",0.170,"InsideLayer");
        Material m4 = new Material("Ferro",80.0,"OutsideWall");
        Material m5 = new Material("La de Rocha",0.04,"MiddleLayer");
        Material m6 = new Material("Cortica",0.04,"InsideLayer");
        ms.addMaterial(m1);
        ms.addMaterial(m2);
        ms.addMaterial(m3);
        ms.addMaterial(m4);
        ms.addMaterial(m5);
        ms.addMaterial(m6);
        List<Double> kMaterials = new ArrayList<>();
        kMaterials.add(m1.getThermalConductivity());
        kMaterials.add(m2.getThermalConductivity());
        kMaterials.add(m3.getThermalConductivity());
        List<Double> dimensions = new ArrayList<>();
        dimensions.add(5.0);
        dimensions.add(2.0);
        dimensions.add(2.0);
        ThermalResistance t = new ThermalResistance();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("1) Show the materials for the container");
        System.out.println("2) Resistivity of the container for the 5 degrees");
        System.out.println("3) Resistivity of the container for the 7 negative degrees");
        System.out.println("0) Leave");
        option = read.readLine();
        switch (option) {
            case "1":
                List<Material> materials = ms.getMaterialList();
                String infoMaterialsContainer="";
                for(Material m :materials ){
                    infoMaterialsContainer+=m.toString()+"\n";
                }
                System.out.println(infoMaterialsContainer);
                break;
            case "2":
                System.out.println(String.format("Outer layer material : %s\nMiddle layer material : %s\nInterior layer material : %s\n\n" +
                                "This container with this material has a thermal resistance = %.3f\n\n\n",m1,m2,
                        m3,t.calculateResistanceContainer(kMaterials,0.03, dimensions)));
                break;
            case "3":
                System.out.println(String.format("Outer layer material : %s\nMiddle layer material : %s\nInterior layer material : %s\n\n" +
                                "This container with this material has a thermal resistance = %.3f\n\n\n",m1,m5,
                        m6,t.calculateResistanceContainer(kMaterials,0.03, dimensions)));
                break;
            case "0":
                break;
        }
    }
}


