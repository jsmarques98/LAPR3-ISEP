package lapr.project.ui;

import lapr.project.data.ConnectionFactory;
import lapr.project.data.DataBaseConnection;
import lapr.project.data.ShipStore;
import lapr.project.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
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
    private static String USER_SESSION;
    private Main() {

    }

    /**
     * Application main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        loginMenu();
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

                    break;
                case "0":
                    System.out.println("bye");
                    break;
            }
        }
    }
    private static void loginMenu() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RolesUI rolesUI = new RolesUI();

        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Login");
        System.out.println("Email:");
        String user = read.readLine();
        System.out.println("Password:");
        String pass = read.readLine();
        String role = login(user, pass);
        if (role.compareTo("not found") == 0){
            System.out.println("Username or password incorret");
            loginMenu();
        }
        role = role.toLowerCase(Locale.ROOT);
        role =role.replace(" ","_");
        System.out.println(role);
        Method method = Class.forName("lapr.project.ui.RolesUI").getMethod(role,String.class);
        method.invoke(rolesUI,USER_SESSION);

    }
    private static String login(String user, String pass){
        String role = "not found";

        DataBaseConnection databaseConnection = null;

        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        Connection connection = databaseConnection.getConnection();
        String sqlCommand = "select \"role\".\"name\",\"user_id\" from \"role\" INNER JOIN \"user\" using(\"role_id\") WHERE \"email\" = ? AND \"pass\" = ?";
        try (PreparedStatement getLoginPreparedStatement = connection.prepareStatement(sqlCommand)) {

            getLoginPreparedStatement.setString(1,user);
            getLoginPreparedStatement.setString(2,pass);

            try (ResultSet loginPreparedResultSet = getLoginPreparedStatement.executeQuery()) {
                while (loginPreparedResultSet.next()) {
                    role = loginPreparedResultSet.getString(1);  //role
                    USER_SESSION = loginPreparedResultSet.getString(2);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return role;
    }



}


