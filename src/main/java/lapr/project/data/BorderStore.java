package lapr.project.data;

import lapr.project.model.Border;
import lapr.project.model.Country;
import lapr.project.model.Port;
import lapr.project.model.Position;
import lapr.project.utils.CsvReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BorderStore {

    private ArrayList<Border> borderArray;

    public BorderStore(){
        this.borderArray = CsvReader.readBorder("src/main/java/lapr/project/data/borders.csv");
    }

    public ArrayList<Border> getBorderArray(){
        return this.borderArray;
    }

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
        savePortPreparedStatement.close();
    }

    private boolean isBorderOnDatabase(DataBaseConnection databaseConnection,
                                       Border border) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isBorderOnDatabase = false;

        String sqlCommand = "SELECT \"border_id\" FROM \"border\" WHERE \"country1\" = ? AND \"country2\" = ?";

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
        getPortPreparedStatement.close();
        return isBorderOnDatabase;
    }
    //TODO INSERIR NO GRAPH
    public boolean loadBorderFromDatabase(DataBaseConnection databaseConnection) {
        boolean returnValue= false;
        Connection connection = databaseConnection.getConnection();
        String sqlCommand = "Select \"country1\",\"country2\" from \"border\"";
        try (PreparedStatement getBorderPreparedStatement = connection.prepareStatement(sqlCommand)) {

            try (ResultSet borderPreparedResultSet = getBorderPreparedStatement.executeQuery()) {

                while (borderPreparedResultSet.next()) {
                    Border tempBorder = new Border(borderPreparedResultSet.getString(1), //country1
                            borderPreparedResultSet.getString(2)//country2
                    );
                    borderArray.add(tempBorder);

                }
                getBorderPreparedStatement.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return returnValue;
        }
        returnValue = true;
        return returnValue;
    }

    //TODO CARREGAR DO GRAPH
    public boolean uploadBorderstoDatabase(DataBaseConnection databaseConnection){
        boolean returnValue = false;


        for (Border b: borderArray) {
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

    public Map<Position, List<Position>> toMap(ArrayList<Border> borderArray, ArrayList<Country> countryArray){
        Map<Position, List<Position>> mapBorder = new HashMap<>();
        for (Border b : borderArray) {
            for (Country c1 : countryArray) {
                if(b.getCountry1().equals(c1.getCountry())) {
                    for (Country c2 : countryArray) {
                        if (b.getCountry2().equals(c2.getCountry())){
                            if(mapBorder.containsKey(c1)){
                                mapBorder.get(c1).add(c2);
                            }else{
                                List<Position> l1 = new ArrayList<>();
                                l1.add(c2);
                                mapBorder.put(c1,l1);
                            }
                        }
                    }
                }
            }
        }
        return mapBorder;
    }

}
