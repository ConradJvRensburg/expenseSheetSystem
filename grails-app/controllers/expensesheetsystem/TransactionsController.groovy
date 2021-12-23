package expensesheetsystem

import groovy.json.JsonSlurper
import java.sql.*
import org.h2.jdbcx.JdbcConnectionPool
import java.text.SimpleDateFormat
import java.util.Date

class TransactionsController {
    def scaffold = Transactions
    JdbcConnectionPool conPool
    Connection con
    Statement state
    ResultSet resSet
    PreparedStatement prepState
    double balance
    double conversion
    double cost
    double converted
    double runningBalance
    String zarStr
    String usdStr
    String uName
    double zar
    double usd
    int count = 0
    Date date = new Date()
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    def timeFormat = new SimpleDateFormat("HH:mm:ss.SSS")
    String datePart = dateFormat.format(date)
    String timePart = timeFormat.format(date)
    def fixerGet = new URL("http://data.fixer.io/api/latest?access_key=677faae5018ac0c3e0a34046a22d1bb3&symbols=ZAR,USD").openConnection()
    def getRC = fixerGet.getInputStream().getText()
    def jsonSlurper = new JsonSlurper()
    def object = jsonSlurper.parseText(getRC)
    def transactList = []
    def transactMap = [:]
    boolean generated = false

    def newTransactions() {
        def user = session["user"]
        if (String.valueOf(user) == "null") {
            redirect(controller: 'landingPage', action: 'findUser')
        }
    }

    def insufficientFunds() {

    }

    def createTransaction() {
        def user = session["user"]
        String transactionCost = params.cost
        cost = transactionCost.toDouble()
        conPool = JdbcConnectionPool.create("jdbc:h2:./data/expenseSheetDB", "sa", "")
        con = conPool.getConnection()
        state = con.createStatement()
        resSet = state.executeQuery("SELECT user_balance, username FROM USERS WHERE ID='" + user + "'")
        if (resSet.next()) {
            balance = resSet.getDouble(1)
            uName = resSet.getString(2)
        }
        resSet.close()
        state.close()
        if ((balance - cost) > 0) {
            state = con.createStatement()
            resSet = state.executeQuery("SELECT ID FROM TRANSACTIONS")
            while (resSet.next()) {
                count = resSet.getInt(1)
            }
            count++
            zarStr = String.valueOf(object.rates.ZAR)
            usdStr = String.valueOf(object.rates.USD)
            zar = zarStr.toDouble()
            usd = usdStr.toDouble()
            conversion = zar / usd
            converted = cost / conversion.round(2)
            runningBalance = balance - cost
            prepState = con.prepareStatement("INSERT INTO TRANSACTIONS (ID, transaction_time, transaction_date, running_balance, transient_cost, USERS_ID, transactionZARCost) VALUES (?,?,?,?,?,?,?)")
            prepState.setInt(1, count)
            prepState.setString(2, timePart)
            prepState.setString(3, datePart)
            prepState.setDouble(4, runningBalance.round(2))
            prepState.setDouble(5, converted.round(2))
            prepState.setString(6, String.valueOf(user))
            prepState.setDouble(7, cost)
            prepState.executeUpdate()
            prepState.close()
            prepState = con.prepareStatement("UPDATE USERS SET user_balance =? WHERE ID=?")
            prepState.setDouble(1, runningBalance)
            prepState.setString(2, String.valueOf(user))
            prepState.executeUpdate()
            prepState.close()
            println("created")
            redirect(controller: 'transactions', action: 'newTransactions')
        } else {
            redirect(controller: 'transactions', action: 'insufficientFunds')
        }
        con.close()
        conPool.dispose()
    }

    def showUserTransactions() {
        def uID = session["user"]
        if (String.valueOf(uID) == "null") {
            redirect(controller: 'landingPage', action: 'findUser')
        } else {
            [toets:transactList]
        }
    }

    def loadUserTransactions() {
        def uID = session["user"]
        if (String.valueOf(uID) == "null") {
            redirect(controller: 'landingPage', action: 'findUser')
        } else {
            conPool = JdbcConnectionPool.create("jdbc:h2:./data/expenseSheetDB", "sa", "")
            con = conPool.getConnection()
            state = con.createStatement()
            resSet = state.executeQuery("SELECT ID, transactionZARCost, transaction_date, transaction_time, running_balance, transient_cost, USERS_ID FROM TRANSACTIONS WHERE USERS_ID='" + String.valueOf(uID) + "'")
            count = 0
            while (resSet.next()) {
                transactMap <<   [
                                    traID:resSet.getString(1),
                                    traCost:resSet.getString(2),
                                    traDate:resSet.getString(3),
                                    traTime:resSet.getString(4),
                                    traBal:resSet.getString(5),
                                    traUSD:resSet.getString(6),
                                    traUser:resSet.getString(7)
                                ]
                println("Map"+transactMap)
                transactList.add(count,transactMap)
            }
            con.close()
            conPool.dispose()
            redirect(controller: 'transactions', action: 'showUserTransactions')
        }
    }


//    def show(){
//        def uID = session["user"]
//        int id = String.valueOf(uID).toInteger()
//        println(id)
//        def user = Users.findById(id)
//
//        def transactions = Transactions.findAllByUsers(user, [sort:"transactionTime", order:"asc"])
//
//        [Users:user, transaction: transactions]
//        println("new")
//        println([Users:user, transaction: transactions]
//     }



//    def index = {
//
//    }
//
//    def create = {
//        println("Cool")
//    }
//
//    def save = {
//
//    }
//
//    def edit = {
//
//    }
//
//    def update = {
//
//    }
//
//    def show = {
//
//    }
//
//    def list = {
//
//    }
//
//    def delete = {
//
//    }

}
