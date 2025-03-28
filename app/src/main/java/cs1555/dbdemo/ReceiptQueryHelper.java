/**
 * A helper class for querying the Receipt example table.
 * The class abstracts SQL query strings passed to JDBC
 * and provides common simple queries that might be used.
 *
 * @author Brian T. Nixon
 */

package cs1555.dbdemo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReceiptQueryHelper {
    private static final int QUERY_TIMEOUT = 30;
    private Connection databaseConnection;

    public ReceiptQueryHelper(Connection conn) {
        this.databaseConnection = conn;
    }

    /**
     * A helper function for querying all rows within a single table
     *
     * @return A List of all Receipts in the Postgres database
     */
    protected List<RowInterface> findAll() {
        List<RowInterface> receiptList = new ArrayList<>();
        try (Statement st = databaseConnection.createStatement()) {
            st.setQueryTimeout(QUERY_TIMEOUT); // set timeout to 30 seconds
            ResultSet rs = st.executeQuery("SELECT * FROM RECEIPT;");
            while (rs.next()) {
                Receipt currentReceipt = buildReceiptFromRow(rs);
                receiptList.add(currentReceipt);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return receiptList;
    }

    /**
     * A helper function for querying the Receipts with the specified
     * receiptID
     *
     * @param receiptID The receiptID that the queried rows should have
     * @return A list of all Receipts in the Postgres database with the specified storeNumber
     */
    protected List<RowInterface> findByReceiptID(int receiptID) {
        List<RowInterface> receiptList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * FROM RECEIPT WHERE receiptID = ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setInt(1, receiptID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Receipt currentReceipt = buildReceiptFromRow(rs);
                receiptList.add(currentReceipt);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return receiptList;
    }

    /**
     * A helper function for querying the Receipts with the specified storeNumber
     *
     * @param storeNumber The storeNumber that the queried rows should have
     * @return A list of all Receipts in the Postgres database with the specified storeNumber
     */
    protected List<RowInterface> findByStoreNumber(int storeNumber) {
        List<RowInterface> receiptList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * FROM RECEIPT WHERE storeNumber = ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setInt(1, storeNumber);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Receipt currentReceipt = buildReceiptFromRow(rs);
                receiptList.add(currentReceipt);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return receiptList;
    }

    /**
     * A helper function for querying the Receipts with the specified coffeeID
     *
     * @param coffeeID The coffeeID that the queried rows should have
     * @return A list of all Receipts in the Postgres database with the specified coffeeID
     */
    protected List<RowInterface> findByCoffeeID(int coffeeID) {
        List<RowInterface> receiptList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * FROM RECEIPT WHERE coffeeID = ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setInt(1, coffeeID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Receipt currentReceipt = buildReceiptFromRow(rs);
                receiptList.add(currentReceipt);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return receiptList;
    }

    /**
     * A helper function for querying the Receipts within a specified
     * quantity range
     *
     * @param lowerBound the lower bound (inclusive) for the quantity range
     * @param upperBound the upper bound (inclusive) for the quantity range
     * @return A list of all Receipts in the Postgres database within the specified quantity range
     */
    protected List<RowInterface> findReceiptInQuantityRange(int lowerBound, int upperBound) {
        List<RowInterface> receiptList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * " +
                                                                            "FROM RECEIPT " +
                                                                            "WHERE quantity >= ? AND quantity <= ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setInt(1, lowerBound);
            st.setInt(2, upperBound);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Receipt currentReceipt = buildReceiptFromRow(rs);
                receiptList.add(currentReceipt);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return receiptList;
    }

    /**
     * A helper function for querying the Receipts within a specified
     * date range
     *
     * @param lowerBound the lower bound (inclusive) for the date range
     * @param upperBound the upper bound (inclusive) for the date range
     * @return A list of all Receipts in the Postgres database within the specified date range
     */
    protected List<RowInterface> findReceiptInDateRange(Date lowerBound, Date upperBound) {
        List<RowInterface> receiptList = new ArrayList<>();
        try (PreparedStatement st = databaseConnection.prepareStatement("SELECT * " +
                "FROM RECEIPT " +
                "WHERE timeOfPurchase >= ? AND timeOfPurchase <= ?")) {
            st.setQueryTimeout(QUERY_TIMEOUT);
            st.setDate(1, lowerBound);
            st.setDate(2, upperBound);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Receipt currentReceipt = buildReceiptFromRow(rs);
                receiptList.add(currentReceipt);
            }
        } catch (SQLException e) {
            handleError(e);
        }
        return receiptList;
    }

    /**
     * A private helper function for converting a row from ResultSet (JDBC's
     * standard return type for a query) to an instance of the Receipt class.
     *
     * @param rs The ResultSet being iterated from executing a query
     * @return The Receipt instance that matches the ResultSet's current row
     * @throws SQLException when the ResultSet is closed or another exception occurs
     *                      such as trying to access a column that is not part of the ResultSet
     */
    private static Receipt buildReceiptFromRow(ResultSet rs) throws SQLException {
        return new Receipt(rs.getInt("receiptID"),
                rs.getInt("storeNumber"),
                rs.getTimestamp("timeOfPurchase"),
                rs.getInt("coffeeID"),
                rs.getInt("quantity"));
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
