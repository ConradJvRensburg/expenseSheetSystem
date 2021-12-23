package expensesheetsystem

class Transactions {
    Double transactionZARcost
    String transactionDate
    String transactionTime
    Double runningBalance
    Double transientCost
    Users user

    static constraints = {
        transactionZARcost blank: false, unique: false
        transactionDate blank: false, unique: false
        transactionTime blank: false, unique: false
        runningBalance blank: false, unique: false
        transientCost blank: false, unique: false

    }

    static belongsTo = [user: Users]

    static mapping = {
        table 'TRANSACTIONS'
        id column: 'ID', generator: 'sequence', params: [sequence:'tab_transactions_seq']
//        transactionZARcost column: 'transactionZARCost'
//        transactionDate column: 'transactionDate'
//        transactionTime column: 'transactionTime'
//        runningBalance column: 'runningBalance'
//        transientCost column: 'transientCost'
        user column: "USERS_ID"
        version(false)
    }
}
