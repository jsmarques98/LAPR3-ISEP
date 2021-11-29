package lapr.project.data;

public interface Persistable {
    boolean save(DataBaseConnection databaseConnection, Object object);

    /**
     * Delete an object from the database.
     *
     * @param databaseConnection
     * @param object
     * @return Operation success.
     */
    boolean delete(DataBaseConnection databaseConnection, Object object);

    //criar os get dos ships
}
