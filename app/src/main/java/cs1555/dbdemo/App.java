/*
 * A simple Java client app for connecting to and interacting with
 * a Postgres database.
 *
 * @author Brian T. Nixon
 * @author Maanya Shanker
 */
package cs1555.dbdemo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        /*
          Attempt to establish a connection to the database
          Java try-with-resources will create connection and
          autoclose at the end of the try block
         */
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("escapeSyntaxCallMode", "callIfNoReturn");
        // TODO: Update Password to your postgres password (should be your pitt id if you followed instructions...)
        props.setProperty("password", "password");
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", props);
             Scanner scanner = new Scanner(System.in)) {

            // TODO: Set the schema to recitation
            
            int menu = -1;

            // Helper Classes for querying the Postgres database using JDBC
            StoreQueryHelper storeHelper = new StoreQueryHelper(conn);
            CoffeeQueryHelper coffeeHelper = new CoffeeQueryHelper(conn);
            ReceiptQueryHelper receiptHelper = new ReceiptQueryHelper(conn);

            // For storing the result rows from queries
            List<RowInterface> queryResults;

            // A simple menu allowing the user to select queries
            while (menu != 0) {
                System.out.println("Please enter the number for the query you would like to run from the list below: ");
                displayMenu();
                menu = scanner.nextInt();
                // When using nextInt, the entire buffer is not consumed... so ensure nothing is dangling
                scanner.nextLine();
                switch (menu) {
                    case 1:
                        System.out.println("You've selected to find all stores:");
                        queryResults = storeHelper.findAll();
                        printResultRows(queryResults);
                        break;
                    case 2:
                        System.out.println("You've selected to find a specific store number, please enter the store number:");
                        int storeToQuery = scanner.nextInt();
                        scanner.nextLine();

                        queryResults = storeHelper.findByStoreNumber(storeToQuery);
                        printResultRows(queryResults);
                        break;
                    case 3:
                        System.out.println("You've selected to find all stores with a specific store type, please enter the store type:");
                        String storeTypeToQuery = scanner.nextLine();

                        queryResults = storeHelper.findByStoreType(storeTypeToQuery);
                        printResultRows(queryResults);
                        break;
                    case 4:
                        System.out.println("You've selected to find all stores with a specific city and state. Please enter the city:");
                        String cityToQuery = scanner.nextLine();
                        System.out.println("Please enter the state:");
                        String stateToQuery = scanner.nextLine();

                        queryResults = storeHelper.findByCityAndState(cityToQuery, stateToQuery);
                        printResultRows(queryResults);
                        break;
                    case 5:
                        System.out.println("You've selected to find all coffees:");
                        queryResults = coffeeHelper.findAll();
                        printResultRows(queryResults);
                        break;
                    case 6:
                        System.out.println("You've selected to find all coffees with a given coffee name: Please enter the name");
                        String coffeeNameToQuery = scanner.nextLine();

                        queryResults = coffeeHelper.findByName(coffeeNameToQuery);
                        printResultRows(queryResults);
                        break;
                    case 7:
                        System.out.println("You've selected to find all coffees with a given intensity: Please enter the intensity");
                        int coffeeIntensityToQuery = scanner.nextInt();
                        scanner.nextLine();

                        queryResults = coffeeHelper.findByIntensity(coffeeIntensityToQuery);
                        printResultRows(queryResults);
                        break;
                    case 8:
                        System.out.println("You've selected to find all coffees within a given price range: Please enter the lower price of the range");
                        double lowerPriceBound = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Please enter the upper price of the range");
                        double upperPriceBound = scanner.nextDouble();
                        scanner.nextLine();

                        queryResults = coffeeHelper.findCoffeeInPriceRange(lowerPriceBound, upperPriceBound);
                        printResultRows(queryResults);
                        break;
                    case 9:
                        System.out.println("You've selected to find all receipts:");

                        queryResults = receiptHelper.findAll();
                        printResultRows(queryResults);
                        break;
                    case 10:
                        System.out.println("You've selected to find all receipts with a given receiptID: Please enter the receiptID");
                        int receiptIDToQuery = scanner.nextInt();
                        scanner.nextLine();

                        queryResults = receiptHelper.findByReceiptID(receiptIDToQuery);
                        printResultRows(queryResults);
                        break;
                    case 11:
                        System.out.println("You've selected to find all receipts for a given storeNumber: Please enter the storeNumber");
                        int storeNumberToQuery = scanner.nextInt();
                        scanner.nextLine();
                        queryResults = receiptHelper.findByStoreNumber(storeNumberToQuery);
                        printResultRows(queryResults);
                        break;
                    case 12:
                        System.out.println("You've selected to find all receipts for a given coffeeID. Please enter the coffeeID");
                        int coffeeIDToQuery = scanner.nextInt();
                        scanner.nextLine();
                        queryResults = receiptHelper.findByCoffeeID(coffeeIDToQuery);
                        printResultRows(queryResults);
                        break;
                    case 13:
                        System.out.println("You've selected to find all receipts within a given quantity range. Please enter the lower quantity of the range");
                        int lowerQuantity = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please enter the upper quantity of the range");
                        int upperQuantity = scanner.nextInt();
                        scanner.nextLine();

                        queryResults = receiptHelper.findReceiptInQuantityRange(lowerQuantity, upperQuantity);
                        printResultRows(queryResults);
                        break;
                    case 14:
                        System.out.println("You've selected to find all receipts within a given date range.\n" +
                                "Please enter the lower date of the range using the following format: YYYY-MM-DD");
                        String lowerRange = scanner.nextLine();
                        System.out.println("Please enter the upper date of the range using the following format: YYYY-MM-DD");
                        String upperRange = scanner.nextLine();

                        // Convert Strings to Date type
                        Date lowerDate = Date.valueOf(lowerRange);
                        Date upperDate = Date.valueOf(upperRange);

                        queryResults = receiptHelper.findReceiptInDateRange(lowerDate, upperDate);
                        printResultRows(queryResults);
                        break;
                    case 15:
                        System.out.println("You've selected to create a new store.\n" +
                                "Please enter the new store number ");
                        int storeNumber = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please enter the new store name ");
                        String name = scanner.nextLine();
                        System.out.println("Please enter the new store type ");
                        String type = scanner.nextLine();
                        System.out.println("Please enter the new store street ");
                        String street = scanner.nextLine();
                        System.out.println("Please enter the new store city ");
                        String city = scanner.nextLine();
                        System.out.println("Please enter the new store state ");
                        String state = scanner.nextLine();

                        storeHelper.createStore(storeNumber, name, type, street, city, state);
                        System.out.println("Successfully added!");
                        break;
                    case 16:
                        System.out.println("You've selected to find all unique store names\n");

                        List<String> results = storeHelper.uniqueStoreNames();
                        printStringResults(results);
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Message = " + e.getMessage());
            System.err.println("SQLState = " + e.getSQLState());
            System.err.println("SQL Code = " + e.getErrorCode());
        }
    }

    private static void displayMenu() {
        // Text Block added in Java 15
        String menuString = """
                +------------------------------------------------------+
                | (0)  Exit the Application                            |
                | (1)  Find All Stores                                 |
                | (2)  Find All Stores with a given store number       |
                | (3)  Find All Stores with a given store type         |
                | (4)  Find All Stores in a given city and state       |
                | (5)  Find All Coffees                                |
                | (6)  Find All Coffees with a given coffee name       |
                | (7)  Find All Coffees for a given intensity          |
                | (8)  Find All Coffees within a given price range     |
                | (9)  Find All Receipts                               |
                | (10) Find All Receipts with a given receiptID        |
                | (11) Find All Receipts for a given storeNumber       |
                | (12) Find All Receipts for a given coffeeID          |
                | (13) Find All Receipts within a given quantity range |
                | (14) Find All Receipts within a given date range     |
                | (15) Create a new Store                              |
                | (16) Find all unique Store names                     |
                +---------------------------------------------------+""";
        System.out.println(menuString);
    }

    private static void printResultRows(List<RowInterface> queryResults) {
        if (queryResults == null) {
            return;
        }

        // Print the name of the table beforehand
        if (!queryResults.isEmpty()) {
            String tableName;
            if (queryResults.getFirst() instanceof Store) {
                tableName = """
                        +----------------------------------------------------------------+
                        |                             Store                              |
                        +----------------------------------------------------------------+""";
            } else if (queryResults.getFirst() instanceof Coffee) {
                tableName = """
                        +----------------------------------------------------------------+
                        |                             Coffee                             |
                        +----------------------------------------------------------------+""";
            } else {
                tableName = """
                        +----------------------------------------------------------------+
                        |                              Receipt                              |
                        +----------------------------------------------------------------+""";
            }
            System.out.println(tableName);
        }
        for (RowInterface queryResult : queryResults) {
            System.out.println(queryResult);
        }
        System.out.println("\n");
    }

    private static void printStringResults(List<String> queryResults) {
        if (queryResults == null) {
            return;
        }
        // Print the name of the table beforehand
        if (!queryResults.isEmpty()) {
            String tableName = """
                        +----------------------------------------------------------------+
                        |                             Store                              |
                        +----------------------------------------------------------------+""";
            System.out.println(tableName);
        }
        for (String queryResult : queryResults) {
            System.out.println(queryResult);
        }
        System.out.println("\n");
    }
}
