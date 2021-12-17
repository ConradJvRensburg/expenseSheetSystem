package expensesheetsystem

import java.sql.Time

class Transactions {
    int transactionID;
    String username;
    Double transactionZARCost;
    Date transactionDate;
    Time transactionTime;
    Double runningBalance;
    Double transientCost;

    static constraints = {
        transactionID()
        username()
        transactionZARCost()
        transactionDate()
        transactionTime()
        runningBalance()
        transientCost()
    }

    static mapping = {
        table 'TRANSACTIONS'
        transactionID column: 'transactionID'
        username column: 'username'
        transactionZARCost column: 'transactionZARCost'
        transactionDate column: 'transactionDate'
        transactionTime column: 'transactionTime'
        runningBalance column: 'runningBalance'
        transientCost column: 'transientCost'
    }
}
