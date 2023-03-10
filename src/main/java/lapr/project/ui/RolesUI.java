package lapr.project.ui;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.store.MaterialStore;
import lapr.project.utils.*;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RolesUI {
    private final String INITIALOPTIONPHRASE="Please make your selection";
    private final String FINALOPTIONPHRASE="0) Leave";
    BorderStore bs;
    CountryStore cs;
    PortStore ps;
    SeaDistStore sds;
    ShipStore ss;
    ArrayList<Ship> shipList = new ArrayList<>();
    ArrayList<Border> borderList = new ArrayList<>();
    ArrayList<Port> portList = new ArrayList<>();
    ArrayList<SeaDist> seaDistList = new ArrayList<>();
    ArrayList<Country> countryList = new ArrayList<>();
    PositionMatrixGraph pmg = new PositionMatrixGraph();
    Centrality cent;
    Circuit circ;
    SupplyEnergy se;
    private static ColourMapUI colorMapUI = new ColourMapUI();
    public RolesUI(){
        bs = new BorderStore();
        cs = new CountryStore();
        KDTreePort portTree = new KDTreePort();
        ps = new PortStore(portTree);
        sds = new SeaDistStore();
        ss = new ShipStore();
        cent = new Centrality();
        se = new SupplyEnergy();
    }


    public  void client(String user) throws IOException {
        System.out.println(user);
        String option = "";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println(INITIALOPTIONPHRASE);
            System.out.println("1) I want to know the route of a specific container I am leasing");
            System.out.println("2) I want to know the current situation of a specific container being used to transport my goods");
            System.out.println(FINALOPTIONPHRASE);
            option = read.readLine();
            switch (option){
                case "1":
                    break;
                case "2":
                    break;
                default:
                    System.out.println("The option doens't exist");
                    break;
            }
        }
    }
    public void shipEmploye(String userId) throws IOException {
        System.out.println(userId);
        noOptionMenu();
    }
    public void shipCapitan(String userId) throws IOException {
        List<Vessel> vessels = new ArrayList<>();
        System.out.println(userId);
        String option = "";
        Calculus cal = new Calculus();
        vessels.add(new Vessel("Bulk Carrier",0,275,160000,40,cal.areaCalc(213.0,11.0,false),
                cal.areaCalc(17.13,11,false), cal.areaCalc(29,29,false) ));
        vessels.add(new Vessel("Panamax",5100,275,52500,57.91,cal.areaCalc(294.0,28,false),
                cal.areaCalc(29,29,false), cal.areaCalc(29,28,true)));
        vessels.add(new Vessel("ULCV",2000,275,52500,57.91,cal.areaCalc(366.46,29.0,false),
                cal.areaCalc(29,19,true), cal.areaCalc(20,10,false)));
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println(INITIALOPTIONPHRASE);
            System.out.println("1) I want to have access to audit trails for a given container of a given cargo manifest");
            System.out.println("2) I intend to submit a summary document, with the following items.");
            System.out.println("3) I want the determine the unladen center of mass for each vessel according to its characteristics.");
            System.out.println("4) I want to know where to position, for example, one hundred containers on the vessel, such that the center of mass remains at xx and yy, determined in the previous point.");
            System.out.println("5) I want to know for a specific vessel, how much did the vessel sink, assuming that each container has half a ton of mass");
            System.out.println(FINALOPTIONPHRASE);
            option = read.readLine();
            switch (option){
                case "1":
                    System.out.println("Insert container id:");
                    String containerId = read.readLine();
                    System.out.println("Insert cargo manifest id:");
                    String cargoManifest = read.readLine();
                    auditTrailsLog(containerId,cargoManifest);
                    break;
                case "2":

                    System.out.println("Bulk Carrier \n" +
                            "\n" +
                            "Capacity: 3000 to 300000 tonnes \n" +
                            "Length: 180 to 275m \n" +
                            "Tonnage: 33000 to 160000 DWT \n" +
                            "Height: 40m \n" +
                            "\n" +
                            " \n" +
                            "Panamax \n" +
                            "\n" +
                            "The size of a Panamax vessel is limited by the original Panama canal???s lock chambers (width and length), by the depth of the water in the canal and by the height of the Bridge of the Americas since that bridge???s construction. A Panamax cargo ship would typically have a 65,000???80,000tonnes, but its maximum cargo would be about 52,500 tonnes during a transit due to draft limitations in the canal \n" +
                            "Capacity(TEU) : 3,001???5,100 \n" +
                            "Length: 965ft (294.13m) \n" +
                            "Beam: 106ft (32.31m) \n" +
                            "Draft:  39.5ft (12.04m) \n" +
                            "Tonnage: 52,500 DWT \n" +
                            "Height: 190ft (57.91m) \n" +
                            " \n" +
                            "\n" +
                            "DWT ?? The more heavily loaded a ship is, the lower it sits in the water. Maximum DWT is the amount of weight a ship can carry without riding dangerously low in the water. \n" +
                            "\n" +
                            " \n" +
                            "Feeder \n" +
                            "\n" +
                            "Capacity: 1,001???2,000  \n" +
                            "length: 160 m \n" +
                            "beam: 26.80 m \n" +
                            "\n" +
                            "Feeder vessels or feeder ships are medium-size freight ships. In general, a feeder designates a seagoing vessel with an average capacity of 300 to 1000 twenty-foot equivalent units (TEU). Feeders collect shipping containers from different ports and transport them to central container terminals where they are loaded to bigger vessels, or for further transport into the hub port's hinterland. ");

                    //[417]
                    break;
                case "3":
                    double[] xCords;
                    double[] yCords;
                     cal = new Calculus();
                    System.out.println("Please insert the Vessel you want to see");
                    System.out.println("1-Bulk carrier");
                    System.out.println("2-Panamax");
                    System.out.println("3-ULCV");
                    System.out.println("0-Leave");
                    String optionVessel = read.readLine();
                    switch (optionVessel){
                        case "1":
                            xCords = new double[]{213, 11, 9};
                            yCords = new double[]{11, 17.13, 15};
                            vessels.set(0,cal.centerOfMass(vessels.get(0),xCords,yCords ));
                            System.out.println("Center of mass");
                            System.out.println(vessels.get(0).getxCm()+" ; "+vessels.get(0).getyCm());
                            break;
                        case "2":
                            xCords = new double[]{294, 29, 29};
                            yCords = new double[]{28, 29, 28};
                            vessels.set(1,cal.centerOfMass(vessels.get(1),xCords,yCords ));
                            System.out.println("Center of mass");
                            System.out.println(vessels.get(1).getxCm()+" ; "+vessels.get(1).getyCm());
                            break;
                        case "3":
                            xCords = new double[]{366.46, 19, 10};
                            yCords = new double[]{29, 29, 20};
                            vessels.set(2,cal.centerOfMass(vessels.get(2),xCords,yCords ));
                            System.out.println("Center of mass");
                            System.out.println(vessels.get(2).getxCm()+" ; "+vessels.get(2).getyCm());
                            break;
                        case "0":
                            break;
                        default:
                            break;

                    }
                    //[418]
                    break;

                case "4":
                    cal = new Calculus();
                    System.out.println("Area do container 10 m2");
                    System.out.println("length do container 5m, heigth 2m");
                    System.out.println("Please insert the Vessel you want to use");
                    System.out.println("1-Bulk carrier");
                    System.out.println("2-Panamax");
                    System.out.println("3-ULCV");
                    System.out.println("0-Leave");
                    optionVessel = read.readLine();
                    System.out.println("Insert number of containers:");
                    String optionContainers= read.readLine();
                    int numCont = Integer.parseInt(optionContainers);
                    switch (optionVessel) {
                        case "1":
                            System.out.println(cal.positionContainers(numCont, vessels.get(0)));
                            break;
                        case "2":
                            System.out.println(cal.positionContainers(numCont, vessels.get(1)));
                            break;
                        case "3":
                            System.out.println(cal.positionContainers(numCont, vessels.get(2)));
                            break;
                        case "0":
                            break;
                        default:
                            break;
                    }
                    //[419]
                    break;
                case "5":
                    cal= new Calculus();
                    System.out.println("Area do container 10 m2");
                    System.out.println("length do container 5m, heigth 2m");
                    System.out.println("Please insert the Vessel you want to use");
                    System.out.println("1-Bulk carrier");
                    System.out.println("2-Panamax");
                    System.out.println("3-ULCV");
                    System.out.println("0-Leave");
                    optionVessel = read.readLine();
                    System.out.println("Insert number of containers:");
                    optionContainers= read.readLine();
                    numCont = Integer.parseInt(optionContainers);
                    Vessel tempVessel= null;
                    double peso=1.0;
                    switch (optionVessel) {
                        case "1":
                            tempVessel = vessels.get(0);
                            tempVessel.setBeam(21.0);
                            tempVessel.setDeeph(12.5);
                            tempVessel.setDraft(11.0);
                            peso=numCont;
                            break;
                        case "2":
                            tempVessel = vessels.get(1);
                            tempVessel.setDraft(12.04);
                            tempVessel.setBeam(32.3);
                            tempVessel.setDeeph(28);
                            peso=cal.containerWeight(numCont);
                            break;
                        case "3":
                            tempVessel = vessels.get(2);
                            tempVessel.setDraft(15.5);
                            tempVessel.setDeeph(29.85);
                            tempVessel.setBeam(48.2);
                            peso=cal.containerWeight(numCont);
                            break;
                        case "0":
                            break;
                        default:
                            break;
                    }

                    tempVessel.setVolume(cal.volumeOfaShip(tempVessel,tempVessel.getBeam()));

                    double submergedValue=cal.submergedWidth(tempVessel);
                    double submergedVolume = cal.volumeOfaShip(tempVessel,submergedValue);
                    double mass = cal.massOfaShip(submergedVolume);
                    tempVessel.setMass(mass);
                    double submergedHeight= cal.submergedHeigth(tempVessel,peso);

                    System.out.println("The vessel has submerged "+String.format("%.2f",(submergedHeight-tempVessel.getDraft()))+" meters");
                    break;
                default:
                    System.out.println("The option doens't exist");
                    break;
            }
        }
    }
    private void auditTrailsLog(String containerId, String cargoManifestId){
        DataBaseConnection databaseConnection = null;

        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(RolesUI.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        Connection connection = databaseConnection.getConnection();
        String sqlCommand = "select \"user_id\",\"operation_type\",\"date\" from \"audit_trails\" WHERE \"container_id\" = ? AND \"cargo_manifest_id\" = ?";
        try (PreparedStatement getAuditPreparedStatement = connection.prepareStatement(sqlCommand)) {

            getAuditPreparedStatement.setInt(1,Integer.parseInt(containerId));
            getAuditPreparedStatement.setInt(2,Integer.parseInt(cargoManifestId));
            try (ResultSet auditPreparedResultSet = getAuditPreparedStatement.executeQuery()) {
                while (auditPreparedResultSet.next()) {
                    System.out.println(auditPreparedResultSet.getInt(1)+" | "+auditPreparedResultSet.getString(2)+" | "+auditPreparedResultSet.getString(3));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void registAuditTrail(String user, int containerId, int cargoManifestId, String operationType){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String date = LocalDateTime.now().format(formatter);
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(RolesUI.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        Connection connection = databaseConnection.getConnection();
        String sqlCommand = "INSERT INTO \"audit_trails\"(\"user_id\",\"container_id\",\"cargo_manifest_id\",\"date\",\"operation_type\") VALUES (?,?,?,?,?)";
        try (PreparedStatement getAuditPreparedStatement = connection.prepareStatement(sqlCommand)) {

            getAuditPreparedStatement.setInt(1,Integer.parseInt(user));
            getAuditPreparedStatement.setInt(2,containerId);
            getAuditPreparedStatement.setInt(3,cargoManifestId);
            getAuditPreparedStatement.setString(4,date);
            getAuditPreparedStatement.setString(5,operationType);
            getAuditPreparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void trafficManager(String userId) throws IOException, SQLException, InterruptedException {
        System.out.println(userId);
        String option = "";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println(INITIALOPTIONPHRASE);
            System.out.println("1) Imports");
            System.out.println("2) I wish to colour the map using as few colours as possible");
            System.out.println("3) I wish to know which places (cities or ports) are closest to all other places (closeness places)");
            System.out.println("4) I want to have a system that ensures that the number of containers in a manifest does not exceed the ship's available capacity");
            System.out.println("5) I do not allow a cargo manifest for a particular ship to be registered in the system on a date when the ship is already occupied. ");
            System.out.println("6) Insert containers in a cargo manifest");
            System.out.println("7) Delete containers in a cargo manifest");
            System.out.println("8) [US401] I wish to know which ports are more critical in this freight network");
            System.out.println("9) [US402] I wish to know the shortest path between two locals");
            System.out.println("10) [US403] I wish to know the most efficient circuit that starts from a source location and visits the greatest number of other locations once, returning to the starting location and with the shortest total distance");
            System.out.println(FINALOPTIONPHRASE);
            option = read.readLine();
            switch (option){
                case "1":
                    importMenu();
                break;
                case "2":
                    //[US302]

                    pmg.fillMatrixGraph(3,cs.getCountryList(),ps.getPortList(),sds.getSeaDistArrayList(),bs.toMap(bs.getBorderArray(),cs.getCountryArray()));
                    colorMapUI.run();
                break;
                case "3":
                    //[US303]
                break;
                case "4":

                    break;
                case "6":
                    System.out.println("Insert the container id:");
                    String code = read.readLine();
                    System.out.println("Insert the cargo manifest id:");
                    String cargoManifest = read.readLine();
                    registAuditTrail(userId,Integer.parseInt(code),Integer.parseInt(cargoManifest),"Insert");
                break;
                case "7":
                    System.out.println("Insert the container id:");
                    String codec = read.readLine();
                    System.out.println("Insert the cargo manifest id:");
                    String cargoManifestC = read.readLine();
                    registAuditTrail(userId,Integer.parseInt(codec),Integer.parseInt(cargoManifestC),"Delete");
                break;
                case "8":
                    pmg.fillMatrixGraph(6,cs.getCountryList(),ps.getPortList(),sds.getSeaDistArrayList(),bs.toMap(bs.getBorderArray(),cs.getCountryArray()));
                    System.out.println("Choose the number of the Critical Ports:");
                    int critical = Integer.parseInt(read.readLine());
                    System.out.println(critical);
                    System.out.println(cent.getNCentralPorts(pmg.getCompleteMap(),critical));
                    TimeUnit.SECONDS.sleep(10);
                    break;
                case "9":
                    //[US402]
                    shortPathMenu();
                    break;
                case "10":
                    //[US403]
                    pmg.fillMatrixGraph(6,cs.getCountryList(),ps.getPortList(),sds.getSeaDistArrayList(),bs.toMap(bs.getBorderArray(),cs.getCountryArray()));
                    Circuit.mostEfficientCircuit(pmg.getCompleteMap());

                    break;
                default:
                    System.out.println("The option doesn't exist");
                    break;
            }
        }
    }
    public void fleetManager(String userId) throws IOException, SQLException {
        Sprint4Store sp= new Sprint4Store();
        System.out.println(userId);
        String option = "";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println(INITIALOPTIONPHRASE);
            System.out.println("1) [US404] I want to know the number of days each ship has been idle since the beginning of the current year");
            System.out.println("2) [US405] I want to know the average occupancy rate per manifest of given ship during a given period.");
            System.out.println("3) [US406] I want to know which ship voyages had an occupancy rate below a certain threshold");
            System.out.println(FINALOPTIONPHRASE);
            option = read.readLine();
            switch (option){
                case "1":
                    sp.idleTimeShip();
                    //[US404]
                    break;
                case "2":
                    //[US405]
                    String shipId;
                    do {
                        System.out.println("Insert first date:");
                        String date1= read.readLine();
                        System.out.println("Insert second date:");
                        String date2= read.readLine();
                        System.out.println("Insert ship id:");
                         shipId= read.readLine();
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            sp.funcAvgOccuRate(LocalDate.parse(date1, formatter),LocalDate.parse(date2, formatter),Integer.parseInt(shipId));
                        }catch (NumberFormatException ex){
                            System.out.println("Invalid number");
                            shipId="no";
                        }
                    }while(shipId.equals("no"));

                    break;
                case "3":
                    String choice;
                    do {
                        System.out.println("Insert threshold");
                        choice= read.readLine();
                        try {
                            sp.funcAvgOcupShipThreshold(Integer.parseInt(choice));
                        }catch (NumberFormatException ex){
                            System.out.println("Invalid number");
                            choice="no";
                        }
                    }while(choice.equals("no"));

                    break;
                default:
                    System.out.println("The option doens't exist");
                    break;
            }
        }
    }
    public void warehouseMtaff(String userId) throws IOException {
        System.out.println(userId);
        noOptionMenu();
    }
    public void warehouseManager(String userId) throws IOException {
        System.out.println(userId);
        noOptionMenu();
    }
    public void portStaff(String userId) throws IOException {
        System.out.println(userId);
        String option= "";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println(INITIALOPTIONPHRASE);
            System.out.println("1) I wish to fill a statically reserved matrix in memory with each container's ID in its respective place");
            System.out.println("2) I wish to know the total number of free/occupied slots in the transport vehicle.");
            System.out.println("3) I wish to know if a container is there or not.");
            System.out.println("4) [US409] I wish to fill a dynamically reserved array in memory with all the container's information in its respective place.");
            System.out.println(FINALOPTIONPHRASE);
            option = read.readLine();
            switch (option){
                case "1":
                    String cargoId= read.readLine();
                    fillMatrix(Integer.parseInt(cargoId));
                    break;
                case "2":
                    //[US314]
                    break;
                case "3":
                    //[US315]
                    break;
                case "4":
                    //[US409]
                    break;
                default:
                    System.out.println("The option doens't exist");
                    break;
            }
        }
    }
    private void fillMatrix(int cargoId){
        ArrayList<String> tempArray = new ArrayList<>();
        DataBaseConnection databaseConnection=null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        Connection connection = databaseConnection.getConnection();
        String sqlCommand = "Select \"container_id\",\"container_position_x\",\"container_position_y\",\"container_position_z\" from \"cargo_manifest_container\" INNER JOIN \"registo_container\" using (\"registo_id\") WHERE \"cargo_manifesto_id\"=?";
        try (PreparedStatement getPortPreparedStatement = connection.prepareStatement(sqlCommand)) {
            getPortPreparedStatement.setInt(1,cargoId);
            try (ResultSet portPreparedResultSet = getPortPreparedStatement.executeQuery()) {

                while (portPreparedResultSet.next()) {
                    String tempS = portPreparedResultSet.getInt(2) +"-"+
                            portPreparedResultSet.getInt(3) +"-"+
                            portPreparedResultSet.getInt(4) +":"+
                            portPreparedResultSet.getInt(1) +"\n";
                    tempArray.add(tempS);
                }
            }
        } catch (SQLException throwables) {
        }
            try {
                FileWriter myWriter = new FileWriter("file1.txt");
                for (String s: tempArray) {
                    myWriter.write(s);
                }

                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

    }
    public void portManager(String userId) throws IOException, SQLException {
        System.out.println(userId);
        Sprint4Store sp= new Sprint4Store();
        String option="";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println(INITIALOPTIONPHRASE);
            System.out.println("1) I want to know the occupancy rate of each warehouse and an estimate of the containers leaving the warehouse during the next 30 days. ");
            System.out.println("2) I intend to get a warning whenever I issue a cargo manifest destined for a warehouse whose available capacity is insufficient to accommodate the new manifest");
            System.out.println("3) I intend to have a map of the occupation of the existing resources in the port during a given month. ");
            System.out.println("4)[US 407] I intend to generate, a week in advance, the loading and unloading map. ");
            System.out.println(FINALOPTIONPHRASE);
            option = read.readLine();
            switch (option){
                case "1":
                    //[US306]
                    try{
                        occupancyRate(userId);
                    } catch (SQLException throwables) {
                        System.out.println("erro");
                    }
                    break;
                case "2":

                    break;
                case "3":

                    break;
                    case "4":
                        String choice;
                        do {
                            System.out.println("Insert port:");
                            choice= read.readLine();
                            try {
                                sp.weaklyOperationMap(Integer.parseInt(choice));
                            }catch (NumberFormatException ex){
                                System.out.println("Invalid number");
                                choice="no";
                            }
                        }while(choice.equals("no"));
                    break;
                default:
                    System.out.println("The option doens't exist");
                    break;
            }
        }
    }
    private void occupancyRate(String userId) throws SQLException {
        System.out.println(userId);
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        Connection connection = databaseConnection.getConnection();
        String sqlCommand="select occunpacy_rate(?) from dual";

        PreparedStatement preparedStatement =
                connection.prepareStatement(sqlCommand);
        preparedStatement.setInt(1,Integer.parseInt(userId));
        preparedStatement.executeUpdate();
    }

        public void shipChiefElectricalEngineer(String userId) throws IOException {
            System.out.println(userId);
            String option = "";
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            double[][] areas = new double[][]{{24.0, 20.0, 24.0}, {14.0, 10.0, 14.0}, {14.0, 10.0, 14.0}, {24.0, 20.0, 24.0}};
            double[][] areas2 = new double[][]{{24.0, 20.0,20.0,20.0, 24.0}, {14.0, 10.0,10.0,10.0, 14.0},{14.0, 10.0,10.0,10.0, 14.0}, {24.0, 20.0,20.0,20.0, 24.0}};
            List<Double> time = new ArrayList<>();
            List<Double> temp = new ArrayList<>();
            Double resistivityC = 1.38;
            Double resistivityS = 0.96;
            se = new SupplyEnergy();
            while (!option.equals("0")){
                System.out.println(INITIALOPTIONPHRASE);
                System.out.println("1) Sprint 1. ");
                System.out.println("2) [412] we intend to know how much energy to supply, for each container, in a determined trip, with an exterior temperature of 20 ??C, and a travel time of 2h30");
                System.out.println("3) [413] the objective is to know the total energy to be supplied to the set of containers in a certain established trip, assuming that all the containers have the same behaviour.");
                System.out.println("4) [414] you want to know how much energy to supply to the container cargo, in a voyage (or route), depending on the position of the containers on the ship. Admitting that the interior containers.");
                System.out.println("5) [415] I need to know how many auxiliary power equipment are needed for the voyage, knowing that each one supplies a maximum of 75 KW.");
                System.out.println("6) [416] I intend to submit a summary document, with the following items.");
                System.out.println(FINALOPTIONPHRASE);
                option = read.readLine();
                switch (option){
                    case "1":
                        physicsMenu();
                        break;
                    case "2":
                        //[412]
                        temp.clear();
                        time.clear();
                        temp.add(20.0);
                        temp.add(15.0);
                        time.add(0.836667);
                        time.add(0.836667);
                        System.out.println("The energy supllied to each container at 7 degrees is " + String.format("%.2f", se.calcTripEnergy(temp, time, resistivityS, 7.0)) + " J");
                        System.out.println("The energy supllied to each container at -5 degrees is " + String.format("%.2f", se.calcTripEnergy(temp, time, resistivityC, -5.0)) + " J");
                        break;
                    case "3":
                        //[413]
                        temp.clear();
                        time.clear();
                        temp.add(20.0);
                        temp.add(15.0);
                        time.add(0.836667);
                        time.add(0.836667);
                        System.out.println("The journey time is: " + se.journetTime(time) + " H");
                        System.out.println("The temperature of the sections are: " + temp);
                        System.out.println("The energy supllied to each container at 7 degrees is " + String.format("%.2f", se.calcContainersTripEnergy(temp, time, resistivityS, 7.0,100)) + " J");
                        System.out.println("The energy supllied to each container at -5 degrees is " + String.format("%.2f", se.calcContainersTripEnergy(temp, time, resistivityC, -5.0,70)) + " J");
                        break;
                    case "4":
                        //[414]
                        temp.clear();
                        time.clear();
                        temp.add(20.0);
                        temp.add(15.0);
                        time.add(0.836667);
                        time.add(0.836667);
                        System.out.println("The journey time is: " + se.journetTime(time) + " H");
                        System.out.println("The temperature of the sections are: " + temp);
                        System.out.println("The energy supllied to each container at 7 degrees is " + String.format("%.2f", se.calcTotalEnergy(3, areas, temp, time, resistivityS, 7.0)) + " J");
                        System.out.println("The energy supllied to each container at -5 degrees is " + String.format("%.2f", se.calcTotalEnergy(3, areas, temp, time, resistivityC, -5.0)) + " J");
                        break;
                    case "5":
                        //[415]
                        temp.clear();
                        time.clear();
                        temp.add(20.0);
                        temp.add(15.0);
                        time.add(0.836667);
                        time.add(0.836667);
                        System.out.println("The journey time is: " + se.journetTime(time) + " H");
                        System.out.println("The temperature of the sections are: " + temp);
                        System.out.println("Necessery generatores: "+se.numOfGen(temp,time,areas2,2));
                        break;
                    case "6":
                        //[416]
                        File file = new File("Procedimento2_grupo1_2DI.pdf");
                        Desktop desk= Desktop.getDesktop();
                        if(file.exists()){
                            desk.open(file);
                        }
                        break;
                    default:
                        System.out.println("The option doens't exist");
                        break;
                }
            }

    }
    private static void physicsMenu() throws IOException {
        String option = "";
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
            case "4":
                File file = new File("ContainerSummary.pdf");
                Desktop desk= Desktop.getDesktop();
                if(file.exists()){
                    desk.open(file);
                }
                break;
            case "0":
                break;
            default:
                System.out.println("The option doens't exist");
                break;
        }
    }
    public void truckDriver(String userId) throws IOException {
        System.out.println(userId);

        noOptionMenu();
    }


    public void importMenu() throws IOException, SQLException {
        String option = "";
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
        System.out.println("3) Import Countries");
        System.out.println("4) Import Borders");
        System.out.println("5) Import SeaDist's");
        System.out.println(FINALOPTIONPHRASE);
        option = read.readLine();
        switch (option) {
            case "1":
                ss.uploadShipsToDB(databaseConnection);
                break;
            case "2":
                ps.uploadPortstoDatabase(databaseConnection);
                break;
            case "3":
                cs.uploadCountriestoDatabase(databaseConnection);
                break;
            case "4":
                bs.uploadBorderstoDatabase(databaseConnection);
                break;
            case "5":
                sds.uploadSeadistToDatabase(databaseConnection);
                break;
            case "0":
                System.out.println("bye");
                break;
            default:
                System.out.println("The option doens't exist");
                break;
        }
    }

    public void shortPathMenu() throws IOException, InterruptedException {
        String option = "";
        LinkedList<Position> s = new LinkedList<>();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        pmg.fillMatrixGraph(6, cs.getCountryList(), ps.getPortList(), sds.getSeaDistArrayList(), bs.toMap(bs.getBorderArray(), cs.getCountryArray()));
        System.out.println(INITIALOPTIONPHRASE);
        System.out.println("1) Land path");
        System.out.println("2) Maritime path");
        System.out.println("3) Land or sea path");
        option = read.readLine();
            switch (option) {
                case "1":
                    Graph<Position, Double> land = pmg.getLandMap();
                    System.out.println("First location:");
                    String string = read.readLine();
                    System.out.println("Secound location:");
                    String string1 = read.readLine();
                    int keyp1 = -1;
                    int keyp2 = -1;
                    for (Position p : land.vertices()) {
                        if (p.getName().equalsIgnoreCase(string) || p.getCountryName().equalsIgnoreCase(string))
                            keyp1 = land.key(p);
                        if (p.getName().equalsIgnoreCase(string1) || p.getCountryName().equalsIgnoreCase(string1))
                            keyp2 = land.key(p);
                    }
                    if (keyp1 == -1)
                        System.out.println(string + " doesn't exist in the graph");
                    if (keyp2 == -1)
                        System.out.println(string1 + " doesn't exist in the graph");
                    Algorithms.shortestPath(land, land.vertex(keyp1), land.vertex(keyp2), Double::compare, Double::sum, 0.0, s);
                    if (s.size() == 0) {
                        System.out.println("The two locations don't have a land connenction!");
                    } else {
                        System.out.println(s);
                    }
                    TimeUnit.SECONDS.sleep(10);
                    break;
                case "2":
                    Graph<Position, Double> sea = pmg.getSeaMap();
                    System.out.println("First location:");
                    string = read.readLine();
                    System.out.println("Secound location:");
                    string1 = read.readLine();
                    keyp1 = -1;
                    keyp2 = -1;
                    for (Position p : sea.vertices()) {
                        if (p.getName().equalsIgnoreCase(string) || p.getCountryName().equalsIgnoreCase(string))
                            keyp1 = sea.key(p);
                        if (p.getName().equalsIgnoreCase(string1) || p.getCountryName().equalsIgnoreCase(string1))
                            keyp2 = sea.key(p);
                    }
                    if (keyp1 == -1)
                        System.out.println(string + " doesn't exist in the graph");
                    if (keyp2 == -1)
                        System.out.println(string1 + " doesn't exist in the graph");
                    Algorithms.shortestPath(sea, sea.vertex(keyp1), sea.vertex(keyp2), Double::compare, Double::sum, 0.0, s);
                    if (s.size() == 0) {
                        System.out.println("The two locations don't have a land connenction!");
                    } else {
                        System.out.println(s);
                    }
                    TimeUnit.SECONDS.sleep(10);
                    break;
                case "3":
                    System.out.println("First location:");
                    string = read.readLine();
                    System.out.println("Secound location:");
                    string1 = read.readLine();
                    keyp1 = -1;
                    keyp2 = -1;
                    for (Position p : pmg.getCompleteMap().vertices()) {
                        if (p.getName().equalsIgnoreCase(string) || p.getCountryName().equalsIgnoreCase(string))
                            keyp1 = pmg.getCompleteMap().key(p);
                        if (p.getName().equalsIgnoreCase(string1) || p.getCountryName().equalsIgnoreCase(string1))
                            keyp2 = pmg.getCompleteMap().key(p);
                    }
                    if (keyp1 == -1)
                        System.out.println(string + " doesn't exist in the graph");
                    if (keyp2 == -1)
                        System.out.println(string1 + " doesn't exist in the graph");
                    Algorithms.shortestPath(pmg.getCompleteMap(), pmg.getCompleteMap().vertex(keyp1), pmg.getLandMap().vertex(keyp2), Double::compare, Double::sum, 0.0, s);
                    if (s.size() == 0) {
                        System.out.println("The two locations don't have a land connenction!");
                    } else {
                        System.out.println(s);
                    }
                    TimeUnit.SECONDS.sleep(10);
                    break;
                case "0":
                    System.out.println("bye");
                    break;
                default:
                    System.out.println("The option doens't exist");
                    break;
            }

    }

    private void noOptionMenu() throws IOException {

        String option = "";
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println(INITIALOPTIONPHRASE);
            System.out.println(FINALOPTIONPHRASE);
            option = read.readLine();
        }
    }
}
