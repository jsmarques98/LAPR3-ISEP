package lapr.project.data;


import java.io.IOException;
import java.sql.*;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sprint4Store {

    public Sprint4Store(){
    }

    public void idleTimeShip() throws SQLException {
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        Connection connection = databaseConnection.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "DELETE FROM \"idle_ship\"";

        stmt.executeUpdate(sql);
        String sqlCommand = "{?=call \"idle_time_ship\"}";
        CallableStatement call = connection.prepareCall(sqlCommand);

        call.registerOutParameter(1,Types.REF_CURSOR);
        call.executeUpdate();
        ResultSet idleTimePreparedResultSet =(ResultSet) call.getObject(1);
        System.out.println("Ship_id | Days");
        while (idleTimePreparedResultSet.next()) {

            System.out.println("    "+idleTimePreparedResultSet.getInt(1)+"      "+idleTimePreparedResultSet.getInt(2));
        }
        idleTimePreparedResultSet.close();
        call.close();
    }
    public void weaklyOperationMap(int portId) throws SQLException {
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }

        Connection connection = databaseConnection.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "DELETE FROM \"weakly_operation_table\"";

        stmt.executeUpdate(sql);
        String sqlCommand = "{?=call \"weakly_operation_map\"(?)}";
        CallableStatement call = connection.prepareCall(sqlCommand);

        call.registerOutParameter(1,Types.REF_CURSOR);
        call.setInt(2,portId);
        call.executeUpdate();
        ResultSet idleTimePreparedResultSet =(ResultSet) call.getObject(1);
        System.out.println("    Date   | Type  | Vehicle |  NumContainers |  Container ID |  x  |  y  |  z  | weigth ");
        while (idleTimePreparedResultSet.next()) {

            System.out.println(idleTimePreparedResultSet.getDate(1)+"   "+idleTimePreparedResultSet.getString(2)
                                +"    "+idleTimePreparedResultSet.getInt(3)+"                "+idleTimePreparedResultSet.getInt(4)
                    +"                  "+idleTimePreparedResultSet.getInt(5)+"       "+idleTimePreparedResultSet.getInt(6)
                    +"   "+idleTimePreparedResultSet.getInt(7)+"       "+idleTimePreparedResultSet.getInt(8)+"     "+idleTimePreparedResultSet.getFloat(9));
        }
        idleTimePreparedResultSet.close();
        call.close();
    }
    public void funcAvgOccuRate(LocalDate data, LocalDate data2, int ship_id) throws SQLException {
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        Connection connection = databaseConnection.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "DELETE FROM \"func_avg_occu_rate_table\"";

        stmt.executeUpdate(sql);
        String sqlCommand = "{?=call \"func_avg_occu_rate\"(?,?,?)}";
        CallableStatement call = connection.prepareCall(sqlCommand);

        call.registerOutParameter(1,Types.REF_CURSOR);
        call.setDate(2,Date.valueOf(data));
        call.setDate(3,Date.valueOf(data2));

        call.setInt(4,ship_id);
        call.executeUpdate();
        ResultSet idleTimePreparedResultSet =(ResultSet) call.getObject(1);
        System.out.println("Cargo ID   | AVG");
        while (idleTimePreparedResultSet.next()) {

            System.out.println(idleTimePreparedResultSet.getInt(1) +"      " +idleTimePreparedResultSet.getFloat(2));
        }
        idleTimePreparedResultSet.close();
        call.close();
    }
    public void funcAvgOcupShipThreshold(int shipId) throws SQLException {
        DataBaseConnection databaseConnection = null;
        try {
            databaseConnection = ConnectionFactory.getInstance()
                    .getDatabaseConnection();
        } catch (IOException exception) {
            Logger.getLogger(ShipStore.class.getName())
                    .log(Level.SEVERE, null, exception);
        }
        Connection connection = databaseConnection.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "DELETE FROM \"func_avg_ocup_ship_threshold_table\"";

        stmt.executeUpdate(sql);
        sql = "DELETE FROM \"func_avg_occu_rate_table\"";

        stmt.executeUpdate(sql);
        stmt.close();
        String sqlCommand = "{?=call \"func_avg_ocup_ship_threshold\"}";
        CallableStatement call = connection.prepareCall(sqlCommand);

        call.registerOutParameter(1,Types.REF_CURSOR);
        call.executeUpdate();
        ResultSet idleTimePreparedResultSet =(ResultSet) call.getObject(1);
        try {
            System.out.println(" Origem    |    Date start   |     Fim     |    Date end");
            while (idleTimePreparedResultSet.next()) {

                System.out.println(idleTimePreparedResultSet.getInt(1) + "   " + idleTimePreparedResultSet.getDate(2)
                        + "   " + idleTimePreparedResultSet.getDate(4) + "   " + idleTimePreparedResultSet.getInt(3));
            }
            idleTimePreparedResultSet.close();
        }catch (NullPointerException x){
            System.out.println("No data");
        }

        call.close();
    }
}
