package expensesheetsystem

class Users {
    String username;
    Double userBalance;

    static constraints = {
        username()
        userBalance()
    }

    static mapping = {
        username column: 'username'
        userBalance column: 'userBalance'
    }
}
