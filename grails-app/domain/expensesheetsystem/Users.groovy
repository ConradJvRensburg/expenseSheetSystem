package expensesheetsystem

class Users {
    String username;
    Double userBalance;

    static constraints = {
//        username()
//        userBalance()
        username blank: false, unique: true
        userBalance blank: false, unique: false
    }

    static mapping = {
        table 'USERS'
        id column: 'ID', generator: 'sequence', params: [sequence:'tab_users_seq']
        username column: 'username'
        userBalance column: 'userBalance'
        version(false)
    }
}
