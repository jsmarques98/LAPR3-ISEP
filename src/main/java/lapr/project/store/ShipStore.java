package lapr.project.store;

import lapr.project.data.DataBaseConnection;
import lapr.project.data.Persistable;
import lapr.project.model.DynamicShip;
import lapr.project.model.Ship;
import lapr.project.model.ShipBST;
import lapr.project.model.ShipMmsi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShipStore implements Persistable {


    //Atualizar atributos
    private ShipBST shipBST = new ShipBST();

    public ShipStore() {
        shipBST.insert();
    }

    public String findShip(String s) {
        Ship ship = shipBST.findShip(s);
        if (ship == null)
            return "Ship doesn't exist.";
        else
            return ship.toString1();
    }

    public boolean insert() {
        shipBST.insert();
        return true;
    }

    public boolean print() {
        shipBST.printTrees();
        return true;
    }

    public String shipSummary(String s) {
        return shipBST.shipSummary(s);
    }

    public boolean uploadShipsToDB(DataBaseConnection dataBaseConnection){
        boolean returnValue = false;
        Iterable<ShipMmsi> ships = shipBST.getTreeForDB();
        for (Ship s: ships) {
            this.save(dataBaseConnection,s);
        }

        return returnValue;
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
                "insert into \"ship\"(\"mmsi\",\"call_sign\", \"ship_imo_id\", \"ship_name\",\"nr_energy_gens\",\"gen_po\",\"length\",\"width\",\"capacity\",\"draft\",\"vessel_type\") values (?, ?, ?,?,?,?,?,?,?,?,?)";

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
        saveShipPreparedStatement.setString(4, ship.getVesselName());
        saveShipPreparedStatement.setInt(5, ship.getNr_gen());//nr_gen
        saveShipPreparedStatement.setFloat(6, (float) ship.getGen_power());//gen_po
        saveShipPreparedStatement.setFloat(7, (float)ship.getLength());
        saveShipPreparedStatement.setFloat(8, (float)ship.getWidth());
        saveShipPreparedStatement.setFloat(9, (float)ship.getCapacity());//cap
        saveShipPreparedStatement.setFloat(10, (float)ship.getDraft());
        saveShipPreparedStatement.setInt(11, ship.getVesselType());
        insertShipData(databaseConnection,ship.getDynamicShip(),ship.getMmsi());
        //TODO METER CENAS NO SHIP DATA
        //atualizar a classe para adicionar atributos
        saveShipPreparedStatement.executeUpdate();
    }
    private void insertShipData(DataBaseConnection databaseConnection,ArrayList<DynamicShip> array,int ship_id) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        String sqlCommand = "INSERT INTO \"ship_data\" VALUES (?,TO_DATE(?, 'yyyy-mm-dd hh24:mi'),?,?,?,?,?,?,?)";

        for (DynamicShip ds: array) {
            if(ds.getCog()>0 && ds.getCog()<359 && ds.getSog()>=0 ) {
                PreparedStatement saveShipDataPreparedStatement =
                        connection.prepareStatement(sqlCommand);
                saveShipDataPreparedStatement.setInt(1, ship_id);
                saveShipDataPreparedStatement.setString(2, ds.getBaseDateTime().toString().replace('T', ' '));
                saveShipDataPreparedStatement.setFloat(3, (float) ds.getLat());
                saveShipDataPreparedStatement.setFloat(4, (float) ds.getLon());
                saveShipDataPreparedStatement.setFloat(5, (float) ds.getSog());
                saveShipDataPreparedStatement.setFloat(6, (float) ds.getCog());//gen_po
                saveShipDataPreparedStatement.setFloat(7, (float) ds.getHeading());
                saveShipDataPreparedStatement.setInt(8, (int) ds.getCargo());
                saveShipDataPreparedStatement.setString(9, ds.getTranscrieverClass() + "");//cap
                saveShipDataPreparedStatement.executeUpdate();
            }
        }



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
        saveShipPreparedStatement.setString(3, ship.getVesselName());
        saveShipPreparedStatement.setInt(4, ship.getNr_gen());//nr_gen
        saveShipPreparedStatement.setFloat(5, (float) ship.getGen_power());//gen_po
        saveShipPreparedStatement.setFloat(6, (float)ship.getLength());
        saveShipPreparedStatement.setFloat(7, (float)ship.getWidth());
        saveShipPreparedStatement.setFloat(8, (float)ship.getCapacity());//cap
        saveShipPreparedStatement.setFloat(9, (float)ship.getDraft());
        saveShipPreparedStatement.setInt(10, ship.getVesselType());
        insertShipData(databaseConnection,ship.getDynamicShip(),ship.getMmsi());
        //atualizar a classe para adicionar atributos
        saveShipPreparedStatement.executeUpdate();
    }

    private boolean isShipOnDatabase(DataBaseConnection databaseConnection,
                                     Ship ship) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isShipOnDatabase = false;

        String sqlCommand = "SELECT * FROM \"ship\" WHERE \"mmsi\" = ?";

        PreparedStatement getShipPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getShipPreparedStatement.setInt(1, ship.getMmsi());

        try (ResultSet shipsResultSet = getShipPreparedStatement.executeQuery()) {

            if (shipsResultSet.next()) {

                isShipOnDatabase = true;
            } else {


                isShipOnDatabase = false;
            }
        }
        return isShipOnDatabase;
    }

    private void updateShipOnDatabase(DataBaseConnection databaseConnection,
                                      Ship ship) throws SQLException {
        String sqlCommand =
                "update \"ship\" set \"call_sign\" = ?, \"ship_imo_id\" = ?,\"ship_name\" = ?, \"nr_energy_gens\" = ?, \"gen_po\" = ?, \"length\" = ?, \"width\" = ?, \"capacity\" = ?, \"draft\" = ?, \"vessel_type\" = ?   where \"mmsi\" = ?";

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
            sqlCommand = "delete from \"ship_data\" where ship_id = ?";
            try (PreparedStatement deleteShipAddressesPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteShipAddressesPreparedStatement.setInt(1,
                        ship.getMmsi());
                deleteShipAddressesPreparedStatement.executeUpdate();
            }
            //eliminar as outras ocurrencias do ship
            sqlCommand = "delete from \"ship\" where MMSI = ?";
            try (PreparedStatement deleteClientPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteClientPreparedStatement.setInt(1, ship.getMmsi());
                deleteClientPreparedStatement.executeUpdate();
            }
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
    // fazer o upload para ter dados
    public boolean loadFromDatabase(DataBaseConnection databaseConnection) throws SQLException {
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        ArrayList<Ship> tempArray = new ArrayList<>();
        String sqlCommand = "Select * from \"ship\"";
        try (PreparedStatement getShipsPreparedStatement = connection.prepareStatement(sqlCommand)) {

            try (ResultSet shipsPreparedResultSet = getShipsPreparedStatement.executeQuery()) {

                while (shipsPreparedResultSet.next()) {
                    Ship tempShip = new Ship(shipsPreparedResultSet.getInt(1), //mmsi
                           new ArrayList<>(), //dynamicShip
                            shipsPreparedResultSet.getString(4), //vesselname
                            shipsPreparedResultSet.getInt(3), //IMO
                            shipsPreparedResultSet.getString(2), //call_sign
                            shipsPreparedResultSet.getInt(11), //vessel_type
                            shipsPreparedResultSet.getFloat(7), //lentgh
                            shipsPreparedResultSet.getFloat(8), //witdh
                            shipsPreparedResultSet.getFloat(10), //draft
                            shipsPreparedResultSet.getInt(5), //nr_gen
                            (double)shipsPreparedResultSet.getFloat(9), //capacity
                            (double)shipsPreparedResultSet.getInt(6) //gen_po
                    );

                    // carregar a list para a arvore
                    sqlCommand = "Select * from \"ship_data\" where \"ship_id\"=?";
                    try (PreparedStatement getShipsDataPreparedStatement = connection.prepareStatement(sqlCommand)) {
                        getShipsDataPreparedStatement.setInt(1, shipsPreparedResultSet.getInt(1));
                        try (ResultSet shipsDataPreparedResultSet = getShipsDataPreparedStatement.executeQuery()) {

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            while (shipsDataPreparedResultSet.next()) {
                                DynamicShip tempShipdata = new DynamicShip(LocalDateTime.parse(shipsDataPreparedResultSet.getString(2).replace('-','/'),formatter),(double)shipsDataPreparedResultSet.getFloat(3),
                                        (double)shipsDataPreparedResultSet.getFloat(4),(double)shipsDataPreparedResultSet.getFloat(5),(double)shipsDataPreparedResultSet.getFloat(6),(double)shipsDataPreparedResultSet.getFloat(7),
                                        (double)shipsDataPreparedResultSet.getFloat(8),
                                        shipsDataPreparedResultSet.getString(9).charAt(0));

                                tempShip.addDynamicShip(tempShipdata);
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(ShipStore.class.getName())
                                    .log(Level.SEVERE, null, ex);
                            databaseConnection.registerError(ex);
                            returnValue = false;
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    tempArray.add(tempShip);

                }
            } catch (SQLException ex) {
                Logger.getLogger(ShipStore.class.getName())
                        .log(Level.SEVERE, null, ex);
                databaseConnection.registerError(ex);
                returnValue = false;

            }
            shipBST.insert(tempArray);
            return returnValue;
        }
    }
}
