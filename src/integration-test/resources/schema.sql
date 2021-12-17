DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS(
                      username VARCHAR(50) NOT NULL PRIMARY KEY,
                      userBalance DOUBLE NOT NULL
);

DROP TABLE IF EXISTS TRANSACTIONS;

CREATE TABLE TRANSACTIONS(
                             transactionID INT AUTO_INCREMENT PRIMARY KEY,
                             username VARCHAR(50) NOT NULL,
                             transactionZARCost DOUBLE NOT NULL,
                             transactionDate DATE NOT NULL,
                             transactionTime TIME NOT NULL,
                             runningBalance DOUBLE NOT NULL,
                             transientCost DOUBLE NOT NULL,
                             FOREIGN KEY (username) REFERENCES users(username)
);