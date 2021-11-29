package lapr.project.data;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    private static ConnectionFactory instance = null;

    /**
     * This is the size of the connection pool.
     */
    private final Integer connectionPoolCount = 1;

    private final List<DataBaseConnection> databaseConnectionList =
            new ArrayList<>();

    private Integer connectionPoolRequest = 0;

    public ConnectionFactory() throws IOException {
        loadProperties();
    }

    public static synchronized ConnectionFactory getInstance()
            throws IOException {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    /**
     * Load Properties from application.properties file.
     */
    private void loadProperties() throws IOException {
        //Load existing properties.
        Properties properties = new Properties(System.getProperties());

        //Read new properties from file.
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream(
                        "application.properties");
        properties.load(inputStream);
        inputStream.close();

        //Set new properties.
        System.setProperties(properties);
    }

    public DataBaseConnection getDatabaseConnection() {
        DataBaseConnection databaseConnection;
        if (++connectionPoolRequest > connectionPoolCount) {
            connectionPoolRequest = 1;
        }
        if (connectionPoolRequest > databaseConnectionList.size()) {
            databaseConnection =
                    new DataBaseConnection(url(), user(), password());
            databaseConnectionList.add(databaseConnection);
        } else {
            databaseConnection =
                    databaseConnectionList.get(connectionPoolRequest - 1);
        }
        return databaseConnection;
    }

    /**
     * Get Database URL from properties file.
     *
     * @return database.url property
     */
    private String url() {
        return System.getProperty("database.url");
    }

    /**
     * Get Database user from properties file.
     *
     * @return database.user property
     */
    private String user() {
        return System.getProperty("database.username");
    }

    /**
     * Get Database password from properties file.
     *
     * @return database.password property
     */
    private String password() {
        return System.getProperty("database.password");
    }
}
