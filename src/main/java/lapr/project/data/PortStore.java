package lapr.project.data;

import lapr.project.model.Port;
import lapr.project.model.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortStore  implements Persistable {

    //Atualizar atributos
    KDTreePort portTree = new KDTreePort();

    public PortStore(KDTreePort portTree){
        this.portTree = portTree;
    }


    public ArrayList<Port> getPortArrayList(){
        return this.portTree.getArray();
    }

    public List<Position> getPortList(){
        List<Position> temp = new ArrayList<>();
        temp.addAll(portTree.getArray());
        return temp;
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
                "insert into \"port_warehouse\" (\"port_warehouse_id\",\"name\",\"continent\", \"country\", \"type\", \"lat\", \"log\",\"capacity\") values (?, ?, ?,?,?,?,?,?)";

        executeInsertPortStatementOnDatabase(databaseConnection, port,
                sqlCommand);
    }
    private void executeInsertPortStatementOnDatabase(
            DataBaseConnection databaseConnection,
            Port port, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(sqlCommand);
        savePortPreparedStatement.setInt(1, port.getCode());
        savePortPreparedStatement.setString(2, port.getPorto());
        savePortPreparedStatement.setString(3, port.getContinent());
        savePortPreparedStatement.setString(4, port.getCountry());
        savePortPreparedStatement.setString(5, "port");
        savePortPreparedStatement.setDouble(6,port.getLat());
        savePortPreparedStatement.setDouble(7,port.getLon());
        savePortPreparedStatement.setDouble(8,3);
        savePortPreparedStatement.executeUpdate();
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
        savePortPreparedStatement.setString(3, port.getPorto());
        savePortPreparedStatement.setFloat(4,(float)port.getLat());
        savePortPreparedStatement.setFloat(5,(float) port.getLon());
        //atualizar a classe para adicionar atributos
        savePortPreparedStatement.executeUpdate();
    }

    private boolean isPortOnDatabase(DataBaseConnection databaseConnection,
                                     Port port) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isPortOnDatabase = false;

        String sqlCommand = "SELECT * FROM \"port_warehouse\" WHERE \"port_warehouse_id\" = ?";

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

        return isPortOnDatabase;
    }

    public boolean loadPortFromDatabase(DataBaseConnection databaseConnection) {
        boolean returnValue= false;
        Connection connection = databaseConnection.getConnection();
        ArrayList<Port> tempArray = new ArrayList<>();
        String sqlCommand = "Select * from \"port_warehouse\"";
        try (PreparedStatement getPortPreparedStatement = connection.prepareStatement(sqlCommand)) {

            try (ResultSet portPreparedResultSet = getPortPreparedStatement.executeQuery()) {

                while (portPreparedResultSet.next()) {
                    Port tempPort = new Port(portPreparedResultSet.getString(3), //continent
                            portPreparedResultSet.getString(4), //country
                            portPreparedResultSet.getInt(1), //id
                            portPreparedResultSet.getString(2), //port
                            portPreparedResultSet.getFloat(6), //lat
                            portPreparedResultSet.getFloat(7) //lon
                    );
                    tempArray.add(tempPort);

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return returnValue;
        }
        returnValue = true;
        portTree.insertPorts(tempArray);
        return returnValue;
    }

    public boolean uploadPortstoDatabase(DataBaseConnection databaseConnection){
        boolean returnValue = false;

        ArrayList<Port> ports = portTree.getArray();
        for (Port p: ports) {
            this.save(databaseConnection,p);
        }
        returnValue = true;
        return returnValue;
    }
    private void updatePortOnDatabase(DataBaseConnection databaseConnection,
                                      Port port) throws SQLException {
        String sqlCommand =
                "update \"port_warehouse\" set \"continent\" = ?, \"country\" = ?, \"name\" = ?, \"lat\" = ?, \"log\" = ?  where \"port_warehouse_id\" = ?";

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
            //TODO VER ISTO
            String sqlCommand = "delete from \"port_warehouse\" where \"port_warehouse_id\" = ?";

            //eliminar as outras ocurrencias do port_warehouse
            try (PreparedStatement deletePortPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deletePortPreparedStatement.setInt(1, port.getCode());
                deletePortPreparedStatement.executeUpdate();
            }

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
