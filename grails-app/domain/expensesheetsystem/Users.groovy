package expensesheetsystem

class Users {
    String username;
    Double userBalance;
    String toString(){
        "${username}"
    }

    static constraints = {
        username blank: false, unique: true
        userBalance blank: false, unique: false
    }

    static hasMany = [transactions: Transactions]

    static mapping = {
        table 'USERS'
        id column: 'ID', generator: 'sequence', params: [sequence:'tab_users_seq']
//        username column: 'username'
//        userBalance column: 'userBalance'
//        transactions cascade: 'all-delete-orphan'
        version(false)
    }
}
