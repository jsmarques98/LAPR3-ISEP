package lapr.project.store;

import lapr.project.data.DataBaseConnection;
import lapr.project.data.Persistable;
import lapr.project.model.Port;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortStore  implements Persistable {

    //Atualizar atributos

    public PortStore(){

    }


    @Override
    public boolean save(DataBaseConnection databaseConnection, Object object) {
        Port port = (Port) object;
        boolean returnValue = false;

        try {
            savePorttoDatabase(databaseConnection, port);

            returnValue = true;

        } catch (SQLException ex) {
            Logger.getLogger(PortStore.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }

    private void savePorttoDatabase(DataBaseConnection dataBaseConnection, Port port) throws SQLException {
        if (isPortOnDatabase(dataBaseConnection, port)) {
            updatePortOnDatabase(dataBaseConnection, port);
        } else {
            insertPortOnDatabase(dataBaseConnection, port);
        }
    }

    private void insertPortOnDatabase(DataBaseConnection databaseConnection,
                                      Port port) throws SQLException {
        String sqlCommand =
                "insert into port_warehouse(continent, country, code, port, lat, lon) values (?, ?, ?,?,?,?)";

        executeInsertPortStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }
    private void executeInsertPortStatementOnDatabase(
            DataBaseConnection databaseConnection,
            Port port, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(sqlCommand);
        savePortPreparedStatement.setString(1, port.getContinent());
        savePortPreparedStatement.setString(2, port.getCountry());
        savePortPreparedStatement.setInt(3, port.getCode());
        savePortPreparedStatement.setString(4, port.getPort());
        savePortPreparedStatement.setDouble(5,port.getLat());
        savePortPreparedStatement.setDouble(6,port.getLon());
        //atualizar a classe para adicionar atributos
        savePortPreparedStatement.executeUpdate();
        connection.close();
    }

    private void executeUpdatePortStatementOnDatabase(
            DataBaseConnection databaseConnection,
            Port port, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(
                        sqlCommand);
        savePortPreparedStatement.setString(1, port.getContinent());
        savePortPreparedStatement.setString(2, port.getCountry());
        savePortPreparedStatement.setInt(6, port.getCode());
        savePortPreparedStatement.setString(3, port.getPort());
        savePortPreparedStatement.setDouble(4,port.getLat());
        savePortPreparedStatement.setDouble(5,port.getLon());
        //atualizar a classe para adicionar atributos
        savePortPreparedStatement.executeUpdate();
        connection.close();
    }

    private boolean isPortOnDatabase(DataBaseConnection databaseConnection,
                                     Port port) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isPortOnDatabase = false;

        String sqlCommand = "SELECT * FROM port_warehouse WHERE code = ?";

        PreparedStatement getPortPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getPortPreparedStatement.setInt(1,port.getCode());

        try (ResultSet portsResultSet = getPortPreparedStatement.executeQuery()) {

            if (portsResultSet.next()) {

                isPortOnDatabase = true;
            } else {


                isPortOnDatabase = false;
            }
        }
        connection.close();
        return isPortOnDatabase;
    }
    private void updatePortOnDatabase(DataBaseConnection databaseConnection,
                                      Port port) throws SQLException {
        String sqlCommand =
                "update port_warehouse set continent = ?, country = ?, port = ?, lat = ?, lon = ?  where code = ?";

        executeUpdatePortStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }
    @Override
    public boolean delete(DataBaseConnection databaseConnection,
                          Object object) {
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        Port port = (Port) object;

        try {

            String sqlCommand;

            //eliminar as outras ocurrencias do port_warehouse
            sqlCommand = "delete from port_warehouse where port_warehouse_id = ?";
            try (PreparedStatement deletePortPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deletePortPreparedStatement.setInt(1, port.getCode());
                deletePortPreparedStatement.executeUpdate();
            }
            connection.close();
            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(PortStore.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);

        }

        return returnValue;
    }


}
