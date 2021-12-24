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
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") //creates new format for date inputs
    def timeFormat = new SimpleDateFormat("HH:mm:ss.SSS") //creates new format for time inpputs
    String datePart = dateFormat.format(date)
    String timePart = timeFormat.format(date)
    def fixerGet = new URL("http://data.fixer.io/api/latest?access_key=677faae5018ac0c3e0a34046a22d1bb3&symbols=ZAR,USD").openConnection() //creates new URL used to get ZAR and USD exchange rates
    def getRC = fixerGet.getInputStream().getText() //instantiates a variable with the data collected from the URL
    def jsonSlurper = new JsonSlurper() //creates a new jsonslurper
    def object = jsonSlurper.parseText(getRC) //uses jsonslurper to parse the text from the data that was retrieved from the URL
    def transactList = []
    def userTransactions = []


    def newTransactions() {
        def user = session["user"]
        if (String.valueOf(user) == "null") { //checks if a user has logged in
            redirect(controller: 'landingPage', action: 'findUser') //redirects back to the login page
        }
    }

    def insufficientFunds() {

    }

    def createTransaction() { //used to create new transactions
        def user = session["user"]
        String transactionCost = params.cost //retrieves the cost inserted in newTransactions.gsp
        cost = transactionCost.toDouble() //casts the input to double
        conPool = JdbcConnectionPool.create("jdbc:h2:./data/expenseSheetDB", "sa", "") //used to connect to the database
        con = conPool.getConnection()
        state = con.createStatement()
        resSet = state.executeQuery("SELECT user_balance, username FROM USERS WHERE ID='" + user + "'") //retrieves the user information of the logged in user
        if (resSet.next()) {
            balance = resSet.getDouble(1) //gets user balance
            uName = resSet.getString(2) //gets username
        }
        resSet.close()
        state.close()
        if ((balance - cost) > 0) { //checks if the user has enough money to make the transaction
            state = con.createStatement()
            resSet = state.executeQuery("SELECT ID FROM TRANSACTIONS") //gets the ids of all the transaction in the system
            while (resSet.next()) {
                count = resSet.getInt(1) //sets count equal to the last id
            }
            count++ //increments count by one
            zarStr = String.valueOf(object.rates.ZAR) //gets the ZAR conversion rate for euro
            usdStr = String.valueOf(object.rates.USD) //gets the USD conversion rate for euro
            zar = zarStr.toDouble() //cast to double
            usd = usdStr.toDouble() //cast to double
            conversion = zar / usd //divides ZAR conversion rate with USD conversion rate to get the ZAR to USD rate
            converted = cost / conversion.round(2) // divides the cost with the ZAR-USD conversion rate to get the USD cost
            runningBalance = balance - cost //deducts cost from the user balance
            prepState = con.prepareStatement("INSERT INTO TRANSACTIONS (ID, transaction_time, transaction_date, running_balance, transient_cost, USERS_ID, transactionZARCost) VALUES (?,?,?,?,?,?,?)")
            //insert values into the database to create a new transaction
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
            //updates the balance of the user
            prepState.setDouble(1, runningBalance)
            prepState.setString(2, String.valueOf(user))
            prepState.executeUpdate()
            prepState.close()
            redirect(controller: 'transactions', action: 'newTransactions')
        } else { //if the user does not have enough money for the transaction
            redirect(controller: 'transactions', action: 'insufficientFunds')
        }
        con.close()
        conPool.dispose()
    }

    def showUserTransactions() {
        def uID = session["user"]
        if (String.valueOf(uID) == "null") { //checks if the user is not logged in
            redirect(controller: 'landingPage', action: 'findUser')
        } else { //user is logged in
            conPool = JdbcConnectionPool.create("jdbc:h2:./data/expenseSheetDB", "sa", "") //connects to database
            con = conPool.getConnection()
            state = con.createStatement()
            //selects all the transactions of the logged in user
            resSet = state.executeQuery("SELECT ID, transactionZARCost, transaction_date, transaction_time, running_balance, transient_cost, USERS_ID FROM TRANSACTIONS WHERE USERS_ID='" + String.valueOf(uID) + "'")
            count = 0
            while (resSet.next()) { //loops for each transaction
                def transactMap = [:] //creates a map used to keep track of the transactions
                transactMap <<   [
                        traID:resSet.getString(1),
                        traCost:resSet.getString(2),
                        traDate:resSet.getString(3),
                        traTime:resSet.getString(4),
                        traBal:resSet.getString(5),
                        traUSD:resSet.getString(6),
                        traUser:resSet.getString(7)
                ] //adds transaction data to the map
                if (transactList.size() > 0){ //checks if the array list is not empty
                    for (int i = 0; i < transactList.size(); i++){ //loop is used to check for duplicate entries
                        if(!transactList.contains(transactMap)){ //if the array list does not contain the map
                            transactList.add(transactMap) //adds map to array list
                            def aTransaction = [
                                                     resSet.getString(1),
                                                     resSet.getString(2),
                                                     resSet.getString(3),
                                                     resSet.getString(4),
                                                     resSet.getString(5),
                                                     resSet.getString(6),
                                                     resSet.getString(7)
                                                  ] //defines array list used in the csv export and adds the transaction to the list
                            userTransactions << aTransaction //adds the transaction to the userTransactions array list
                        }
                    }
                }else{ //if the array list is empty
                    transactList.add(transactMap) //add first transaction
                    def aTransaction = [
                            resSet.getString(1),
                            resSet.getString(2),
                            resSet.getString(3),
                            resSet.getString(4),
                            resSet.getString(5),
                            resSet.getString(6),
                            resSet.getString(7)
                    ] //defines array list used in the csv export and adds the transaction to the list
                    userTransactions << aTransaction //adds the transaction to the userTransactions array list
                }
            }

            con.close()
            conPool.dispose()

            [toets:transactList] //returns the list of all the user's transactions to showUserTransactions.gsp
        }
    }

    def csv = { //used to export csv file
        response.setHeader("Content-disposition", "attachment; filename=userTransactions.csv") //sets data of the file that will be exported
        def headings = 'ID, transactionZARCost, transaction_date,transaction_time, running_balance, transient_cost, USERS_ID, \n' //headings of the csv file
        userTransactions.each {row-> //loops through each entry of the userTransactions array list
            row.each {col ->
                headings +=col + ','
            }
            headings = headings[0..-2]
            headings +='\n' //adds new line to the end of each row
        }

        render(contentType: 'text/csv', text: headings)
    }
}
