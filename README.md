# CS 1555 JDBC Exercise

This exercise acts as a brief demo for a CS 1555, Database Management Systems, at the University of
Pittsburgh.

## Introduction

In this recitation exercise, you will practice connecting to a Postgres database from a java application using JDBC. JDBC
allows the application to send dynamic SQL (Structured Query Language) statements to the database. These statements
include the creation of tables, modification of tables, and querying the tables for analysis. Your tasks will be to
store data for the *DataBrew* coffee chain by completing the necessary TODOs to learn how to connect to a relational
database from Java and how to perform simple database queries. Recall that *DataBrew* consists of Stores, Coffees, and
Receipts. You will implement the `findAll`, `findByStoreNumber`, `findByStoreType`, `findByCityAndState`, `createStore`, 
`buildStoreFromRow` methods of `StoreQueryHelper`.

## Exercise

1) Review the structure of the repository.

   Navigate to the subdirectory `app/src/main/java/cs1555.dbdemo/` and note the following provided Java files:

    - `App.java` is a simple client application with a menu for performing database queries.
    - `Store.java` is a class for modeling a row in the Store table.
    - `Coffee.java` is a class for modeling a row in the Coffee table.
    - `Receipt.java` is a class for modeling a row in the Receipt table.
    - `RowInterface.java` acts as an interface implemented by `Coffee`, `Store`, and `Receipt` for easier printing in
      `App.java`
    - `StoreQueryHelper.java` is a class that performs Postgres database queries on the Store table by sending SQL queries
      using JDBC. The class is utilized as an abstraction for the client app.
    - `CoffeeQueryHelper.java` is a class that performs Postgres database queries on the Coffee table by sending SQL queries
      using JDBC. The class is utilized as an abstraction for the client app.
    - `ReceiptQueryHelper.java` is a class that performs Postgres database queries on the Receipt table by sending SQL queries
      using JDBC. The class is utilized as an abstraction for the client app.

   In addition to the main portion of the code, the sql file that creates the tables and functions necessary are located in `app/src/main/sql/coffee_shop.sql` and test classes could be added in the subdirectory `app/src/test/java/cs1555.dbdemo/`.

Lastly, it's important to note the file `/app/build.gradle` which essentially tells Gradle what to run and any
dependencies (libraries) that need loaded into the project when building/running. While the first three dependencies
are fairly standard in a Gradle application, the last dependency `implementation 'org.postgresql:postgresql:42.7.5'` is
responsible for loading the `.jar` for using the Postgres database. If you swap to another DBMS, be sure to read the
installation instructions and acquire the correct `.jar` file if using JDBC.

2) We actually need to create all the tables and functions that we need by first running `coffee_shop.sql` in DataGrip. So open up Datagrip and run the entire file.

3) To test compiling and building the setup, run `./gradlew build`. To run the application use `./gradlew run`. It should
not run (assuming your password isn't password), and say that password authentication failed. To fix this, go to 
`./app/src/main/java/cs1555.dbdemo/App.java` and update the password (TODO).


4) Continue reading the contents of `./app/src/main/java/cs1555.dbdemo/App.java`.
    1) First note the test properties being set and the JDBC `Connection` object created within the try-with-resources.
       Recall that JDBC is an API that offers several useful methods and features for interacting with a DBMS. An
       important note is the URL when connecting to the DBMS, which can be swapped out depending on the database. The
       URL always begins with `jdbc` followed by the DBMS being used, in our case `postgresql`, and other specifications for
       connecting to the DBMS (e.g., host, port, etc.). In our case, postgres is deployed locally, and we want to connect to the default database `postgres`. The second argument to the connection is always a
       Properties object that specifies additional connection requirements such as a username and password.
     2) We need to update the schema to match the schema we set while creating our tables. In this case, the schema is `'recitation'`.
     2) With the connection setup, the next few lines initialize some helpful classes for querying our Postgres database,
       storing results from querying the database, and a simple menu for user interaction.

5) Now we need to fill in `StoreQueryHelper.java` so that we can query from that table.
    1) Note the constructor of `StoreQueryHelper`. The constructor takes in a JDBC `Connection` object, which is
       used to create a `Statement` object. You can think of the `Statement` class as providing a method to execute
       queries and updates to the database **that do not require any parameters.**

5) At the first  `TODO` comment, complete the `findAll` method to retrieve all stores. 

5) Let's test this function by running `./gradlew run` and selecting the `Find All Stores` option. If we were successful
   with the last step, all ten stores should be displayed on the console.

6) For the `TODO` comments for the `findByStoreNumber`, `findByStoreType`, and `findByCityAndState` methods of `StoreQueryHelper.java`, we need
   to modify our statement to be able to **filter rows** and for the query to be able to **accept user arguments** as part
   of the filtering. (HINT: You should be using prepared statements.)

7) The `TODO` comment for the `createStore` method is to call a procedure defined in the `coffee_shop.sql` file. Remember, procedures can be called by using `CALL`.

8) The last `TODO` comment for the `uniqueStoreNames` method is to call a function defined in the `coffee_shop.sql` file. Remember, functions can be called by using `SELECT`.

9) The last step is to verify that your code is working correctly using `./gradlew run`.

## Conclusion

In this exercise, you wrote implementation code for connecting a Java app to a Postgres database using JDBC.
