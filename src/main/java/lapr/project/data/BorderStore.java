package lapr.project.data;

import lapr.project.model.Border;
import lapr.project.model.Port;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BorderStore {

    public boolean save(DataBaseConnection databaseConnection, Object object) {
        Border border = (Border) object;
        boolean returnValue = false;

        try {
            saveBorderToDatabase(databaseConnection, border);

            returnValue = true;

        } catch (SQLException ex) {
            Logger.getLogger(BorderStore.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }
    private void saveBorderToDatabase(DataBaseConnection dataBaseConnection, Border border) throws SQLException {
        if (!isBorderOnDatabase(dataBaseConnection, border)) {
            insertBorderOnDatabase(dataBaseConnection, border);
        }
    }

    private void insertBorderOnDatabase(DataBaseConnection databaseConnection,
                                        Border border) throws SQLException {
        String sqlCommand =
                "insert into \"border\" (\"country1\",\"country2\") values (?, ?)";

        executeInsertBorderStatementOnDatabase(databaseConnection, border,
                sqlCommand);
    }
    private void executeInsertBorderStatementOnDatabase(
            DataBaseConnection databaseConnection,
            Border border, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(sqlCommand);
        savePortPreparedStatement.setString(1, border.getCountry1());
        savePortPreparedStatement.setString(2, border.getCountry2());
        savePortPreparedStatement.executeUpdate();
    }

    private boolean isBorderOnDatabase(DataBaseConnection databaseConnection,
                                       Border border) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isBorderOnDatabase = false;

        String sqlCommand = "SELECT * FROM \"border\" WHERE \"country1\" = ? AND \"country2\" = ?";

        PreparedStatement getPortPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getPortPreparedStatement.setString(1,border.getCountry1());
        getPortPreparedStatement.setString(2,border.getCountry2());

        try (ResultSet bordersResultSet = getPortPreparedStatement.executeQuery()) {

            if (bordersResultSet.next()) {
                isBorderOnDatabase = true;
            } else {


                isBorderOnDatabase = false;
            }
        }

        return isBorderOnDatabase;
    }
    //TODO INSERIR NO GRAPH
    public boolean loadBorderFromDatabase(DataBaseConnection databaseConnection) {
        boolean returnValue= false;
        Connection connection = databaseConnection.getConnection();
        ArrayList<Border> tempArray = new ArrayList<>();
        String sqlCommand = "Select \"country1\",\"country2\" from \"border\"";
        try (PreparedStatement getBorderPreparedStatement = connection.prepareStatement(sqlCommand)) {

            try (ResultSet borderPreparedResultSet = getBorderPreparedStatement.executeQuery()) {

                while (borderPreparedResultSet.next()) {
                    Border tempBorder = new Border(borderPreparedResultSet.getString(1), //country1
                            borderPreparedResultSet.getString(2)//country2
                    );
                    tempArray.add(tempBorder);

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return returnValue;
        }
        returnValue = true;
        //portTree.insertPorts(tempArray);
        return returnValue;
    }

    //TODO CARREGAR DO GRAPH
    public boolean uploadBorderstoDatabase(DataBaseConnection databaseConnection){
        boolean returnValue = false;

        ArrayList<Border> borders = null;
        for (Border b: borders) {
            this.save(databaseConnection,b);
        }
        returnValue = true;
        return returnValue;
    }
    public boolean uploadBorderstoDatabase(DataBaseConnection databaseConnection,ArrayList<Border> borders){
        boolean returnValue = false;

        for (Border b: borders) {
            this.save(databaseConnection,b);
        }
        returnValue = true;
        return returnValue;
    }
    public boolean delete(DataBaseConnection databaseConnection,
                          Object object) {
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        Border border = (Border) object;

        try {
            //TODO VER ISTO
            String sqlCommand = "delete from \"border\" where \"country1\" = ? AND \"country2\" = ?";
            try (PreparedStatement deleteBorderPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteBorderPreparedStatement.setString(1, border.getCountry1());
                deleteBorderPreparedStatement.setString(2, border.getCountry2());
                deleteBorderPreparedStatement.executeUpdate();
            }

            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(BorderStore.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);

        }

        return returnValue;
    }

}
