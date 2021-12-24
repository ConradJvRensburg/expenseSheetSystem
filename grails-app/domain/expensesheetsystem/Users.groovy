package expensesheetsystem

//creates domain class for users
class Users {
    String username;
    Double userBalance;
    String toString(){
        "${username}" //makes use of the username to identify the instance of the domain class in the transactions class
    }

    static constraints = { //sets constraints of the users table
        username blank: false, unique: true
        userBalance blank: false, unique: false
    }

    static hasMany = [transactions: Transactions] //defines relationship with transactions

    static mapping = {
        table 'USERS'
        id column: 'ID', generator: 'sequence', params: [sequence:'tab_users_seq'] //generates id for each user
        version(false)
    }
}
