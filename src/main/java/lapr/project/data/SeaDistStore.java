package lapr.project.data;

import lapr.project.model.Country;
import lapr.project.model.Port;
import lapr.project.model.SeaDist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeaDistStore {

    private ArrayList<SeaDist> seaDistArrayList;

    public SeaDistStore(){
        seaDistArrayList = new ArrayList<>();
    }

    public ArrayList<SeaDist> getSeaDistArrayList(){
        return this.seaDistArrayList;
    }

    public boolean save(DataBaseConnection databaseConnection, Object object) {
        SeaDist seadits = (SeaDist) object;
        boolean returnValue = false;

        try {
            saveSeadistToDatabase(databaseConnection, seadits);

            returnValue = true;

        } catch (SQLException ex) {
            Logger.getLogger(SeaDistStore.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }
    private void saveSeadistToDatabase(DataBaseConnection dataBaseConnection, SeaDist seadits) throws SQLException {
        if (!isSeaDistOnDatabase(dataBaseConnection, seadits)) {

            insertSeaDistOnDatabase(dataBaseConnection, seadits);
        }
    }
    private void insertSeaDistOnDatabase(DataBaseConnection databaseConnection,
                                         SeaDist seadits) throws SQLException {
        String sqlCommand =
                "insert into \"seadist\" (\"from_country\", \"from_port_id\", \"to_country\", \"to_port_id\", \"distance\") values ( ?, ?,?,?,?)";

        executeInsertSeaDistStatementOnDatabase(databaseConnection, seadits,
                sqlCommand);
    }
    private void executeInsertSeaDistStatementOnDatabase(
            DataBaseConnection databaseConnection,
            SeaDist seadits, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement saveSeadDistPreparedStatement =
                connection.prepareStatement(sqlCommand);
        saveSeadDistPreparedStatement.setString(1, seadits.getFromCountry());
        saveSeadDistPreparedStatement.setInt(2, seadits.getFromPortId());
        saveSeadDistPreparedStatement.setString(3, seadits.getToCountry());
        saveSeadDistPreparedStatement.setInt(4, seadits.getToPortId());
        saveSeadDistPreparedStatement.setFloat(5, (float) seadits.getSeaDistance());

        saveSeadDistPreparedStatement.executeUpdate();
    }
    private boolean isSeaDistOnDatabase(DataBaseConnection databaseConnection,
                                        SeaDist seadits) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isSeaDistOnDatabase = false;

        String sqlCommand = "SELECT * FROM \"seadist\" WHERE \"from_port_id\" = ? AND \"to_port_id\" = ?";

        PreparedStatement getSeadDistPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getSeadDistPreparedStatement.setInt(1,seadits.getFromPortId());
        getSeadDistPreparedStatement.setInt(2,seadits.getToPortId());

        try (ResultSet seaDistResultSet = getSeadDistPreparedStatement.executeQuery()) {

            if (seaDistResultSet.next()) {
                isSeaDistOnDatabase = true;
            } else {


                isSeaDistOnDatabase = false;
            }
        }

        return isSeaDistOnDatabase;
    }
    public boolean delete(DataBaseConnection databaseConnection,
                          Object object) {
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        SeaDist seaDist = (SeaDist) object;

        try {
            String sqlCommand = "delete from \"seadist\" where \"from_port_id\" = ? AND \"to_port_id\" = ?";
            try (PreparedStatement deleteSeaDistPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteSeaDistPreparedStatement.setInt(1, seaDist.getFromPortId());
                deleteSeaDistPreparedStatement.setInt(2, seaDist.getToPortId());
                deleteSeaDistPreparedStatement.executeUpdate();
            }

            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(SeaDistStore.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);

        }

        return returnValue;
    }
    public boolean loadSeadistFromDatabase(DataBaseConnection databaseConnection) {
        boolean returnValue= false;
        Connection connection = databaseConnection.getConnection();
        String sqlCommand = "Select \"from_country\",\"from_port_id\",\"to_country\",\"to_port_id\",\"distance\" from \"seadist\"";
        try (PreparedStatement getSeadistPreparedStatement = connection.prepareStatement(sqlCommand)) {

            try (ResultSet seadistPreparedResultSet = getSeadistPreparedStatement.executeQuery()) {
                while (seadistPreparedResultSet.next()) {
                    String from_Country_id = seadistPreparedResultSet.getString(1);
                    String from_Country_name = "";
                    int from_port_id = seadistPreparedResultSet.getInt(2);
                    String from_port_name = "";
                    String to_Country_id = seadistPreparedResultSet.getString(3);
                    String to_Country_name = "";
                    int to_port_id = seadistPreparedResultSet.getInt(4);
                    String to_port_name = "";
                    sqlCommand = "Select \"capital\" from \"country\" WHERE \"alpha3code\"=?";
                    try (PreparedStatement getseadistPreparedStatement = connection.prepareStatement(sqlCommand)){
                        getseadistPreparedStatement.setString(1,from_Country_id);
                        try (ResultSet countryPreparedResultSet = getSeadistPreparedStatement.executeQuery()){
                            from_Country_name=countryPreparedResultSet.getString(1);
                        }
                    }
                    try (PreparedStatement getcountryPreparedStatement = connection.prepareStatement(sqlCommand)){
                        getcountryPreparedStatement.setString(1,from_Country_id);
                        try (ResultSet countryPreparedResultSet = getcountryPreparedStatement.executeQuery()){
                            to_Country_name=countryPreparedResultSet.getString(1);
                        }
                    }
                    sqlCommand = "Select \"name\" from \"port_warehouse\" WHERE \"port_warehouse_id\"=?";
                    try (PreparedStatement getseadistPreparedStatement = connection.prepareStatement(sqlCommand)){
                        getseadistPreparedStatement.setInt(1,from_port_id);
                        try (ResultSet countryPreparedResultSet = getSeadistPreparedStatement.executeQuery()){
                            from_port_name=countryPreparedResultSet.getString(1);
                        }
                    }
                    try (PreparedStatement getcountryPreparedStatement = connection.prepareStatement(sqlCommand)){
                        getcountryPreparedStatement.setInt(1,to_port_id);
                        try (ResultSet countryPreparedResultSet = getcountryPreparedStatement.executeQuery()){
                            to_port_name=countryPreparedResultSet.getString(1);
                        }
                    }
                    SeaDist tempSeadist = new SeaDist(from_Country_name,from_port_id,from_port_name,to_Country_name
                            ,to_port_id,to_port_name,seadistPreparedResultSet.getFloat(5));


                    seaDistArrayList.add(tempSeadist);

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return returnValue;
        }
        returnValue = true;
        return returnValue;
    }
    public boolean uploadSeadistToDatabase(DataBaseConnection databaseConnection){
        boolean returnValue = false;

        ArrayList<SeaDist> seaDists = null;
        for (SeaDist s: seaDists) {
            this.save(databaseConnection,s);
        }
        returnValue = true;
        return returnValue;
    }
        public boolean uploadSeadistToDatabase(DataBaseConnection databaseConnection,ArrayList<SeaDist> seaDists){
        boolean returnValue = false;

        for (SeaDist s: seaDists) {
            this.save(databaseConnection,s);
        }
        returnValue = true;
        return returnValue;
    }

}
