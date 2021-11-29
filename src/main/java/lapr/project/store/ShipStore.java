package lapr.project.store;

import lapr.project.data.DataBaseConnection;
import lapr.project.data.Persistable;
import lapr.project.model.Ship;
import lapr.project.model.ShipBST;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipStore implements Persistable {



    //Atualizar atributos
    private ShipBST shipBST = new ShipBST();

    public ShipStore(){
        shipBST.insert();
    }

    public String findShip(String s){
        Ship ship = shipBST.findShip(s);
        if(ship == null)
            return "Ship doesn't exist.";
        else
            return ship.toString1();
    }

    public boolean insert(){
        shipBST.insert();
        return true;
    }

   public boolean print(){shipBST.printTrees();
    return true;
    }

    public String shipSummary(String s){
        return shipBST.shipSummary(s);
    }

    @Override
    public boolean save(DataBaseConnection databaseConnection, Object object) {
        Ship ship = (Ship) object;
        boolean returnValue = false;

        try {
            saveShiptoDatabase(databaseConnection, ship);

            returnValue = true;

        } catch (SQLException ex) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }

    private void saveShiptoDatabase(DataBaseConnection dataBaseConnection, Ship ship) throws SQLException {
        if (isShipOnDatabase(dataBaseConnection, ship)) {
            updateShipOnDatabase(dataBaseConnection, ship);
        } else {
            insertShipOnDatabase(dataBaseConnection, ship);
        }
    }

    private void insertShipOnDatabase(DataBaseConnection databaseConnection,
                                        Ship ship) throws SQLException {
        String sqlCommand =
                "insert into ship(MMSI,CALL_SIGN, SHIP_IMO_ID, SHIP_NAME,NR_ENERGY_GENS,GEN_PO,LENGTH,WIDTH,CAPACITY,DRAFT,VESSEL_TYPE) values (?, ?, ?,?,?,?,?,?,?,?,?)";

        executeInsertShipStatementOnDatabase(databaseConnection, ship,
                sqlCommand);
    }
    private void executeInsertShipStatementOnDatabase(
            DataBaseConnection databaseConnection,
            Ship ship, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement saveShipPreparedStatement =
                connection.prepareStatement(sqlCommand);
        saveShipPreparedStatement.setInt(1, ship.getMmsi());
        saveShipPreparedStatement.setString(2, ship.getCallSign());
        saveShipPreparedStatement.setInt(3, ship.getImo());
        saveShipPreparedStatement.setString(4,ship.getVesselName());
        //atualizar a classe para adicionar atributos
        saveShipPreparedStatement.executeUpdate();
        connection.close();
    }

    private void executeUpdateShipStatementOnDatabase(
            DataBaseConnection databaseConnection,
            Ship ship, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement saveShipPreparedStatement =
                connection.prepareStatement(
                        sqlCommand);
        saveShipPreparedStatement.setInt(11, ship.getMmsi());
        saveShipPreparedStatement.setString(1, ship.getCallSign());
        saveShipPreparedStatement.setInt(2, ship.getImo());
        saveShipPreparedStatement.setString(3,ship.getVesselName());
        //atualizar a classe para adicionar atributos
        saveShipPreparedStatement.executeUpdate();
        connection.close();
    }

    private boolean isShipOnDatabase(DataBaseConnection databaseConnection,
                                     Ship ship) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isShipOnDatabase = false;

        String sqlCommand = "SELECT * FROM ship WHERE MMSI = ?";

        PreparedStatement getShipPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getShipPreparedStatement.setInt(1,ship.getMmsi());

        try (ResultSet shipsResultSet = getShipPreparedStatement.executeQuery()) {

            if (shipsResultSet.next()) {

                isShipOnDatabase = true;
            } else {


                isShipOnDatabase = false;
            }
        }
        connection.close();
        return isShipOnDatabase;
    }
    private void updateShipOnDatabase(DataBaseConnection databaseConnection,
                                        Ship ship) throws SQLException {
        String sqlCommand =
                "update ship set CALL_SIGN = ?, SHIP_IMO_ID = ?,SHIP_NAME = ?, NR_ENERGY_GENS = ?, GEN_PO = ?, LENGTH = ?, WIDTH = ?, CAPACITY = ?, DRAFT = ?, VESSEL_TYPE = ?   where MMSI = ?";

        executeUpdateShipStatementOnDatabase(databaseConnection, ship,
                sqlCommand);
    }
    @Override
    public boolean delete(DataBaseConnection databaseConnection,
                          Object object) {
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        Ship ship = (Ship) object;

        try {

            String sqlCommand;
            sqlCommand = "delete from ship_data where ship_id = ?";
            try (PreparedStatement deleteShipAddressesPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteShipAddressesPreparedStatement.setInt(1,
                        ship.getMmsi());
                deleteShipAddressesPreparedStatement.executeUpdate();
            }
            //eliminar as outras ocurrencias do ship
            sqlCommand = "delete from ship where MMSI = ?";
            try (PreparedStatement deleteClientPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteClientPreparedStatement.setInt(1, ship.getMmsi());
                deleteClientPreparedStatement.executeUpdate();
            }
            connection.close();
            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(Ship.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);
            returnValue = false;
        }

        return returnValue;
    }



}
