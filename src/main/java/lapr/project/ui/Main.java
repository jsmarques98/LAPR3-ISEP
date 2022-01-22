package lapr.project.ui;

import lapr.project.data.*;
import lapr.project.model.*;

import java.io.*;
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
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        loginMenu(databaseConnection);
    }
    private static void loginMenu(DataBaseConnection databaseConnection) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        RolesUI rolesUI = new RolesUI();

        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Login");
        System.out.println("Email:");
        String user = read.readLine();
        System.out.println("Password:");
        String pass = read.readLine();
        String role = login(databaseConnection,user, pass);
        if (role.compareTo("not found") == 0){
            System.out.println("Username or password incorret");
            loginMenu(databaseConnection);
        }

        role=role.replaceFirst(role.substring(0,1), role.substring(0,1).toLowerCase(Locale.ROOT));
        System.out.println(role.substring(0,1));
        role=role.replace(" ","");

        Method method = Class.forName("lapr.project.ui.RolesUI").getMethod(role,String.class);
        method.invoke(rolesUI,USER_SESSION);

    }
    private static String login(DataBaseConnection databaseConnection,String user, String pass) throws SQLException {
        String role = "not found";


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


