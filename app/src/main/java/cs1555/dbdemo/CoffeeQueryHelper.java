/**
 * A helper class for querying the Coffee example table.
 * The class abstracts SQL query strings passed to JDBC
 * and provides common simple queries that might be used.
 *
 * @author Brian T. Nixon
 */

package cs1555.dbdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoffeeQueryHelper {
    private static final int QUERY_TIMEOUT = 30;
    private Connection databaseConnection;

    public CoffeeQueryHelper(Connection conn) {
        this.databaseConnection = conn;
    }

    /**
     * A helper function for querying all rows within a single table
     *
     * @return A List of all Sales in the Postgres database
     */
    protected List<RowInterface> findAll() {
        List<RowInterface> coffeeList = new ArrayList<>();
        try (Statement st = databaseConnection.createStatement()) {
            st.setQueryTimeout(QUERY_TIMEOUT); // set timeout to 30 seconds
            ResultSet rs = st.executeQuery("SELECT * FROM COFFEE;");
            while (rs.next()) {
                Coffee currentCoffee = buildCoffeeFromRow(rs);
                coffeeList.add(currentCoffee);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return coffeeList;
    }

    /**
     * A helper function for querying all Coffees with a
     * specified name
     *
     * @param name The name that queried Coffees will have
     * @return A list of all Coffees in the Postgres database with the specified name
     */
    protected List<RowInterface> findByName(String name) {
        List<RowInterface> coffeeList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * FROM COFFEE WHERE name = ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Coffee currentCoffee = buildCoffeeFromRow(rs);
                coffeeList.add(currentCoffee);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return coffeeList;
    }

    /**
     * A helper function for querying all Coffees with a
     * specified coffee intensity
     *
     * @param intensity The intensity that queried Coffees will have
     * @return A list of all Coffees in the Postgres database with the specified intensity
     */
    protected List<RowInterface> findByIntensity(int intensity) {
        List<RowInterface> coffeeList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * FROM COFFEE WHERE intensity = ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setInt(1, intensity);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Coffee currentCoffee = buildCoffeeFromRow(rs);
                coffeeList.add(currentCoffee);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return coffeeList;
    }

    /**
     * A helper function for querying all Coffees with a price
     * within the range lowerBound <= price <= upperBound
     *
     * @param lowerBound The lower bound on price
     * @param upperBound The upper bound on price
     * @return A list of all Coffees in the Postgres database within the price range
     */
    protected List<RowInterface> findCoffeeInPriceRange(double lowerBound, double upperBound) {
        List<RowInterface> coffeeList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * " +
                "FROM COFFEE " +
                "WHERE intensity >= ? AND intensity <= ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setDouble(1, lowerBound);
            st.setDouble(2, upperBound);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Coffee currentCoffee = buildCoffeeFromRow(rs);
                coffeeList.add(currentCoffee);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return coffeeList;
    }

    /**
     * A private helper function for converting a row from ResultSet (JDBC's
     * standard return type for a query) to an instance of the Sale class.
     *
     * @param rs The ResultSet being iterated from executing a query
     * @return The Sale instance that matches the ResultSet's current row
     * @throws SQLException when the ResultSet is closed or another exception occurs
     *                      such as trying to access a column that is not part of the ResultSet
     */
    private static Coffee buildCoffeeFromRow(ResultSet rs) throws SQLException {
        return new Coffee(rs.getInt("coffeeID"),
                rs.getString("name"),
                rs.getInt("intensity"),
                rs.getDouble("price"));
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
