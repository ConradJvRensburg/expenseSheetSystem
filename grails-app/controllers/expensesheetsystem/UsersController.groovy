package expensesheetsystem

import java.sql.*
import org.h2.jdbcx.JdbcConnectionPool

class UsersController {
    static String loggedInUser;
    static String uName;
    JdbcConnectionPool conPool
    Connection con
    Statement state
    ResultSet resSet

    def scaffold = Users

    def defaultLanding() {

    }

    def loadUser() {
        conPool = JdbcConnectionPool.create("jdbc:h2:./data/expenseSheetDB","sa","")
        con = conPool.getConnection()
        def username = params.usernameInput
        loggedInUser = String.valueOf(username)
        state = con.createStatement()
        resSet = state.executeQuery("SELECT ID FROM USERS WHERE username='"+loggedInUser+"'")
        if (resSet.next()){
            println(resSet.getString(1))
            uName = resSet.getString(1)
            def user = session["user"]
            session["user"] = uName
            redirect(controller: 'users', action: 'defaultLanding')
        }else{
            redirect(controller: 'landingPage', action:'userNotFound')
        }
        con.close()
        conPool.dispose()
    }




}
