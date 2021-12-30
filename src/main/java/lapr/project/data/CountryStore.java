package lapr.project.data;

import lapr.project.model.Border;
import lapr.project.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountryStore {
    public boolean save(DataBaseConnection databaseConnection, Object object) {
        Country country = (Country) object;
        boolean returnValue = false;

        try {
            saveCountryToDatabase(databaseConnection, country);

            returnValue = true;

        } catch (SQLException ex) {
            Logger.getLogger(CountryStore.class.getName())
                    .log(Level.SEVERE, null, ex);
            databaseConnection.registerError(ex);
            returnValue = false;
        }
        return returnValue;
    }
    private void saveCountryToDatabase(DataBaseConnection dataBaseConnection, Country country) throws SQLException {
        if (isCountryOnDatabase(dataBaseConnection, country)) {
            updateCountryOnDatabase(dataBaseConnection, country);
        } else {
            insertCountryOnDatabase(dataBaseConnection, country);
        }
    }

    private void insertCountryOnDatabase(DataBaseConnection databaseConnection,
                                         Country country) throws SQLException {
        String sqlCommand =
                "insert into \"country\" (\"name\",\"continent\", \"alpha2code\", \"alpha3code\", \"population\", \"capital\",\"lat\",\"lon\") values (?, ?, ?,?,?,?,?,?)";

        executeInsertCountryStatementOnDatabase(databaseConnection, country,
                sqlCommand);
    }
    private void executeInsertCountryStatementOnDatabase(
            DataBaseConnection databaseConnection,
            Country country, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(sqlCommand);
        savePortPreparedStatement.setString(1, country.getCountry());
        savePortPreparedStatement.setString(2, country.getContinent());
        savePortPreparedStatement.setString(3, country.getAlpha2Code());
        savePortPreparedStatement.setString(4, country.getAlpha3Code());
        savePortPreparedStatement.setFloat(5, (float) country.getPopulation());
        savePortPreparedStatement.setString(6, country.getCapital());
        savePortPreparedStatement.setFloat(7,(float)country.getLatitude());
        savePortPreparedStatement.setFloat(8,(float) country.getLongitude());

        savePortPreparedStatement.executeUpdate();
    }

    private void updateCountryOnDatabase(DataBaseConnection databaseConnection,
                                         Country country) throws SQLException {
        String sqlCommand =
                "update \"country\" set \"name\" = ?, \"continent\" = ?, \"alpha2code\" = ?, \"population\" = ?, \"capital\", \"lat\", \"lon\" = ?  where \"alpha3code\" = ?";

        executeUpdateCountryStatementOnDatabase(databaseConnection, country,
                sqlCommand);
    }
    private void executeUpdateCountryStatementOnDatabase(
            DataBaseConnection databaseConnection,
            Country country, String sqlCommand) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        PreparedStatement savePortPreparedStatement =
                connection.prepareStatement(
                        sqlCommand);
        savePortPreparedStatement.setString(1, country.getCountry());
        savePortPreparedStatement.setString(2, country.getContinent());
        savePortPreparedStatement.setString(3, country.getAlpha2Code());
        savePortPreparedStatement.setFloat(4, (float) country.getPopulation());
        savePortPreparedStatement.setString(5, country.getCapital());
        savePortPreparedStatement.setFloat(6,(float)country.getLatitude());
        savePortPreparedStatement.setFloat(7,(float) country.getLongitude());
        savePortPreparedStatement.setString(8, country.getAlpha3Code());

        //atualizar a classe para adicionar atributos
        savePortPreparedStatement.executeUpdate();
    }
    private boolean isCountryOnDatabase(DataBaseConnection databaseConnection,
                                        Country country) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        boolean isCountryOnDatabase = false;

        String sqlCommand = "SELECT * FROM \"country\" WHERE \"alpha3code\" = ?";

        PreparedStatement getCountryPreparedStatement =
                connection.prepareStatement(sqlCommand);

        getCountryPreparedStatement.setString(1,country.getAlpha3Code());

        try (ResultSet countryResultSet = getCountryPreparedStatement.executeQuery()) {

            if (countryResultSet.next()) {
                isCountryOnDatabase = true;
            } else {


                isCountryOnDatabase = false;
            }
        }

        return isCountryOnDatabase;
    }

    public boolean loadCountryFromDatabase(DataBaseConnection databaseConnection) {
        boolean returnValue= false;
        Connection connection = databaseConnection.getConnection();
        ArrayList<Country> tempArray = new ArrayList<>();
        String sqlCommand = "Select * from \"country\"";
        try (PreparedStatement getCountryPreparedStatement = connection.prepareStatement(sqlCommand)) {

            try (ResultSet countryPreparedResultSet = getCountryPreparedStatement.executeQuery()) {

                while (countryPreparedResultSet.next()) {
                    Country tempCountry = new Country(countryPreparedResultSet.getString(2), //continent
                            countryPreparedResultSet.getString(3),//alpha2code,
                            countryPreparedResultSet.getString(4), //alpha3code
                            countryPreparedResultSet.getString(1), //country
                            countryPreparedResultSet.getFloat(5), //population
                            countryPreparedResultSet.getString(6), //capital
                            countryPreparedResultSet.getFloat(7), //lat
                            countryPreparedResultSet.getFloat(8)  //lon
                            );
                    tempArray.add(tempCountry);

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return returnValue;
        }
        returnValue = true;
        return returnValue;
    }
    public boolean uploadCountriestoDatabase(DataBaseConnection databaseConnection){
        boolean returnValue = false;

        ArrayList<Country> countries = null;
        for (Country c: countries) {
            this.save(databaseConnection,c);
        }
        returnValue = true;
        return returnValue;
    }
    public boolean uploadCountriestoDatabase(DataBaseConnection databaseConnection,ArrayList<Country> countries ){
        boolean returnValue = false;

        for (Country c: countries) {
            this.save(databaseConnection,c);
        }
        returnValue = true;
        return returnValue;
    }
    public boolean delete(DataBaseConnection databaseConnection,
                          Object object) {
        boolean returnValue = false;
        Connection connection = databaseConnection.getConnection();
        Country country = (Country) object;

        try {
            //TODO VER ISTO

            String sqlCommand = "delete from \"country\" where \"alpha3code\" = ?";
            try (PreparedStatement deleteCountryPreparedStatement = connection.prepareStatement(
                    sqlCommand)) {
                deleteCountryPreparedStatement.setString(1, country.getAlpha3Code());
                deleteCountryPreparedStatement.executeUpdate();
            }
            returnValue = true;

        } catch (SQLException exception) {
            Logger.getLogger(CountryStore.class.getName())
                    .log(Level.SEVERE, null, exception);
            databaseConnection
                    .registerError(exception);

        }

        return returnValue;
    }

}
