/**
 * A helper class for querying the Store example table.
 * The class abstracts SQL query strings passed to JDBC
 * and provides common simple queries that might be used.
 *
 * @author Brian T. Nixon
 * @author Maanya Shanker
 */

package cs1555.dbdemo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreQueryHelper {
    private static final int QUERY_TIMEOUT = 30;
    private Connection databaseConnection;

    /**
     * The constructor for the StoreQueryHelper class, which
     * attempts to build and initialize the STORE table in the
     * Postgres database with some sample data
     *
     * @param conn The database connection for executing modifications
     *             and queries
     */
    public StoreQueryHelper(Connection conn) {
        this.databaseConnection = conn;
        
    }

    /**
     * TODO fill in this method
     * A helper function for querying all rows within a single table
     *
     * @return A List of all Stores in the Postgres database
     */
    protected List<RowInterface> findAll() {
        List<RowInterface> storeList = new ArrayList<>();
        // add your code here
        return storeList;
    }

    /**
     * TODO fill in this method
     * A helper function for querying the Stores with the specified
     * storeNumber
     *
     * @param storeNumber The storeNumber that the queried rows should have
     * @return A list of all Stores in the Postgres database with the specified storeNumber
     */
    protected List<RowInterface> findByStoreNumber(int storeNumber) {
        List<RowInterface> storeList = new ArrayList<>();
        // add your code here
        return storeList;
    }

    /**
     * TODO fill in this method
     * A helper function for querying the Stores with the specified
     * storeNumber
     *
     * @param storeType The storeType that the queried rows should have
     * @return A list of all Stores in the Postgres database with the specified storeType
     */
    protected List<RowInterface> findByStoreType(String storeType) {
        List<RowInterface> storeList = new ArrayList<>();
        // add your code here
        return storeList;
    }

    /**
     * TODO fill in this method
     *  A helper function for querying the Stores with the specified
     * city and state
     *
     * @param city The city that the queried rows should have
     * @param state The state that the queried rows should have
     * @return A list of all Stores in the Postgres database with the specified
     *          city and state
     */
    protected List<RowInterface> findByCityAndState(String city, String state) {
        List<RowInterface> storeList = new ArrayList<>();
        // add your code here
        return storeList;
    }

     /**
     * TODO fill in this method
     * A helper function for adding a new store.
     *
     * @param storeNumber The storeNumber of the store that should be added to the database
     * @param name The name of the store that should be added to the database
     * @param storeType The storeType that should be added to the database
     * @param street The street of the store that should be added to the database
     * @param city The city that should be added to the database
     * @param state The state that should be added to the database
     * @throws SQLException when the ResultSet is closed or another exception occurs
     *                      such as trying to access a column that is not part of the ResultSet
     */
    protected void createStore(int storeNumber, String name, String storeType, String street, String city, String state) throws SQLException {
        // add your code here
    }

    /**
     * A helper function for querying the unique store names
     *
     * @return A list of all Store names in the Postgres database with the specified
     *          city and state
     */
    protected List<String> uniqueStoreNames() {
        List<String> storeList = new ArrayList<>();
        // add your code here
        return storeList;
    }

    /**
     * A private helper function for converting a row from ResultSet (JDBC's
     * standard return type for a query) to an instance of the Store class.
     *
     * @param rs The ResultSet being iterated from executing a query
     * @return The Store instance that matches the ResultSet's current row
     * @throws SQLException when the ResultSet is closed or another exception occurs
     *                      such as trying to access a column that is not part of the ResultSet
     */
    private static Store buildStoreFromRow(ResultSet rs) throws SQLException {
        return new Store(rs.getInt("storeNumber"),
                rs.getString("name"),
                rs.getString("storeType"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("state"));
    }

   

    /**
     * A helper function for handling errors that prints the error message,
     * SQL State, and the SQL Code for the error
     * @param err The SQLException being handled
     */
    private static void handleError(SQLException err) {
        System.err.println("The following error occurred while executing the query/update:");
        System.err.println("Message = " + err.getMessage());
        System.err.println("SQLState = " + err.getSQLState());
        System.err.println("SQL Code = " + err.getErrorCode());
    }
}
