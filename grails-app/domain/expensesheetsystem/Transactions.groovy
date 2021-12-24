package expensesheetsystem

//domain class for transactions
class Transactions {
    Double transactionZARcost
    String transactionDate
    String transactionTime
    Double runningBalance
    Double transientCost
    Users user

    static constraints = { //sets domain class constraints
        transactionZARcost blank: false, unique: false
        transactionDate blank: false, unique: false
        transactionTime blank: false, unique: false
        runningBalance blank: false, unique: false
        transientCost blank: false, unique: false

    }

    static belongsTo = [user: Users] //creates relationship with users domain class

    static mapping = { //maps domain class values
        table 'TRANSACTIONS'
        id column: 'ID', generator: 'sequence', params: [sequence:'tab_transactions_seq'] //id column for the transactions
        user column: "USERS_ID" //users foreign key
        version(false)
    }
}
