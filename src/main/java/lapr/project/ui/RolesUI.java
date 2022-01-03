package lapr.project.ui;

import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.store.MaterialStore;
import lapr.project.utils.CsvReader;
import lapr.project.utils.ThermalResistance;

import javax.xml.stream.Location;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RolesUI {

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

    public RolesUI(){
        bs = new BorderStore();
        cs = new CountryStore();
        KDTreePort portTree = new KDTreePort();
        ps = new PortStore(portTree);
        sds = new SeaDistStore();
        ss = new ShipStore();
    }


    public  void client(String user) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("1) I want to know the route of a specific container I am leasing");
            System.out.println("2) I want to know the current situation of a specific container being used to transport my goods");
            System.out.println("0) Leave");
            option = read.readLine();
            switch (option){
                case "1":
                    System.out.println("Insert container id:");
                    String container_id = read.readLine();
                    leasing_container(container_id);
                    break;
                case "2":
                    //mudar o x e y para ID
                    System.out.println("x to work");
                    System.out.println("y to break");
                    String container_id_ = read.readLine();
                    leasing_container(container_id_);
                    break;
            }
        }
    }
    private void leasing_container(String container_id){

    }
    public void ship_employe(String user_id) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("0) Leave");
            option = read.readLine();
        }
    }
    public void ship_capitan(String user_id) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("1) I want to have access to audit trails for a given container of a given cargo manifest");
            System.out.println("0) Leave");
            option = read.readLine();
            switch (option){
                case "1":
                    System.out.println("Insert container id:");
                    String container_id = read.readLine();
                    System.out.println("Insert cargo manifest id:");
                    String cargo_manifest = read.readLine();
                    audit_trails_log(container_id,cargo_manifest);
                    break;
            }
        }
    }
    private void audit_trails_log(String container_id, String cargo_manifest_id){
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

            getAuditPreparedStatement.setInt(1,Integer.parseInt(container_id));
            getAuditPreparedStatement.setInt(2,Integer.parseInt(cargo_manifest_id));
            try (ResultSet auditPreparedResultSet = getAuditPreparedStatement.executeQuery()) {
                while (auditPreparedResultSet.next()) {
                    System.out.println(auditPreparedResultSet.getInt(1)+" | "+auditPreparedResultSet.getString(2)+" | "+auditPreparedResultSet.getString(3));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void regist_audit_trail (String user, int container_id, int cargo_manifest_id, String operation_type){
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
            getAuditPreparedStatement.setInt(2,container_id);
            getAuditPreparedStatement.setInt(3,cargo_manifest_id);
            getAuditPreparedStatement.setString(4,date);
            getAuditPreparedStatement.setString(5,operation_type);
            getAuditPreparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void traffic_manager(String user_id) throws IOException, SQLException{
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("1) Imports");
            System.out.println("2) I wish to colour the map using as few colours as possible");
            System.out.println("3) I wish to know which places (cities or ports) are closest to all other places (closeness places)");
            System.out.println("4) I want to have a system that ensures that the number of containers in a manifest does not exceed the ship's available capacity");
            System.out.println("5) I do not allow a cargo manifest for a particular ship to be registered in the system on a date when the ship is already occupied. ");
            System.out.println("6) Insert containers in a cargo manifest");
            System.out.println("7) Delete containers in a cargo manifest");
            System.out.println("0) Leave");
            option = read.readLine();
            switch (option){
                case "1":
                    importMenu();
                break;
                case "2":
                    //[US302]

                    pmg.fillMatrixGraph(3,cs.getCountryList(),ps.getPortList(),sds.getSeaDistArrayList(),bs.toMap(bs.getBorderArray(),cs.getCountryArray()));
                    System.out.println(pmg.getCompleteMap());
                    System.out.println();
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
                    String cargo_manifest = read.readLine();
                    System.out.println("Insert the position x:");
                    String x = read.readLine();
                    System.out.println("Insert the position y:");
                    String y = read.readLine();
                    System.out.println("Insert the position z:");
                    String z = read.readLine();
                    System.out.println("Insert gross weigth:");
                    String weigth = read.readLine();
                    regist_audit_trail(user_id,Integer.parseInt(code),Integer.parseInt(cargo_manifest),"Insert");
                break;
                case "7":
                    System.out.println("Insert the container id:");
                    String code_ = read.readLine();
                    System.out.println("Insert the cargo manifest id:");
                    String cargo_manifest_ = read.readLine();
                    regist_audit_trail(user_id,Integer.parseInt(code_),Integer.parseInt(cargo_manifest_),"Delete");
                break;
                // 4 e 5 são triggers
            }
        }
    }

    private void delete_containers(String code,String cargo_manifest) throws SQLException {
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        String sqlCommand =
                "delete from \"cargo_manifest_container\" where \"registo_id\"=?   AND \"cargo_manifesto_id\"=?";
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(sqlCommand);
        savePortPreparedStatement.setInt(1, Integer.parseInt(code));
        savePortPreparedStatement.setInt(2, Integer.parseInt(cargo_manifest));
        savePortPreparedStatement.executeUpdate();
    }
    private void insert_containers(String code,String cargo_manifest, String x,String y,String z,String weigth) throws SQLException {
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        String sqlCommand =
                "insert into \"cargo_manifest_container\" (\"registo_id\",\"cargo_manifesto_id\",\"container_position_x\",\"container_position_y\",\"container_position_z\",\"container_gross_weigth\")" +
                        " values (?, ?,?,?,?,?)";
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(sqlCommand);
        savePortPreparedStatement.setInt(1, Integer.parseInt(code));
        savePortPreparedStatement.setInt(2, Integer.parseInt(cargo_manifest));
        savePortPreparedStatement.setInt(3, Integer.parseInt(x));
        savePortPreparedStatement.setInt(4, Integer.parseInt(y));
        savePortPreparedStatement.setInt(5, Integer.parseInt(z));
        savePortPreparedStatement.setFloat(6, Float.parseFloat(weigth));
        savePortPreparedStatement.executeUpdate();
    }
    public void fleet_manager(String user_id) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("0) Leave");
            option = read.readLine();
        }
    }
    public void warehouse_staff(String user_id) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("0) Leave");
            option = read.readLine();
        }
    }
    public void warehouse_manager(String user_id) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("0) Leave");
            option = read.readLine();
        }
    }
    public void port_staff(String user_id) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("1) I wish to fill a statically reserved matrix in memory with each container's ID in its respective place");
            System.out.println("2) I wish to know the total number of free/occupied slots in the transport vehicle.");
            System.out.println("3) I wish to know if a container is there or not.");
            System.out.println("0) Leave");
            option = read.readLine();
            switch (option){
                case "1":
                    String cargo_id= read.readLine();
                    fill_matrix(Integer.parseInt(cargo_id));
                    break;
                case "2":
                    //[US314]
                    break;
                case "3":
                    //[US315]
                    break;
            }
        }
    }
    private void fill_matrix(int cargo_id){
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
            getPortPreparedStatement.setInt(1,cargo_id);
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
        if(tempArray!=null){
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

    }
    public void port_manager(String user_id) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("1) I want to know the occupancy rate of each warehouse and an estimate of the containers leaving the warehouse during the next 30 days. ");
            System.out.println("2) I intend to get a warning whenever I issue a cargo manifest destined for a warehouse whose available capacity is insufficient to accommodate the new manifest");
            System.out.println("3) I intend to have a map of the occupation of the existing resources in the port during a given month. ");
            System.out.println("0) Leave");
            option = read.readLine();
            switch (option){
                case "1":
                    //[US306]
                    try{
                        occupancyRate(user_id);
                    } catch (SQLException throwables) {
                        System.out.println("erro");
                    }
                    break;
                case "2":
                    warehouse_capacity_limit();
                    break;
                case "3":
                    occupation_map();
                    break;
            }
        }
    }
    private void occupation_map(){}
    private void warehouse_capacity_limit(){
        //criar 2 bootstrap um de sucesso outro de insucesso
    }
    private void occupancyRate(String user_id) throws SQLException {
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
        preparedStatement.setInt(1,Integer.parseInt(user_id));
        preparedStatement.executeUpdate();
    }

        public void ship_chief_electrical_engineer(String user_id) throws IOException {
        physicsMenu();
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
            case "4":
                File file = new File("ContainerSummary.pdf");
                Desktop desk= Desktop.getDesktop();
                if(file.exists()){
                    desk.open(file);
                }
                break;
            case "0":
                break;
        }
    }
    public void truck_driver(String user_id) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("0) Leave");
            option = read.readLine();
        }
    }


    public void importMenu() throws IOException, SQLException {
        String option = new String();
        CsvReader csvReader;
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
        System.out.println("3) Import Countries");
        System.out.println("4) Import Borders");
        System.out.println("5) Import SeaDist's");
        System.out.println("0) Leave");
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
        }
    }

    private void loadBD() throws SQLException {
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        sds.loadSeadistFromDatabase(databaseConnection);
        bs.loadBorderFromDatabase(databaseConnection);
        cs.loadCountryFromDatabase(databaseConnection);
        ps.loadPortFromDatabase(databaseConnection);
        ss.loadFromDatabase(databaseConnection,shipList);
    }

}
