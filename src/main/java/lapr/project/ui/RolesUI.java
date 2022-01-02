package lapr.project.ui;

import lapr.project.data.*;
import lapr.project.model.KDTreePort;
import lapr.project.model.Material;
import lapr.project.store.MaterialStore;
import lapr.project.utils.CsvReader;
import lapr.project.utils.ThermalResistance;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RolesUI {

    public static void client(String user) throws IOException {
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")){
            System.out.println("Please make your selection");
            System.out.println("1) I want to know the route of a specific container I am leasing");
            System.out.println("2) I want to know the current situation of a specific container being used to transport my goods");
            System.out.println("0) Leave");
            option = read.readLine();
        }
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
            System.out.println("0) Leave");
            option = read.readLine();
            switch (option){
                case "1":
                    importMenu();
                break;
                case "2":
                    //[US302]
                break;
                case "3":
                    //[US303]
                break;
                // 4 e 5 são triggers
            }
        }
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
                    //[US313]
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
                    break;
                case "2":
                    //[US307]
                    break;
                case "3":
                    //[US310]
                    break;
            }
        }
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
        ShipStore st = new ShipStore();
        KDTreePort portTree = new KDTreePort();
        String option = new String();
        PortStore pt = new PortStore(portTree);
        CountryStore cs = new CountryStore();
        BorderStore bs = new BorderStore();
        SeaDistStore sds = new SeaDistStore();
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
                st.uploadShipsToDB(databaseConnection);
                st.loadFromDatabase(databaseConnection);
                break;
            case "2":
                pt.uploadPortstoDatabase(databaseConnection);
                pt.loadPortFromDatabase(databaseConnection);
                break;
            case "3":
                cs.uploadCountriestoDatabase(databaseConnection, CsvReader.readCountry("src/main/java/lapr/project/data/countries.csv"));
                cs.loadCountryFromDatabase(databaseConnection);
                break;
            case "4":
                bs.uploadBorderstoDatabase(databaseConnection, CsvReader.readBorder("src/main/java/lapr/project/data/borders.csv"));
                bs.loadBorderFromDatabase(databaseConnection);
                break;
            case "5":
                sds.uploadSeadistToDatabase(databaseConnection, CsvReader.readSeaDist("src/main/java/lapr/project/data/seadists.csv"));
                sds.loadSeadistFromDatabase(databaseConnection);
                break;
            case "0":
                System.out.println("bye");
                break;
        }
    }


}