package expensesheetsystem

import java.sql.Time

class Transactions {
    int transactionID;
    String username;
    Double transactionZARCost;
    Date transactionDate;
//    Time transactionTime;
    Double runningBalance;
    Double transientCost;

    static constraints = {
//        transactionID()
//        username()
//        transactionZARCost()
//        transactionDate()
//        transactionTime()
//        runningBalance()
//        transientCost()
        transactionID blank: false, unique: true
        username blank: false, unique: false
        transientCost blank: false, unique: false
        transactionDate blank: false, unique: false
//        transactionTime blank: false, unique: false
        runningBalance blank: false, unique: false
        transientCost blank: false, unique: false

    }

    static belongsTo = [users: Users]

    static mapping = {
        table 'TRANSACTIONS'
        id column: 'ID', generator: 'sequence', params: [sequence:'tab_users_seq']
        transactionID column: 'transactionID'
        username column: 'username'
        transactionZARCost column: 'transactionZARCost'
        transactionDate column: 'transactionDate'
//        transactionTime column: 'transactionTime'
        runningBalance column: 'runningBalance'
        transientCost column: 'transientCost'
        version(false)
    }
}
