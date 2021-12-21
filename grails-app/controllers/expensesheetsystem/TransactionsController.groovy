package expensesheetsystem

import groovy.json.JsonSlurper
import java.sql.*
import org.h2.jdbcx.JdbcConnectionPool
import java.text.SimpleDateFormat
import java.util.Date

class TransactionsController {
//    def scaffold = Transactions
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
    double zar
    double usd
    Date date = new Date()
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    def timeFormat = new SimpleDateFormat("HH:mm:ss.SSS")
    String datePart = dateFormat.format(date)
    String timePart = timeFormat.format(date)
    def fixerGet = new URL("http://data.fixer.io/api/latest?access_key=b2cc1095f7a5cfd38c627cd13c213b52&symbols=ZAR,USD").openConnection()
    def getRC = fixerGet.getInputStream().getText()
    def jsonSlurper = new JsonSlurper()
    def object = jsonSlurper.parseText(getRC)

//b2cc1095f7a5cfd38c627cd13c213b52
    def newTransactions() {

    }

    def insufficientFunds(){

    }

    def createTransaction(){
        def user = session["user"]
        String transactionCost = params.cost
        cost = transactionCost.toDouble()
        conPool = JdbcConnectionPool.create("jdbc:h2:./data/expenseSheetDB","sa","")
        con = conPool.getConnection()
        state = con.createStatement()
        resSet = state.executeQuery("SELECT userBalance FROM USERS WHERE username='"+user+"'")
        if (resSet.next()){
            balance = resSet.getDouble(1)
        }
        resSet.close()
        state.close()
        if((balance - cost) > 0){
            zarStr = String.valueOf(object.rates.ZAR)
            usdStr = String.valueOf(object.rates.USD)
            zar = zarStr.toDouble()
            usd = usdStr.toDouble()
            conversion = zar/usd
            converted = cost/conversion.round(2)
            runningBalance = balance - cost
            prepState = con.prepareStatement("INSERT INTO TRANSACTIONS (username, transactionZARCost, transactionDate, transactionTime, runningBalance, transientCost) VALUES (?,?,?,?,?,?)")
            prepState.setString(1,String.valueOf(user))
            prepState.setDouble(2, cost)
            prepState.setString(3, datePart)
            prepState.setString(4, timePart)
            prepState.setDouble(5, runningBalance)
            prepState.setDouble(6, converted)
            prepState.executeUpdate()
            prepState.close()
            prepState = con.prepareStatement("UPDATE USERS SET userBalance =? WHERE username=?")
            prepState.setDouble(1, runningBalance)
            prepState.setString(2, String.valueOf(user))
            prepState.executeUpdate()
            prepState.close()
            println("created")
            redirect(controller:'transactions', action:'newTransactions')
        }else{
            redirect(controller:'transactions', action:'insufficientFunds')
        }
        con.close()
        conPool.dispose()
    }

}
