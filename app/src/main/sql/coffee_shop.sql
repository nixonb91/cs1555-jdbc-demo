DROP SCHEMA IF EXISTS recitation CASCADE;
CREATE SCHEMA recitation;
SET SCHEMA 'recitation';

DROP TABLE IF EXISTS COFFEE CASCADE;
DROP TABLE IF EXISTS RECEIPT CASCADE;
DROP TABLE IF EXISTS STORE CASCADE;

CREATE TABLE COFFEE
(
    coffeeID integer PRIMARY KEY,
    name varchar(50),
    intensity integer,
    price numeric(5,2)
);

CREATE TABLE STORE
(
    storeNumber integer PRIMARY KEY,
    name varchar(50),
    storeType varchar(50),
    street varchar(50),
    city varchar(50),
    state varchar(20)
);

CREATE TABLE RECEIPT 
(
    receiptID integer PRIMARY KEY,
    storeNumber integer,
    timeOfPurchase timestamp,
    coffeeID integer,
    quantity integer,
    CONSTRAINT RECEIPT_STORE_FK FOREIGN KEY (storeNumber) REFERENCES STORE(storeNumber),
    CONSTRAINT RECEIPT_COFFEE_FK FOREIGN KEY (coffeeID) REFERENCES COFFEE(coffeeID)
);



INSERT INTO COFFEE
VALUES  (1, 'Espresso', 10, 1.45),
        (2, 'Latte', 4, 3.65),
        (3, 'Drip Coffee', 8, 1.15),
        (4, 'Cappuccino', 5, 2.79),
        (5, 'Macchiato', 5, 3.30),
        (6, 'Cold Brew', 7, 3.10),
        (7, 'Hot Chocolate', 1, 1.59),
        (8, 'Americano', 7, 2.05),
        (9, 'Mocha', 3, 2.65),
        (10, 'Frappuccino', 2, 2.89);

INSERT INTO STORE
VALUES  (1, 'Test Store', 'sitting', 'Forbes', 'Pittsburgh', 'Pennsylvania'),
        (2, 'Test Store2', 'sitting', 'Fifth', 'Pittsburgh', 'Pennsylvania'),
        (3, 'Test Store 3', 'drive-through', 'Lincoln', 'Seattle', 'Washington'),
        (4, 'Test Store 4', 'digital', 'Pike', 'Los Angeles', 'California'),
        (5, 'Test Store 5', 'digital', 'Park Place', 'Hollywood', 'California'),
        (6, 'Test Store 6', 'sitting', 'Madison Square', 'New York City', 'New York'),
        (7, 'Test Store 7', 'digital', 'Washington Ave', 'New York City', 'New York'),
        (8, 'Test Store 8', 'drive-through', 'Lincoln Place', 'Albany', 'New York'),
        (9, 'Test Store 9', 'digital', 'Forbes', 'Pittsburgh', 'Pennsylvania'),
        (10, 'Test Store 10', 'drive-through', 'Boulevard of the Allies', 'Pittsburgh', 'Pennsylvania');

INSERT INTO RECEIPT 
VALUES  (1, 1, '2024-11-01 10:00:00', 1, 2),
        (2, 1, '2024-11-02 10:15:00', 1, 3),
        (3, 2, '2024-10-28 09:30:40', 2, 1),
        (4, 3, '2024-10-29 11:15:00', 3, 3),
        (5, 4, '2024-10-28 08:20:15', 4, 2),
        (6, 5, '2024-10-29 09:30:40', 5, 2),
        (7, 6, '2024-11-02 12:40:00', 6, 5),
        (8, 2, '2024-10-21 09:20:20', 7, 1),
        (9, 9, '2024-10-19 11:11:11', 9, 3),
        (10, 10, '2024-11-03 06:45:00', 10, 4);

CREATE OR REPLACE PROCEDURE addStore(storeNumber int, name varchar(50), storeType varchar(50), street varchar(50), city varchar(50), state varchar(50)) AS
$$
BEGIN
    INSERT INTO store VALUES(storeNumber, name, storeType, street, city, state);
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION returnAllUniqueStoreNames() RETURNS
    TABLE (
        storeName varchar(50)
    ) AS
$$
BEGIN
    RETURN QUERY SELECT DISTINCT name FROM STORE;
END;
$$ LANGUAGE plpgsql;
